package retanar.test.chi.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import retanar.test.chi.database.UserEntity
import retanar.test.chi.database.UsersDao
import java.time.LocalDate
import java.time.format.DateTimeFormatter

// Should correspond to R.array.sorting_types
enum class SortingType(val comparator: Comparator<UserEntity>?) {
    DEFAULT(null),
    NAME(compareBy(UserEntity::name)),

    // Sorting by days since epoch - more recent birthdate first
    AGE(compareBy {
        -LocalDate.parse(it.dateOfBirth, DateTimeFormatter.ofPattern("dd.MM.yyyy")).toEpochDay()
    }),
    STUDENT(compareBy({ !it.isStudent }, UserEntity::name)),
}

class MainViewModel(
    private val dao: UsersDao,
) : ViewModel() {

    private val sortingType = MutableStateFlow(SortingType.DEFAULT)

    // Combine database updates with sort type updates
    val allUsers = dao.getAllUsersFlow().combine(sortingType) { users, sortingType ->
        sortingType.comparator?.let { comparator ->
            users.sortedWith(comparator)
        } ?: users
    }.asLiveData()

    private val _userAddedNotification = MutableLiveData<Boolean?>(null)
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

    fun setSortingType(index: Int) {
        sortingType.value = SortingType.values().getOrNull(index) ?: SortingType.DEFAULT
    }
}

class MainViewModelFactory(
    private val dao: UsersDao
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainViewModel(dao) as T
    }
}