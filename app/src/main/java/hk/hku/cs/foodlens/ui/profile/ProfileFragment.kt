package hk.hku.cs.foodlens.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import hk.hku.cs.foodlens.databinding.FragmentProfileBinding
import hk.hku.cs.foodlens.ui.Review
import hk.hku.cs.foodlens.ui.ReviewsAdapter

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val profileViewModel =
            ViewModelProvider(this).get(ProfileViewModel::class.java)

        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val userNameTextView = binding.userName
        val numberOfFriendsTextView = binding.numberOfFriends
        val signOutButton = binding.signOutButton
        val reviewsRecyclerView: RecyclerView = binding.reviewsRecyclerView

        // Set the user's name
        userNameTextView.text = "John Doe" // Replace with actual user name
        numberOfFriendsTextView.text = "Friends: 5" // Replace with actual number of friends
        // Handle sign out button click
        signOutButton.setOnClickListener {
            // Implement sign out logic here
        }

        // Set up the RecyclerView
        val reviews = listOf(
            Review("You", "Dish 1", "Restaurant 1", "Great place!", 3),
            Review("You", "Dish 2", "Restaurant 2", "Good food.", 2),
            Review("You", "Dish 3", "Restaurant 3", "Okay experience.", 1)
        )
        reviewsRecyclerView.layoutManager = LinearLayoutManager(context)
        reviewsRecyclerView.adapter = ReviewsAdapter(reviews)

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}