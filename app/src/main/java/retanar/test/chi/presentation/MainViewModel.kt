package retanar.test.chi.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import retanar.test.chi.database.UserEntity
import retanar.test.chi.database.UsersDao

class MainViewModel(
    private val dao: UsersDao,
) : ViewModel() {

    val allUsers = dao.getAllUsersFlow().asLiveData()

    fun addNewUser(name: String, dateOfBirth: String) {
        viewModelScope.launch {
            dao.insert(UserEntity(id = 0, name = name, dateOfBirth = dateOfBirth, isStudent = false))
        }
    }
}

class MainViewModelFactory(
    private val dao: UsersDao
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainViewModel(dao) as T
    }
}