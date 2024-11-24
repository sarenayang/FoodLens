//package hk.hku.cs.foodlens.ui.signup
//
//import android.app.Application
//import android.util.Log
//import androidx.lifecycle.AndroidViewModel
//import androidx.lifecycle.MutableLiveData
//import com.android.volley.Request
//import com.android.volley.Response
//import com.android.volley.toolbox.StringRequest
//import com.android.volley.toolbox.Volley
//
//class SignupViewModel(application: Application) : AndroidViewModel(application) {
//
//    private val context = getApplication<Application>().applicationContext
//
//    val signupResult = MutableLiveData<Boolean>()
//    val errorMessage = MutableLiveData<String>()
//
//    fun signup(name: String, email: String, password: String) {
//        val url = "http://10.71.1.1:5000/accounts/create"  // Base URL without query params
//
//        // Create a POST request with parameters in the body
//        val stringRequest = object : StringRequest(
//            Method.POST,
//            url,
//            { response ->
//                Log.d("SignupViewModel", "Response: $response")
//                signupResult.value = true
//            },
//            { error ->
//                Log.e("SignupViewModel", "Error: ${error.message}")
//                errorMessage.value = "Signup failed. Please try again."
//                signupResult.value = false
//            }
//        ) {
//            // Add parameters to request body
//            override fun getParams(): Map<String, String> {
//                val params = HashMap<String, String>()
//                params["name"] = name
//                params["email"] = email
//                params["password"] = password
//                return params
//            }
//        }
//
//        // Add the request to the Volley request queue
//        Volley.newRequestQueue(context).add(stringRequest)
//    }
//}
package hk.hku.cs.foodlens.ui.signup

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import java.io.UnsupportedEncodingException
import java.net.URLEncoder

class SignupViewModel(application: Application) : AndroidViewModel(application) {

    private val context = getApplication<Application>().applicationContext

    val signupResult = MutableLiveData<Boolean>()
    val errorMessage = MutableLiveData<String>()

    fun signup(name: String, email: String, password: String) {
        val baseUrl = "http://10.71.1.1:5000/accounts/create" //CHANGE THIS TO YOUR LOCAL SERVER

        // Encode parameters to make sure they are URL-safe
        val encodedName = try { URLEncoder.encode(name, "UTF-8") } catch (e: UnsupportedEncodingException) { name }
        val encodedEmail = try { URLEncoder.encode(email, "UTF-8") } catch (e: UnsupportedEncodingException) { email }
        val encodedPassword = try { URLEncoder.encode(password, "UTF-8") } catch (e: UnsupportedEncodingException) { password }

        // Build the URL with parameters
        val fullUrl = "$baseUrl?name=$encodedName&email=$encodedEmail&password=$encodedPassword"

        // Log the full URL with parameters
        Log.d("SignupViewModel", "Full URL: $fullUrl")

        // Now you can proceed to make the request
        val stringRequest = object : StringRequest(
            Method.GET,
            fullUrl, // Use the full URL with parameters
            { response ->
                Log.d("SignupViewModel", "Response: $response")
                signupResult.value = true
            },
            { error ->
                Log.e("SignupViewModel", "Error: ${error.message}")
                errorMessage.value = "Signup failed. Please try again."
                signupResult.value = false
            }
        ) {}

        // Add the request to the Volley request queue
        Volley.newRequestQueue(context).add(stringRequest)
    }
}
