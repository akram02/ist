package org.istbd.IST_Syllabus

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import org.istbd.IST_Syllabus.db.CourseEntity
import org.istbd.IST_Syllabus.db.ISTDatabase

class MainViewModel: ViewModel() {
    val course = MutableLiveData<List<CourseEntity>>()

    fun getCourse() {
        viewModelScope.launch {
            course.value = ISTDatabase.getInstance().courseDao.getAll()
            println(course.value)
        }
    }
}