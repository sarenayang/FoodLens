package hk.hku.cs.foodlens.ui.friends

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONArray
import org.json.JSONObject

class FriendsViewModel(application: Application) : AndroidViewModel(application) {

    private val _reviews = MutableLiveData<List<ReviewData>>()
    val reviews: LiveData<List<ReviewData>> = _reviews

    init {
        fetchFriendsReviews()
    }

    private fun fetchFriendsReviews() {
        val url = "http://10.71.5.170:5000/accounts/7" // Replace with the actual account ID
        val jsonArrayRequest = JsonArrayRequest(
            Request.Method.GET, url, null,
            { response ->
                val friendListString = response.getString(6)
                val friendList = friendListString.split(",").map { it.trim().toInt() }
                fetchAllReviews(friendList)
            },
            { error ->
                Log.e("FriendsViewModel", "Error fetching friends list", error)
            }
        )
        Volley.newRequestQueue(getApplication()).add(jsonArrayRequest)
    }

    private fun fetchAllReviews(friendList: List<Int>) {
        val url = "http://10.71.5.170:5000/reviews"
        val jsonArrayRequest = JsonArrayRequest(
            Request.Method.GET, url, null,
            { response ->
                val allReviews = parseReviews(response)
                val friendsReviews = allReviews.filter { it.accountId in friendList }
                fetchAdditionalData(friendsReviews)
            },
            { error ->
                Log.e("FriendsViewModel", "Error fetching all reviews", error)
            }
        )
        Volley.newRequestQueue(getApplication()).add(jsonArrayRequest)
    }

    private fun fetchAdditionalData(reviews: List<ReviewData>) {
        val queue = Volley.newRequestQueue(getApplication())
        val restaurantUrl = "http://10.71.5.170:5000/restaurants"

        // Fetch restaurant names
        val restaurantRequest = JsonArrayRequest(
            Request.Method.GET, restaurantUrl, null,
            { response ->
                val restaurantMap = mutableMapOf<Int, String>()
                for (i in 0 until response.length()) {
                    val restaurant = response.getJSONArray(i)
                    restaurantMap[restaurant.getInt(0)] = restaurant.getString(1)
                }
                reviews.forEach { it.restaurantName = restaurantMap[it.restaurantId] ?: "" }
                fetchFriendAndDishNames(reviews, queue)
            },
            { error ->
                Log.e("FriendsViewModel", "Error fetching restaurants", error)
            }
        )
        queue.add(restaurantRequest)
    }

    private fun fetchFriendAndDishNames(reviews: List<ReviewData>, queue: RequestQueue) {
        reviews.forEach { review ->
            // Fetch friend name
            val friendUrl = "http://10.71.5.170:5000/accounts/${review.accountId}/name"
            val friendRequest = StringRequest(
                Request.Method.GET, friendUrl,
                { response ->
                    review.friendName = response
                    _reviews.value = reviews
                },
                { error ->
                    Log.e("FriendsViewModel", "Error fetching friend name", error)
                }
            )
            queue.add(friendRequest)

            // Fetch dish name
            val dishUrl = "http://10.71.5.170:5000/dishes/${review.dishId}"
            val dishRequest = JsonArrayRequest(
                Request.Method.GET, dishUrl, null,
                { response ->
                    review.dishName = response.getString(1)
                    _reviews.value = reviews
                },
                { error ->
                    Log.e("FriendsViewModel", "Error fetching dish name", error)
                }
            )
            queue.add(dishRequest)
        }
    }

    private fun parseReviews(response: JSONArray): List<ReviewData> {
        val reviewList = mutableListOf<ReviewData>()
        for (i in 0 until response.length()) {
            val review = response.getJSONArray(i)
            reviewList.add(
                ReviewData(
                    review.getInt(0),
                    review.getString(1),
                    review.getInt(2),
                    review.getInt(3),
                    review.getInt(4),
                    review.getInt(5)
                )
            )
        }
        return reviewList
    }
}

data class ReviewData(
    val id: Int,
    val date: String,
    val restaurantId: Int,
    val dishId: Int,
    val grade: Int,
    val accountId: Int,
    var friendName: String = "",
    var dishName: String = "",
    var restaurantName: String = ""
)