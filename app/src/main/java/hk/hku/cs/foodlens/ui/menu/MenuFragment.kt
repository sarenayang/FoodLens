package hk.hku.cs.foodlens.ui.menu

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import hk.hku.cs.foodlens.ArActivity
import hk.hku.cs.foodlens.R
import hk.hku.cs.foodlens.databinding.FragmentMenuBinding


class MenuFragment : Fragment() {

    private var _binding: FragmentMenuBinding? = null
    private val binding get() = _binding!!
    private lateinit var menuViewModel: MenuViewModel

    private val args: MenuFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        menuViewModel = ViewModelProvider(this, MenuViewModelFactory(args.restaurantId, requireActivity().application)).get(MenuViewModel::class.java)

        _binding = FragmentMenuBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.titleTextView.text = args.restaurantName

        val recyclerView: RecyclerView = binding.menuListRecyclerview
        recyclerView.layoutManager = LinearLayoutManager(context)

        val menuViewModel = ViewModelProvider(this).get(MenuViewModel::class.java)
        menuViewModel.menu_items.observe(viewLifecycleOwner) { items ->
            recyclerView.adapter = MenuItemAdapter(requireContext(), args.restaurantName, items) { it ->
                // Create an Intent to start ARActivity
                val intent = Intent(requireContext(), ArActivity::class.java)
                startActivity(intent)
            }
        }

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}