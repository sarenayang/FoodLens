package hk.hku.cs.foodlens.ui.restaurants

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.android.volley.Request
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import hk.hku.cs.foodlens.ui.model.Restaurant
import org.json.JSONArray

class RestaurantsViewModel(application: Application) : AndroidViewModel(application) {
    private val _restaurants = MutableLiveData<List<Restaurant>>()
    val restaurants: LiveData<List<Restaurant>> = _restaurants

    init {
//        fetchRestaurantsFromDatabase()
        _restaurants.value = listOf(
            Restaurant("1,","Chickin Nuggies Restaurante", "Central, Hong Kong", "freezer food"),
            Restaurant("2","Borger Guys", "Sai Ying Pun, Hong Kong", "fast food"),
            Restaurant("3","Mickey D's", "New York, United States", "fast food"),
            Restaurant("4","Din Tai Fung", "Taipei, Taiwan", "Taiwanese"),
            Restaurant("5","Ichiran", "Shibuya, Japan", "Japanese"),
            Restaurant("6","Bonchon Chicken", "Korea", "Korean"),
            Restaurant("7","Chipotle", "United States", "Mexican"),
            Restaurant("8","Chipotle", "United States", "Mexican"),
            Restaurant("9","Nando's", "United Kingdom", "South African"),

            )
    }

    private fun fetchRestaurantsFromDatabase() {
//        val localIp = "http://10.71.10.68:5000" // REPLACE WITH LOCAL SERVER IP
        val localIp = "http://172.30.64.180:5000"
        val url = "$localIp/restaurants"
        val jsonArrayRequest = JsonArrayRequest(
            Request.Method.GET, url, null,
            { response ->
                Log.d("RestaurantsViewModel", "Response: $response")
                val restaurantList = parseRestaurants(response)
                _restaurants.value = restaurantList
            },
            { error ->
                Log.e("RestaurantsViewModel", "Error fetching restaurants", error)
            })
        Volley.newRequestQueue(getApplication()).add(jsonArrayRequest)
    }

    private fun parseRestaurants(response: JSONArray): List<Restaurant> {
        val restaurantList = mutableListOf<Restaurant>()
        for (i in 0 until response.length()) {
            val restaurantArray = response.getJSONArray(i)
            val restaurantId = restaurantArray.getString(0)
            val restaurantName = restaurantArray.getString(1) // Assuming the title is the second element
            restaurantList.add(Restaurant(restaurantId, restaurantName, "location","cuisine"))
        }
        return restaurantList
    }


}