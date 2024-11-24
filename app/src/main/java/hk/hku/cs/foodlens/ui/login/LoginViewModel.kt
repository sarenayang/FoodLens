package hk.hku.cs.foodlens.ui.login

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley

class LoginViewModel(application: Application) : AndroidViewModel(application) {

    private val context = getApplication<Application>().applicationContext

    val loginResult = MutableLiveData<Boolean>()
    val errorMessage = MutableLiveData<String>()

    fun login(username: String, password: String) {
        val url = "http://172.30.64.180:5000/accounts/findid/$username" // CHANGE THIS TO YOUR LOCAL SERVER

        val stringRequest = StringRequest(
            Request.Method.GET,
            url,
            Response.Listener { response ->
                Log.d("LoginViewModel", "Response: $response")

                // Example validation based on mock response (you need to adjust it as per the API response format)
                if (response.isNotEmpty()) {
                    // Assuming you would then check for a matching password
                    loginResult.value = true
                } else {
                    loginResult.value = false
                    errorMessage.value = "Invalid username or password"
                }
            },
            Response.ErrorListener { error ->
                Log.e("LoginViewModel", "Error: ${error.message}")
                loginResult.value = false
                // Provide a more descriptive error message based on the actual error
                errorMessage.value = "Failed to log in. Please try again or check your network connection."
            }
        )

        // Add the request to the Volley request queue
        Volley.newRequestQueue(context).add(stringRequest)
    }
}
