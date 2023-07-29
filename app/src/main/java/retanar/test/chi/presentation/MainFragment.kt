package retanar.test.chi.presentation

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.google.android.material.snackbar.Snackbar
import retanar.test.chi.databinding.FragmentMainBinding

class MainFragment : Fragment() {

    private val viewModel: MainViewModel by activityViewModels()
    private lateinit var binding: FragmentMainBinding
    private lateinit var adapter: ShibeListAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentMainBinding.inflate(inflater, container, false)

        adapter = ShibeListAdapter()
        binding.recycler.adapter = adapter
        binding.recycler.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL).apply {
            gapStrategy = StaggeredGridLayoutManager.GAP_HANDLING_NONE
        }
//            GridLayoutManager(context, 2)

        viewModel.requestShibes()
        viewModel.shibeList.observe(viewLifecycleOwner) { list ->
            adapter.submitList(list)
            Log.d("MainFragment", list.take(4).joinToString())
        }
        viewModel.errorNotification.observe(viewLifecycleOwner) { errorText ->
            errorText?.let {
                Snackbar.make(binding.root, "Error: $errorText", Snackbar.LENGTH_LONG).show()
                viewModel.errorNotification.value = null
            }
        }
        return binding.root
    }

}