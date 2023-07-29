package retanar.test.chi.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.core.os.bundleOf
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
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
            onItemClick = ::openDetailsFragment,
            onUserChecked = viewModel::updateUser,
            onItemLongClick = ::openRemoveDialog,
        )
        binding.recycler.layoutManager = LinearLayoutManager(context)
        binding.recycler.adapter = adapter

        viewModel.allUsers.observe(viewLifecycleOwner) { usersList ->
            adapter.submitList(usersList)
        }
        viewModel.userAddedNotification.observe(viewLifecycleOwner) { wasAdded ->
            wasAdded?.let {
                Snackbar.make(
                    binding.root,
                    if (wasAdded)
                        "User was added successfully"
                    else
                        "An error occurred, user was not added",
                    Snackbar.LENGTH_SHORT
                ).show()
                viewModel.clearUserAddedNotification()
            }
        }

        return binding.root
    }

    private fun openRemoveDialog(userPosition: Int) {
        val user = viewModel.allUsers.value?.get(userPosition) ?: return
        AlertDialog.Builder(requireContext())
            .setTitle("Remove ${user.name}?")
            .setPositiveButton("YES") { _, _ -> viewModel.removeUser(user) }
            .setNegativeButton("NO", null)
            .show()
    }

    private fun openDetailsFragment(userPosition: Int) {
        val entity = viewModel.allUsers.value?.get(userPosition) ?: return
        val args = bundleOf(
            "name" to entity.name,
            "dateOfBirth" to entity.dateOfBirth,
            "age" to entity.age,
            "isStudent" to entity.isStudent,
            "description" to entity.description,
        )
        findNavController().navigate(R.id.action_mainFragment_to_userDetailsFragment, args)
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.main_menu, menu)
    }

    override fun onMenuItemSelected(menuItem: MenuItem) = when (menuItem.itemId) {
        R.id.actionAddUser -> {
            findNavController().navigate(R.id.action_mainFragment_to_addUserFragment)
            true
        }

        R.id.actionSorting -> {
            openSortingDialog()
            true
        }

        else -> false
    }

    private fun openSortingDialog() {
        AlertDialog.Builder(requireContext())
            .setTitle("Sort by")
            .setItems(R.array.sorting_types) { _, index ->
                viewModel.setSortingType(index)
            }
            .show()
    }
}