package hk.hku.cs.foodlens.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import hk.hku.cs.foodlens.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val recyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(context)
        val cardList = listOf(
            RestaurantCardData("Restaurant 1", "Description 1"),
            RestaurantCardData("Restaurant 2", "Description 2"),
            RestaurantCardData("Restaurant 3", "Description 3")
            // Add more RestaurantCardData items here
        )
        val adapter = RestaurantCardAdapter(cardList) { cardData ->
            val action = HomeFragmentDirections.actionHomeFragmentToDetailFragment(cardData.title, cardData.description)
            findNavController().navigate(action)
        }
        recyclerView.adapter = adapter

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}