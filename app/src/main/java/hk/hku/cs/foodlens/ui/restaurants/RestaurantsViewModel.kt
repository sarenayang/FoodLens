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
import hk.hku.cs.foodlens.ui.model.Restaurant
import org.json.JSONArray
import org.json.JSONObject

class RestaurantsViewModel(application: Application) : AndroidViewModel(application) {
    private val _restaurants = MutableLiveData<List<Restaurant>>()
    val restaurants: LiveData<List<Restaurant>> = _restaurants

    init {
//        fetchRestaurantsFromDatabase()
        _restaurants.value = listOf(
            Restaurant("Chickin Nuggies Restaurante", "ur mom's house", "freezer food", listOf("item1", "item2")),
            Restaurant("Borger Guys", "ur mom's house", "fast food", listOf("item1", "item2")),
            Restaurant("Mickey D's", "ur mom's house", "fast food", listOf("item1", "item2"))
        )
    }


}