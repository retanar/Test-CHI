package retanar.test.chi.presentation

import android.os.Bundle
import android.view.*
import androidx.core.os.bundleOf
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import retanar.test.chi.R
import retanar.test.chi.database.UsersDatabase
import retanar.test.chi.databinding.FragmentMainBinding

class MainFragment : Fragment(), MenuProvider {

    private val viewModel: MainViewModel by activityViewModels {
        MainViewModelFactory(UsersDatabase.getDatabase(requireContext()).usersDao)
    }
    private lateinit var binding: FragmentMainBinding
    private lateinit var adapter: UsersListAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        // Add menu for this fragment
        requireActivity().addMenuProvider(this, viewLifecycleOwner)

        adapter = UsersListAdapter(
            onItemClickListener = {
                findNavController().navigate(R.id.action_mainFragment_to_userDetailsFragment, bundleOf(/*TODO*/))
            },
            onUserChecked = viewModel::updateUser,
        )
        binding.recycler.layoutManager = LinearLayoutManager(context)
        binding.recycler.adapter = adapter

        viewModel.allUsers.observe(viewLifecycleOwner) { usersList ->
            adapter.submitList(usersList)
        }

        return binding.root
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.main_menu, menu)
    }

    override fun onMenuItemSelected(menuItem: MenuItem) = when (menuItem.itemId) {
        R.id.actionAddUser -> {
            findNavController().navigate(R.id.action_mainFragment_to_addUserFragment)
            true
        }

        else -> false
    }
}