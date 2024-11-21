package hk.hku.cs.foodlens.ui.menu

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

class MenuViewModel(application: Application) : AndroidViewModel(application) {
    private val _menuItems = MutableLiveData<List<String>>()
    val menu_items: LiveData<List<String>> = _menuItems

    init {
//        fetchRestaurantsFromDatabase()
        _menuItems.value = listOf("item1", "item2", "item3")
    }


}