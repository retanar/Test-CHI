package retanar.test.chi.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
class UserEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    val name: String,
    @ColumnInfo("date_of_birth")
    val dateOfBirth: String,
    @ColumnInfo("is_student")
    val isStudent: Boolean,
)
