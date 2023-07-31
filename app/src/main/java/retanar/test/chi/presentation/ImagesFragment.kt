package retanar.test.chi.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import retanar.test.chi.databinding.FragmentImagePageBinding

class ImagesFragment : Fragment() {

    private val viewModel: MainViewModel by activityViewModels()
    private lateinit var binding: FragmentImagePageBinding
    private lateinit var adapter: ShibeListAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentImagePageBinding.inflate(inflater, container, false)

        adapter = ShibeListAdapter(
            onChangeFavorite = viewModel::changeFavorite,
            onReachedBottom = viewModel::requestShibes,
        )
        binding.recycler.adapter = adapter
        binding.recycler.layoutManager = GridLayoutManager(requireContext(), 2, GridLayoutManager.VERTICAL, false)

        viewModel.shibeList.observe(viewLifecycleOwner) { list ->
            if (list == null) return@observe
            adapter.submitList(list)
        }

        return binding.root
    }
}