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
import androidx.recyclerview.widget.RecyclerView
import hk.hku.cs.foodlens.databinding.FragmentRestaurantsBinding

class RestaurantsFragment : Fragment() {

    private var _binding: FragmentRestaurantsBinding? = null
    private val binding get() = _binding!!
    private lateinit var restaurantsViewModel: RestaurantsViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        restaurantsViewModel =
            ViewModelProvider(this).get(RestaurantsViewModel::class.java)

        _binding = FragmentRestaurantsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView: RecyclerView = binding.restaurantsListRecyclerview
        recyclerView.layoutManager = LinearLayoutManager(context)

        val homeViewModel = ViewModelProvider(this).get(RestaurantsViewModel::class.java)
        homeViewModel.restaurants.observe(viewLifecycleOwner) { restaurants ->
            recyclerView.adapter = RestaurantCardAdapter(requireContext(), restaurants) { cardData ->
                val action = RestaurantsFragmentDirections.actionRestaurantsFragmentToMenuFragment(cardData.name)
                findNavController().navigate(action)
            }
        }
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}