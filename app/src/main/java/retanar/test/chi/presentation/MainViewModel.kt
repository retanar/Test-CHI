package retanar.test.chi.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
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
    private var _userAddedNotification = MutableLiveData<Boolean?>(null)
    val userAddedNotification: LiveData<Boolean?>
        get() = _userAddedNotification

    fun addNewUser(name: String, dateOfBirth: String) {
        try {
            viewModelScope.launch {
                dao.insert(UserEntity(name = name, dateOfBirth = dateOfBirth))
            }
            _userAddedNotification.value = true
        } catch (e: Exception) {
            _userAddedNotification.value = false
        }
    }

    fun clearUserAddedNotification() {
        _userAddedNotification.value = null
    }

    fun updateUser(updatedUser: UserEntity) {
        viewModelScope.launch {
            dao.updateUser(updatedUser)
        }
    }

    fun removeUser(user: UserEntity) {
        viewModelScope.launch {
            dao.deleteUser(user)
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