package retanar.test.chi.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import retanar.test.chi.database.UsersDatabase
import retanar.test.chi.databinding.FragmentAddUserBinding

// TODO: add date picker
class AddUserFragment : Fragment() {

    private val viewModel: MainViewModel by activityViewModels {
        MainViewModelFactory(UsersDatabase.getDatabase(requireContext()).usersDao)
    }
    private lateinit var binding: FragmentAddUserBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentAddUserBinding.inflate(inflater, container, false)

        binding.addButton.setOnClickListener {
            viewModel.addNewUser(
                binding.userNameEditText.text.toString(),
                binding.dateEditText.text.toString(),
            )
            findNavController().popBackStack()
        }

        return binding.root
    }

}