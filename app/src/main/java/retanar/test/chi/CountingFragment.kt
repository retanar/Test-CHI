package retanar.test.chi

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

private const val ARG_COUNT = "count"

class CountingFragment : Fragment() {

    private var counter = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            counter = it.getInt(ARG_COUNT)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_counting, container, false)
    }

    companion object {
        @JvmStatic
        fun newInstance(count: Int) = CountingFragment().apply {
            arguments = Bundle().apply {
                putInt(ARG_COUNT, count)
            }
        }
    }
}