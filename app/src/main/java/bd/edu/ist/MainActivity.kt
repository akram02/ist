package bd.edu.ist

import android.content.Context
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.People
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import bd.edu.ist.ui.theme.ISTTheme
import com.google.accompanist.insets.navigationBarsWithImePadding
import com.google.accompanist.insets.statusBarsPadding
import com.google.accompanist.pager.ExperimentalPagerApi
import splitties.activities.start

class MainActivity : AppCompatActivity() {
    private val viewModel by lazy { ViewModelProvider(this).get(MainViewModel::class.java) }

    @ExperimentalPagerApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.getCourse()
        viewModel.getDepartments()
        viewModel.getFavorites()

        setContent {
            ISTTheme {
                val navController = rememberNavController()
                val navItems = listOf(
                    NavScreen.Favorite,
                    NavScreen.Category,
                    NavScreen.Credit
                )

                val departmentState = viewModel.departments.observeAsState()
                val favoritesState = viewModel.favorites.observeAsState()
                val context = LocalContext.current
                Scaffold(
                    bottomBar = {
                        BottomNavigationComponent(navController, navItems)
                    }
                ) {
                    NavHost(
                        navController = navController,
                        startDestination = NavScreen.Favorite.route
                    ) {
                        composable(NavScreen.Favorite.route) {
                            if (favoritesState.value.isNullOrEmpty()) {
                                Column(
                                    modifier = Modifier.fillMaxSize(),
                                    verticalArrangement = Arrangement.Center,
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Text(text = "Add some course to favorite")
                                }
                            } else {
                                Column(modifier = Modifier.padding(bottom = 40.dp)){
                                    CourseListComponent(courses = favoritesState.value!!)  { course ->
                                        course.favorite = course.favorite==false || course.favorite==null
                                        viewModel.updateCourse(course)
                                    }
                                }
                            }
                        }
                        composable(NavScreen.Category.route) {
                            CategoryComponent(departmentState, context)
                        }
                        composable(NavScreen.Credit.route) {
                            CreditComponent()
                        }
                    }
                }
            }
        }
    }

    override fun onRestart() {
        super.onRestart()
        viewModel.getCourse()
        viewModel.getDepartments()
        viewModel.getFavorites()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}


@Composable
private fun CategoryComponent(
    departmentState: State<List<String>?>,
    context: Context
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        items(departmentState.value ?: emptyList()) { department ->
            Button(onClick = {
                context.start<DepartmentActivity> {
                    putExtra(
                        "department",
                        department
                    )
                }
            }, modifier = Modifier.padding(20.dp)) {
                Text(text = department, fontSize = 20.sp)
            }
        }
    }
}

sealed class NavScreen(val route: String, val title: String, val icon: ImageVector) {
    object Favorite : NavScreen("favorite", "Favorite", Icons.Default.Favorite)
    object Category : NavScreen("category", "Category", Icons.Default.List)
    object Credit : NavScreen("credit", "Credit", Icons.Default.People)
}

@Composable
private fun BottomNavigationComponent(
    navController: NavHostController,
    navItems: List<NavScreen>
) {
    BottomNavigation {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        navItems.forEach { screen ->
            val isSelected = currentRoute == screen.route
            BottomNavigationItem(
                label = { Text(screen.title, fontSize = 15.sp) },
                selected = isSelected,
                onClick = { navController.navigate(screen.route) },
                icon = {
                    Image(
                        imageVector = screen.icon,
                        contentDescription = "",
                        colorFilter = ColorFilter.tint(Color.White),
                        modifier = Modifier.size(if (isSelected) 25.dp else 15.dp)
                    )
                }
            )
        }
    }
}