package bd.edu.ist

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import bd.edu.ist.db.CourseEntity
import bd.edu.ist.db.ISTDatabase

class MainViewModel: ViewModel() {
    val courses = MutableLiveData<List<CourseEntity>>()
    val favorites = MutableLiveData<List<CourseEntity>>()
    val semesters = MutableLiveData<List<String>>()
    val departments = MutableLiveData<List<String>>()

    fun getCourse() {
        viewModelScope.launch {
            courses.value = ISTDatabase.getInstance().courseDao.getAll()
        }
    }
    fun getCourse(department: String) {
        viewModelScope.launch {
            courses.value = ISTDatabase.getInstance().courseDao.getAllByDepartment(department)
        }
    }
    fun getSemesters(department: String) {
        viewModelScope.launch {
            semesters.value = ISTDatabase.getInstance().courseDao.getAllSemesterByDepartment(department)
        }
    }
    fun getDepartments() {
        viewModelScope.launch {
            departments.value = ISTDatabase.getInstance().courseDao.getAllDepartment()
        }
    }

    fun getFavorites() {
        viewModelScope.launch {
            favorites.value = ISTDatabase.getInstance().courseDao.getAllFavorites()
        }
    }

    fun updateCourse(course: CourseEntity, department: String = "") {
        viewModelScope.launch {
            ISTDatabase.getInstance().courseDao.update(course)
            courses.value = ISTDatabase.getInstance().courseDao.getAllByDepartment(department)
            semesters.value = ISTDatabase.getInstance().courseDao.getAllSemesterByDepartment(department)
            favorites.value = ISTDatabase.getInstance().courseDao.getAllFavorites()
        }
    }
}