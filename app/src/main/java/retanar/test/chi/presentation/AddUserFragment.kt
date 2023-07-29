package retanar.test.chi.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import retanar.test.chi.database.UsersDatabase
import retanar.test.chi.databinding.FragmentAddUserBinding

class AddUserFragment : Fragment() {

    private val viewModel: MainViewModel by activityViewModels {
        MainViewModelFactory(UsersDatabase.getDatabase(requireContext()).usersDao)
    }
    private lateinit var binding: FragmentAddUserBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentAddUserBinding.inflate(inflater, container, false)

        binding.dateEditText.setOnClickListener {
            DatePickerFragment {
                binding.dateEditText.setText(it)
            }.show(parentFragmentManager, null)
        }

        binding.addButton.setOnClickListener {
            if (binding.userNameEditText.text.isBlank() || binding.dateEditText.text.isBlank()) {
                Snackbar.make(it, "Name and date should not be blank.", Snackbar.LENGTH_LONG).show()
                return@setOnClickListener
            }
            viewModel.addNewUser(
                binding.userNameEditText.text.toString(),
                binding.dateEditText.text.toString(),
                binding.userDescriptionEditText.text.toString(),
            )
            findNavController().popBackStack()
        }

        return binding.root
    }

}