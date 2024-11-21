package hk.hku.cs.foodlens.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import hk.hku.cs.foodlens.R
import hk.hku.cs.foodlens.databinding.FragmentHomeBinding
import hk.hku.cs.foodlens.ui.model.Restaurant

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView: RecyclerView = binding.restaurantsListRecyclerview
        recyclerView.layoutManager = LinearLayoutManager(context)

        val homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        homeViewModel.restaurants.observe(viewLifecycleOwner) { restaurants ->
            recyclerView.adapter = HomeAdapter(requireContext(), restaurants) { restaurant ->
                // handle click to open restaurant menu
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}