package hk.hku.cs.foodlens.ui.menu

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.fragment.navArgs
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import hk.hku.cs.foodlens.ui.model.MenuItem
import hk.hku.cs.foodlens.ui.model.Restaurant
import org.json.JSONArray
import org.json.JSONObject


class MenuViewModel(restaurant_id: String, application: Application) : AndroidViewModel(application) {
    private val _menuItems = MutableLiveData<List<MenuItem>>()
    val menu_items: LiveData<List<MenuItem>> = _menuItems

    var _restaurantId: String = restaurant_id
    val localIp = "http://10.71.10.68:5000" // REPLACE WITH LOCAL SERVER IP


    init {
        fetchMenuItemsFromDatabase()
//        _menuItems.value = listOf("item1", "item2", "item3")
    }

//    fun setRestaurantId(id: String) {
//        _restaurantId = id
//        Log.v("BRUH", _restaurantId)
//
//    }

    private fun fetchMenuItemsFromDatabase() {
        val url = "$localIp/restaurants/${_restaurantId}/dishes" // gets the menu of dish ids
// once we have all the dish ids, we need to parse the dish to get the name and filename

        val jsonArrayRequest = JsonArrayRequest(
            Request.Method.GET, url, null,
            { response ->
                Log.d("MenuViewModel", "Fetch menu from database Response: $response")
                parseMenu(response)
            },
            { error ->
                Log.e("MenuViewModel", "Error fetching menu", error)
            })
        Volley.newRequestQueue(getApplication()).add(jsonArrayRequest)
    }

    private fun parseMenu(response: JSONArray) {
        val menu = mutableListOf<MenuItem>()
        for (i in 0 until response.length()) {
            val dishId = response.getString(i)

            val url = "$localIp/dishes/$dishId" // gets the dish info from dish id
            val jsonArrayRequest = JsonArrayRequest(
                Request.Method.GET, url, null,
                { response ->
                    Log.d("MenuViewModel", "parse menu Response: $response")
                    val menuItem = parseMenuItem(response)
                    menu.add(menuItem)
                },
                { error ->
                    Log.e("MenuViewModel", "Error fetching dish info", error)
                })
            Volley.newRequestQueue(getApplication()).add(jsonArrayRequest)

        }

        _menuItems.value = menu
    }

    private fun parseMenuItem(response: JSONArray): MenuItem {
        val dishName = response.getString(1)
        val dishFileName = if (response.isNull(2)) "sushi.glb" else response.getString(2)

        return MenuItem(dishName, dishFileName)
    }


}