package co.composables.roomtutorial

import android.app.Application
import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Entity(tableName = "person")
data class Person(
    @PrimaryKey(autoGenerate = true) val uid: Int = 0,
    @ColumnInfo(name = "first_name") val firstName: String,
    @ColumnInfo(name = "last_name") val lastName: String,
)

@Dao
interface PersonDao {
    @Query("SELECT * FROM person")
    fun getAllPeople(): List<Person>

    @Insert
    fun insertPeople(vararg people: Person)
}

@Database(entities = [Person::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun personDao(): PersonDao
}


object MyDatabase {

    private var database: AppDatabase? = null

    fun getInstance(application: Application): AppDatabase {
        if (database == null) {
            database =
                Room.databaseBuilder(application, AppDatabase::class.java, "database-name").build()
        }
        return requireNotNull(database)
    }
}