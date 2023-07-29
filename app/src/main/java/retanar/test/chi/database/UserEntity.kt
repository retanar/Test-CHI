package retanar.test.chi.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import java.time.LocalDate
import java.time.Period
import java.time.format.DateTimeFormatter

@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val description: String,
    @ColumnInfo("date_of_birth")
    val dateOfBirth: String,
    @ColumnInfo("is_student")
    val isStudent: Boolean = false,
) {
    @delegate:Ignore
    val age by lazy {
        Period.between(
            LocalDate.parse(dateOfBirth, DateTimeFormatter.ofPattern("dd.MM.yyyy")),
            LocalDate.now(),
        ).years
    }
}
