package retanar.test.chi

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.navigation.fragment.findNavController

class MainFragment : Fragment() {

    private lateinit var textView: TextView
    private var counter = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        textView = view.findViewById(R.id.mainCounterTextView)
        val button = view.findViewById<Button>(R.id.openFragmentButton)

        savedInstanceState?.let {
            counter = it.getInt("counter")
            textView.text = counter.toString()
        }

        setFragmentResultListener("req") { _, bundle ->
            counter = bundle.getInt("counter")
            textView.text = counter.toString()
        }

        button.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_countingFragment, bundleOf("counter" to counter))
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putInt("counter", counter)
    }
}