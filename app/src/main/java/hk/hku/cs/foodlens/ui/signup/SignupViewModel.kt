//package hk.hku.cs.foodlens.ui.signup
//
//import android.app.Application
//import androidx.lifecycle.AndroidViewModel
//import androidx.lifecycle.viewModelScope
//import hk.hku.cs.foodlens.data.AppDatabase
//import hk.hku.cs.foodlens.data.User
//import kotlinx.coroutines.launch
//
//class SignupViewModel(application: Application) : AndroidViewModel(application) {
//    private val userDao = AppDatabase.getDatabase(application).userDao()
//
//    fun signup(username: String, password: String) {
//        viewModelScope.launch {
//            userDao.insertUser(User(username, password))
//        }
//    }
//}
package hk.hku.cs.foodlens.ui.signup

import androidx.lifecycle.ViewModel

class SignupViewModel : ViewModel() {
    // Placeholder logic or variables
    // Example: val signupStatus = MutableLiveData<String>()
}
