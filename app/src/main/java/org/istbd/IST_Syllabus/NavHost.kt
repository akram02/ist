package org.istbd.IST_Syllabus

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.google.accompanist.pager.ExperimentalPagerApi
import org.istbd.IST_Syllabus.db.CourseEntity


@ExperimentalPagerApi
@Composable
fun PrepareNavHost(
    navController: NavHostController,
    innerPaddingModifier: PaddingValues,
    courses: List<CourseEntity>
) {
    NavHost(
        navController = navController,
        startDestination = TabSections.CSE.route,
        modifier = Modifier.padding(innerPaddingModifier)
    ) {
        composable(TabSections.CSE.route) {
            Department(
                tabData = tab,
                department="CSE",
                courses = courses
            )
        }
        composable(TabSections.BBA.route) {
            Department(
                tabData = tab,
                department="BBA",
                courses = courses
            )
        }
        composable(TabSections.ECE.route) {
            Department(
                tabData = tab,
                department="ECE",
                courses = courses
            )
        }
        composable(TabSections.CREDIT.route) {
            CreditComponent()
        }
    }
}
