package bd.edu.ist

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModelProvider
import com.google.accompanist.pager.ExperimentalPagerApi
import bd.edu.ist.db.CourseEntity
import bd.edu.ist.ui.theme.ISTTheme

var tab = arrayOf(
    "1st Semester" to "1st",
    "2nd Semester" to "2nd",
    "3rd Semester" to "3rd",
    "4th Semester" to "4th",
    "5th Semester" to "5th",
    "6th Semester" to "6th",
    "7th Semester" to "7th",
    "8th Semester" to "8th"
)

class MainActivity : AppCompatActivity() {
    private val viewModel by lazy { ViewModelProvider(this).get(MainViewModel::class.java) }

    @ExperimentalPagerApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.getCourse()
        viewModel.course.observe(this) { courses ->
            setContent {
                ISTTheme {
                    App(courses)
                }
            }
        }
    }
}

@ExperimentalPagerApi
@Composable
fun Department(tabData: Array<Pair<String, String>>, department: String, courses: List<CourseEntity>) {
    Column(modifier = Modifier.fillMaxSize()) {
        PagerComponent(
            tabData,
            department,
            courses
        )
    }
}

@ExperimentalPagerApi
@Preview
@Composable
fun DrawerPreview() {
    ISTTheme {
//        Department(
//            tabData = tab,
//            semesters = cseSemesters,
//            icon = R.drawable.cse_button
//        )
    }
}