package retanar.test.chi.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import retanar.test.chi.data.ShibeEntity
import retanar.test.chi.data.shibeApiService

class MainViewModel : ViewModel() {

    private val internalList = mutableListOf<ShibeEntity>()
    val shibeList = MutableLiveData<List<ShibeEntity>>(emptyList())
    val favorites = shibeList.map { list ->
        list.filter(ShibeEntity::isFavorite)
    }
    val errorNotification = MutableLiveData<String?>(null)

    fun requestShibes() {
        if (!shibeList.value.isNullOrEmpty()) return
        viewModelScope.launch {
            try {
                val resp = shibeApiService.getShibes()
                if (resp.isSuccessful) {
                    internalList.addAll(resp.body()!!.map { url ->
                        ShibeEntity(url)
                    })
                    updatePublicList()
                    errorNotification.value = null
                } else {
                    errorNotification.value = "Response was not successful"
                }
            } catch (e: Exception) {
                errorNotification.value = e.message
            }
        }
    }

    // change by unique url
    fun changeFavorite(url: String) {
        val index = internalList.indexOfFirst { it.url == url }
        val shibe = internalList.getOrNull(index) ?: return
        internalList[index] = shibe.copy(isFavorite = !shibe.isFavorite)
        updatePublicList()
    }

    private fun updatePublicList() {
        shibeList.value = internalList.toList()
    }
}