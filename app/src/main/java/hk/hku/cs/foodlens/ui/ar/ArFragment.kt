package hk.hku.cs.foodlens.ui.ar

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.ar.sceneform.ArSceneView
import com.google.ar.sceneform.ux.ArFragment
import hk.hku.cs.foodlens.databinding.FragmentArBinding
import hk.hku.cs.foodlens.databinding.FragmentMenuBinding
import hk.hku.cs.foodlens.ui.menu.MenuViewModel

class ArFragment : Fragment() {

    private var _binding: FragmentArBinding? = null
    private val binding get() = _binding!!
    private lateinit var arViewModel: ArViewModel
    private lateinit var arSceneView: ArSceneView
    private val args: ArFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        arViewModel =
            ViewModelProvider(this).get(ArViewModel::class.java)

        _binding = FragmentArBinding.inflate(inflater, container, false)
        val root: View = binding.root

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