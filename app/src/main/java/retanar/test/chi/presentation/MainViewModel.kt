package retanar.test.chi.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import retanar.test.chi.data.shibeApiService

class MainViewModel : ViewModel() {

    val shibeList = MutableLiveData(emptyList<String>())
    val errorNotification = MutableLiveData<String?>(null)

    fun requestShibes() {
        if (!shibeList.value.isNullOrEmpty()) return
        viewModelScope.launch {
            try {
                val resp = shibeApiService.getShibes()
                if (resp.isSuccessful) {
                    shibeList.value = resp.body()
                }
            } catch (e: Exception) {
                errorNotification.value = e.message
            }
        }
    }
}