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
            dao.insert(UserEntity(name = name, dateOfBirth = dateOfBirth))
        }
    }

    fun updateUser(updatedUser: UserEntity) {
        viewModelScope.launch {
            dao.updateUser(updatedUser)
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