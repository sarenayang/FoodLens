package hk.hku.cs.foodlens.ui.restaurants

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONArray
import org.json.JSONObject

class RestaurantsViewModel(application: Application) : AndroidViewModel(application) {
    private val _restaurants = MutableLiveData<List<RestaurantCardData>>()
    val restaurants: LiveData<List<RestaurantCardData>> = _restaurants

    init {
        fetchRestaurantsFromDatabase()
    }

    private fun fetchRestaurantsFromDatabase() {
        val url = "http://10.71.5.170:5000/restaurants"
        val jsonArrayRequest = JsonArrayRequest(
            Request.Method.GET, url, null,
            { response ->
                Log.d("RestaurantsViewModel", "Response: $response")
                val restaurantList = parseRestaurants(response)
                _restaurants.value = restaurantList
            },
            { error ->
                Log.e("RestaurantsViewModel", "Error fetching restaurants", error)
            }
        )
        Volley.newRequestQueue(getApplication()).add(jsonArrayRequest)
    }

    private fun parseRestaurants(response: JSONArray): List<RestaurantCardData> {
        val restaurantList = mutableListOf<RestaurantCardData>()
        for (i in 0 until response.length()) {
            val restaurantArray = response.getJSONArray(i)
            val title = restaurantArray.getString(1) // Assuming the title is the second element
            restaurantList.add(RestaurantCardData(title))
        }
        return restaurantList
    }
}