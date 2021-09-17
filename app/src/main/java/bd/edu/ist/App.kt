package bd.edu.ist

import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.res.colorResource
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.insets.ProvideWindowInsets
import com.google.accompanist.pager.ExperimentalPagerApi
import bd.edu.ist.db.CourseEntity
import bd.edu.ist.ui.theme.ISTTheme


@ExperimentalPagerApi
@Composable
fun App(courses: List<CourseEntity>) {
    ProvideWindowInsets {
        ISTTheme {
            val tabs = remember { TabSections.values() }
            val navController = rememberNavController()
            Scaffold(
                bottomBar = {
                    Surface(color = colorResource(id = R.color.ist)) {
                        BottomBar(navController = navController, tabs = tabs)
                    }
                }
            ) { innerPaddingModifier ->
                PrepareNavHost(navController, innerPaddingModifier, courses)
            }
        }
    }
}
