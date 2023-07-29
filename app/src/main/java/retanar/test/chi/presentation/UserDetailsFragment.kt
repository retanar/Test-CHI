package retanar.test.chi.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import retanar.test.chi.R
import retanar.test.chi.databinding.FragmentUserDetailsBinding

class UserDetailsFragment : Fragment() {

    private lateinit var binding: FragmentUserDetailsBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentUserDetailsBinding.inflate(inflater, container, false)

        arguments?.let {
            binding.name.text = it.getString("name")
            binding.dateOfBirth.text = resources.getString(
                R.string.user_details_date_of_birth,
                it.getString("dateOfBirth")
            )
            binding.age.text = resources.getString(R.string.user_details_age, it.getInt("age"))
            binding.student.text = resources.getString(
                R.string.user_details_is_student,
                if (it.getBoolean("isStudent")) "Yes" else "No"
            )
            binding.description.text = it.getString("description")
        }

        return binding.root
    }
}