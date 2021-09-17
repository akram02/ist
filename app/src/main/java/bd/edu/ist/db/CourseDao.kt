package bd.edu.ist.db

import androidx.room.*

@Dao
abstract class CourseDao {

    @Update
    abstract suspend fun update(courseEntity: CourseEntity)

    @Query("SELECT * FROM course")
    abstract suspend fun getAll(): List<CourseEntity>

    @Query("SELECT * FROM course WHERE department = :department order by major")
    abstract suspend fun getAllByDepartment(department: String): List<CourseEntity>
}