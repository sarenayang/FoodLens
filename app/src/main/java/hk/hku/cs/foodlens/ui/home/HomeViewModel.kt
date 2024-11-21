package hk.hku.cs.foodlens.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import hk.hku.cs.foodlens.ui.model.Restaurant

class HomeViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "ur mom"
    }
    val text: LiveData<String> = _text


    // restaurant list
    private val _restaurants = MutableLiveData<List<Restaurant>>()
    val restaurants: LiveData<List<Restaurant>> get() = _restaurants


    // Initialize with sample data
    init {
        _restaurants.value = listOf(
            Restaurant("Chickin Nuggies Restaurante", "ur mom's house", "freezer food", listOf("item1", "item2")),
            Restaurant("Borger Guys", "ur mom's house", "fast food", listOf("item1", "item2")),
            Restaurant("Mickey D's", "ur mom's house", "fast food", listOf("item1", "item2"))
        )
    }






}