package retanar.test.chi

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.activity.addCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.navigation.fragment.findNavController

class CountingFragment : Fragment() {

    private var counter = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_counting, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val textView = view.findViewById<TextView>(R.id.fragmentCounterTextView)
        val button = view.findViewById<Button>(R.id.incrementButton)

        arguments?.let { counter = it.getInt("counter") }
        savedInstanceState?.let { counter = it.getInt("counter") }

        // Override on back press to pass counter value
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            setFragmentResult("req", bundleOf("counter" to counter))
            findNavController().popBackStack()
        }

        textView.text = counter.toString()
        button.setOnClickListener {
            incrementCounter(textView)
        }

        (activity as AppCompatActivity?)?.supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putInt("counter", counter)
    }

    private fun incrementCounter(textView: TextView) {
        counter += 1
        textView.text = counter.toString()
    }
}