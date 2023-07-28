package retanar.test.chi.presentation

import android.app.DatePickerDialog
import android.app.Dialog
import android.icu.util.Calendar
import android.os.Bundle
import android.widget.DatePicker
import androidx.fragment.app.DialogFragment
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class DatePickerFragment(
    private val onDateSet: (String) -> Unit,
) : DialogFragment(), DatePickerDialog.OnDateSetListener {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        return DatePickerDialog(requireContext(), this, year, month, day)
    }

    // Returned month numbers start with 0
    override fun onDateSet(datePicker: DatePicker?, year: Int, month: Int, day: Int) {
        onDateSet(LocalDate.of(year, month + 1, day).format(DateTimeFormatter.ofPattern("dd.MM.yyyy")))
    }
}