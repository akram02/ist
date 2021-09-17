package bd.edu.ist

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import bd.edu.ist.db.CourseEntity
import bd.edu.ist.db.ISTDatabase

class MainViewModel: ViewModel() {
    val course = MutableLiveData<List<CourseEntity>>()

    fun getCourse() {
        viewModelScope.launch {
            course.value = ISTDatabase.getInstance().courseDao.getAll()
            println(course.value)
        }
    }
}