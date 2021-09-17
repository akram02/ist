package bd.edu.ist

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.ScrollableTabRow
import androidx.compose.material.Surface
import androidx.compose.material.Tab
import androidx.compose.material.Text
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import bd.edu.ist.ui.theme.BLACK
import bd.edu.ist.ui.theme.ISTTheme
import bd.edu.ist.ui.theme.WHITE
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.launch

class DepartmentActivity : AppCompatActivity() {
    private val viewModel by lazy { ViewModelProvider(this).get(MainViewModel::class.java) }

    @ExperimentalPagerApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val department = intent.getStringExtra("department") ?: ""

        viewModel.getCourse(department)
        viewModel.getSemesters(department)

        setContent {
            val courses = viewModel.courses.observeAsState()
            val semesters = viewModel.semesters.observeAsState()
            if (!courses.value.isNullOrEmpty() && !semesters.value.isNullOrEmpty()) {

                val tabData = viewModel.semesters.value?.map {
                    "$it Semester" to it
                }?.toTypedArray() ?: emptyArray()

                ISTTheme {
                    val pagerState = rememberPagerState(pageCount = tabData.size)
                    val tabIndex = pagerState.currentPage
                    val coroutineScope = rememberCoroutineScope()

                    Column {
                        ScrollableTabRow(selectedTabIndex = tabIndex, edgePadding = 0.dp) {
                            tabData.forEachIndexed { index, text ->
                                Surface(color = colorResource(id = R.color.ist)) {
                                    Tab(
                                        selected = tabIndex == index,
                                        onClick = {
                                            coroutineScope.launch { pagerState.animateScrollToPage(index) }
                                        },
                                        text = { Text(text = text.first) },
                                        unselectedContentColor = WHITE,
                                        selectedContentColor = BLACK
                                    )
                                }
                            }
                        }
                        HorizontalPager(state = pagerState, modifier = Modifier.weight(1f)) { index ->
                            Column(
                                modifier = Modifier.fillMaxSize(),
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                val filteredCourses = courses.value!!.filter { courseEntity ->
                                    courseEntity.department == department && courseEntity.semester == tabData[index].second
                                }
                                CourseListComponent(filteredCourses) { course ->
                                    course.favorite = course.favorite==false || course.favorite==null
                                    viewModel.updateCourse(course, department)
                                }
                            }
                        }
                    }
                }
            }
        }

    }
}
