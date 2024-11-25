package hk.hku.cs.foodlens.ui.menu

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import hk.hku.cs.foodlens.ArActivity
import hk.hku.cs.foodlens.R
import hk.hku.cs.foodlens.databinding.FragmentMenuBinding
import io.github.sceneview.utils.colorOf


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


        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView: RecyclerView = binding.menuListRecyclerview
        recyclerView.layoutManager = LinearLayoutManager(context)



        val menuViewModel = ViewModelProvider(this).get(MenuViewModel::class.java)
        menuViewModel.menu_items.observe(viewLifecycleOwner) { items ->
            recyclerView.adapter = MenuItemAdapter(requireContext(),
                args.restaurantName,
                items,
                onItemClick = { it ->
                    val intent = Intent(requireContext(), ArActivity::class.java)
                    val filename = it.dishFileName
                    intent.putExtra("Filename", filename)
                    startActivity(intent)
                },
                onButtonClick = { menuItem -> showDialog() }
            )


        }

    }

    private fun showDialog() {
        val dialog = Dialog(requireContext())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.fragment_rating)

        val star1: ImageView = dialog.findViewById(R.id.star1)
        star1.setOnClickListener { star1.setImageResource(R.drawable.star_filled_rating) }// Change to selected star image
        val star2: ImageView = dialog.findViewById(R.id.star2)
        star2.setOnClickListener {
            star1.setImageResource(R.drawable.star_filled_rating)
            star2.setImageResource(R.drawable.star_filled_rating)
        }
        val star3: ImageView = dialog.findViewById(R.id.star3)
        star3.setOnClickListener {
            star1.setImageResource(R.drawable.star_filled_rating)
            star2.setImageResource(R.drawable.star_filled_rating)
            star3.setImageResource(R.drawable.star_filled_rating)

        }

        val doneBtn = dialog.findViewById(R.id.doneButton) as Button
        doneBtn.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }




    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}