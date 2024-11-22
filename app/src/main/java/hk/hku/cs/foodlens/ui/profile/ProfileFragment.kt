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
import hk.hku.cs.foodlens.ui.ReviewsAdapter

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private lateinit var profileViewModel: ProfileViewModel
    private lateinit var reviewsAdapter: ReviewsAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        profileViewModel = ViewModelProvider(this).get(ProfileViewModel::class.java)

        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val userNameTextView = binding.userName
        val numberOfFriendsTextView = binding.numberOfFriends
        val signOutButton = binding.signOutButton
        val reviewsRecyclerView: RecyclerView = binding.reviewsRecyclerView

        // Observe the user's name
        profileViewModel.userName.observe(viewLifecycleOwner, { name ->
            userNameTextView.text = name
        })

        // Observe the number of friends
        profileViewModel.numFriends.observe(viewLifecycleOwner, { numFriends ->
            numberOfFriendsTextView.text = "Friends: $numFriends"
        })

        // Handle sign out button click
        signOutButton.setOnClickListener {
            // Implement sign out logic here
        }

        // Set up the RecyclerView
        reviewsAdapter = ReviewsAdapter(emptyList())
        reviewsRecyclerView.layoutManager = LinearLayoutManager(context)
        reviewsRecyclerView.adapter = reviewsAdapter

        // Observe the reviews LiveData
        profileViewModel.reviews.observe(viewLifecycleOwner, { reviews ->
            reviewsAdapter.updateReviews(reviews)
        })

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}