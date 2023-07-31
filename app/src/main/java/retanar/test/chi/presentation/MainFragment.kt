package retanar.test.chi.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayoutMediator
import retanar.test.chi.databinding.FragmentMainBinding

class MainFragment : Fragment() {

    private val viewModel: MainViewModel by activityViewModels()
    private lateinit var binding: FragmentMainBinding
    private lateinit var pageAdapter: PageAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentMainBinding.inflate(inflater, container, false)

        viewModel.requestShibes()
        viewModel.errorNotification.observe(viewLifecycleOwner) { errorText ->
            errorText?.let {
                Snackbar.make(binding.root, "Error: $errorText", Snackbar.LENGTH_LONG).show()
                viewModel.errorNotification.value = null
            }
        }

        pageAdapter = PageAdapter(
            this,
            listOf(
                "ALL" to ImagePageFragment(),
                "FAVORITE" to FavoritesFragment(),
            )
        )
        binding.viewPager.adapter = pageAdapter
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = pageAdapter.getTitle(position)
        }.attach()

        return binding.root
    }

    class PageAdapter(
        mainFragment: Fragment,
        private val pages: List<Pair<String, Fragment>>,
    ) : FragmentStateAdapter(mainFragment) {
        override fun getItemCount() = pages.size

        override fun createFragment(position: Int) = pages[position].second

        fun getTitle(position: Int) = pages[position].first
    }
}