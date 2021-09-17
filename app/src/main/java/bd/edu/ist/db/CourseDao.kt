package bd.edu.ist.db

import androidx.room.*

@Dao
abstract class CourseDao {

    @Update
    abstract suspend fun update(courseEntity: CourseEntity)

    @Query("SELECT * FROM course")
    abstract suspend fun getAll(): List<CourseEntity>

    @Query("SELECT * FROM course WHERE department LIKE '%' || :department || '%' order by major")
    abstract suspend fun getAllByDepartment(department: String): List<CourseEntity>

    @Query("SELECT semester FROM course WHERE department LIKE '%' || :department || '%' group by semester order by major ")
    abstract suspend fun getAllSemesterByDepartment(department: String): List<String>

    @Query("SELECT department FROM course group by department")
    abstract suspend fun getAllDepartment(): List<String>

    @Query("SELECT * FROM course WHERE favorite = 1 order by title")
    abstract suspend fun getAllFavorites(): List<CourseEntity>?
}