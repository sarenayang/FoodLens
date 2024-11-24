package hk.hku.cs.foodlens.ui.menu

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class MenuViewModelFactory(val restaurant_id: String, val application: Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MenuViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MenuViewModel(restaurant_id, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}