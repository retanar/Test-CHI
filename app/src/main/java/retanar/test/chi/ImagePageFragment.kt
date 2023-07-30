package retanar.test.chi

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.GridLayoutManager
import retanar.test.chi.data.ShibeEntity
import retanar.test.chi.databinding.FragmentImagePageBinding
import retanar.test.chi.presentation.ShibeListAdapter

class ImagePageFragment(
    private val shibeList: LiveData<List<ShibeEntity>>,
    private val onChangeFavorite: (String) -> Unit,
) : Fragment() {

    private lateinit var binding: FragmentImagePageBinding
    private lateinit var adapter: ShibeListAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentImagePageBinding.inflate(inflater, container, false)

        adapter = ShibeListAdapter(onChangeFavorite = onChangeFavorite)
        binding.recycler.adapter = adapter
        binding.recycler.layoutManager = GridLayoutManager(requireContext(), 2, GridLayoutManager.VERTICAL, false)

        shibeList.observe(viewLifecycleOwner) { list ->
            if (list == null) return@observe
            adapter.submitList(list)
        }

        return binding.root
    }
}