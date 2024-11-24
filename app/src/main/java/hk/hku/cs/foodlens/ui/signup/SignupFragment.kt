package hk.hku.cs.foodlens.ui.signup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import hk.hku.cs.foodlens.R
import hk.hku.cs.foodlens.databinding.FragmentSignupBinding

class SignupFragment : Fragment() {

    private var _binding: FragmentSignupBinding? = null
    private val binding get() = _binding!!

    // Using shared ViewModel to communicate between fragment and activity
    private val signupViewModel: SignupViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSignupBinding.inflate(inflater, container, false)
        val root: View = binding.root

        // Observing the signup result and error message
        signupViewModel.signupResult.observe(viewLifecycleOwner, Observer { result ->
            if (result) {
                // If signup is successful, navigate to login fragment
                findNavController().navigate(R.id.action_signupFragment_to_loginFragment)
            }
        })

        signupViewModel.errorMessage.observe(viewLifecycleOwner, Observer { error ->
            // If there's an error, show a Toast
            Toast.makeText(context, error, Toast.LENGTH_SHORT).show()
        })

        binding.signupButton.setOnClickListener {
            val username = binding.usernameInput.text.toString().trim()
            val email = binding.emailInput.text.toString().trim()
            val password = binding.passwordInput.text.toString().trim()

            if (validateInput(username, email, password)) {
                // Call the signup method in ViewModel
                signupViewModel.signup(username, email, password)
            } else {
                Toast.makeText(context, "Please fill out all fields correctly.", Toast.LENGTH_SHORT).show()
            }
        }

        return root
    }

    private fun validateInput(username: String, email: String, password: String): Boolean {
        return username.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
