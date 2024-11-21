package hk.hku.cs.foodlens.ui.friends

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import hk.hku.cs.foodlens.databinding.FragmentFriendsBinding
import hk.hku.cs.foodlens.ui.Review
import hk.hku.cs.foodlens.ui.ReviewsAdapter

class FriendsFragment : Fragment() {

    private var _binding: FragmentFriendsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val friendsViewModel =
            ViewModelProvider(this).get(FriendsViewModel::class.java)

        _binding = FragmentFriendsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val recyclerView: RecyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(context)

        val reviews = listOf(
            Review("User1", "Dish 1", "Restaurant 1", "Great place!", 3),
            Review("User2", "Dish 2", "Restaurant 2", "Good food.", 2),
            Review("User3", "Dish 3", "Restaurant 3", "Okay experience.", 1)
        )
        recyclerView.adapter = ReviewsAdapter(reviews)

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}