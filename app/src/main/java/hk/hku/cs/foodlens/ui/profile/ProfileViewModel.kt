package hk.hku.cs.foodlens.ui.profile

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
import hk.hku.cs.foodlens.ui.friends.ReviewData
import org.json.JSONArray
import org.json.JSONObject

class ProfileViewModel(application: Application) : AndroidViewModel(application) {

    private val _userName = MutableLiveData<String>()
    val userName: LiveData<String> = _userName

    private val _numFriends = MutableLiveData<Int>()
    val numFriends: LiveData<Int> = _numFriends

    private val _reviews = MutableLiveData<List<ReviewData>>()
    val reviews: LiveData<List<ReviewData>> = _reviews

    private val userId = 1 // Replace with the actual user ID
    private val queue: RequestQueue = Volley.newRequestQueue(getApplication())

    init {
        fetchUserName()
        fetchNumFriends()
        fetchUserReviews()
    }

    private fun fetchUserName() {
        val url = "http://10.71.5.170:5000/accounts/$userId/name"
        val stringRequest = StringRequest(
            Request.Method.GET, url,
            { response ->
                _userName.value = response
            },
            { error ->
                Log.e("ProfileViewModel", "Error fetching user name", error)
            }
        )
        queue.add(stringRequest)
    }

    private fun fetchNumFriends() {
        val url = "http://10.71.5.170:5000/accounts/$userId"
        val jsonObjectRequest = JsonArrayRequest(
            Request.Method.GET, url, null,
            { response ->
                _numFriends.value = response.getInt(4)
            },
            { error ->
                Log.e("ProfileViewModel", "Error fetching number of friends", error)
            }
        )
        queue.add(jsonObjectRequest)
    }

    private fun fetchUserReviews() {
        val url = "http://10.71.5.170:5000/accounts/$userId/reviews"
        val jsonArrayRequest = JsonArrayRequest(
            Request.Method.GET, url, null,
            { response ->
                Log.d("ProfileViewModel", "User reviews response: $response") // Log the response
                if (response.length() == 0) {
                    _reviews.value = emptyList()
                    return@JsonArrayRequest
                }
                val reviewIds = mutableListOf<Int>()
                for (i in 0 until response.length()) {
                    reviewIds.add(response.getInt(i))
                }
                fetchReviewsDetails(reviewIds)
            },
            { error ->
                Log.e("ProfileViewModel", "Error fetching user reviews", error)
            }
        )
        queue.add(jsonArrayRequest)
    }

    private fun fetchReviewsDetails(reviewIds: List<Int>) {
        val url = "http://10.71.5.170:5000/reviews"
        val jsonArrayRequest = JsonArrayRequest(
            Request.Method.GET, url, null,
            { response ->
                val allReviews = parseReviews(response)
                val userReviews = allReviews.filter { it.id in reviewIds }
                fetchAdditionalData(userReviews)
            },
            { error ->
                Log.e("ProfileViewModel", "Error fetching reviews details", error)
            }
        )
        queue.add(jsonArrayRequest)
    }

    private fun fetchAdditionalData(reviews: List<ReviewData>) {
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
                fetchDishNames(reviews)
            },
            { error ->
                Log.e("ProfileViewModel", "Error fetching restaurants", error)
            }
        )
        queue.add(restaurantRequest)
    }

    private fun fetchDishNames(reviews: List<ReviewData>) {
        reviews.forEach { review ->
            val dishUrl = "http://10.71.5.170:5000/dishes/${review.dishId}"
            val dishRequest = JsonArrayRequest(
                Request.Method.GET, dishUrl, null,
                { response ->
                    review.dishName = response.getString(1)
                    _reviews.value = reviews
                },
                { error ->
                    Log.e("ProfileViewModel", "Error fetching dish name", error)
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
                    review.getInt(5),
                    "You"

                )
            )
        }
        return reviewList
    }
}