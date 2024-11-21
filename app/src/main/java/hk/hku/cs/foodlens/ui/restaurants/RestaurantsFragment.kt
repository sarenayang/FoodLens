package hk.hku.cs.foodlens.ui.restaurants

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import hk.hku.cs.foodlens.databinding.FragmentRestaurantsBinding

class RestaurantsFragment : Fragment() {

    private var _binding: FragmentRestaurantsBinding? = null
    private val binding get() = _binding!!
    private lateinit var restaurantsViewModel: RestaurantsViewModel
    private lateinit var adapter: RestaurantCardAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        restaurantsViewModel =
            ViewModelProvider(this).get(RestaurantsViewModel::class.java)

        _binding = FragmentRestaurantsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val recyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(context)
        adapter = RestaurantCardAdapter(emptyList()) { cardData ->
            val action = RestaurantsFragmentDirections.actionRestaurantsFragmentToMenuFragment(cardData.title)
            findNavController().navigate(action)
        }
        recyclerView.adapter = adapter

        // Observe the restaurants LiveData
        restaurantsViewModel.restaurants.observe(viewLifecycleOwner, { restaurants ->
            Log.d("RestaurantsFragment", "Restaurants: $restaurants")
            adapter.updateRestaurants(restaurants)
        })

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}