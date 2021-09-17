package bd.edu.ist

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.ScrollableTabRow
import androidx.compose.material.Surface
import androidx.compose.material.Tab
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.launch
import bd.edu.ist.db.CourseEntity
import bd.edu.ist.ui.theme.BLACK
import bd.edu.ist.ui.theme.WHITE


@ExperimentalPagerApi
@Composable
fun PagerComponent(tabData: Array<Pair<String, String>>, department: String, courses: List<CourseEntity>) {
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
                SemesterComponent(tabData[index], courses, department)
            }
        }
    }
}