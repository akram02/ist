package org.istbd.IST_Syllabus

import androidx.compose.animation.core.SpringSpec
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.selection.selectable
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.google.accompanist.insets.navigationBarsPadding

@Composable
fun BottomBar(
    navController: NavController,
    tabs: Array<TabSections>,
) {

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val sections = remember { TabSections.values() }
    val routes = remember { sections.map { it.route } }
    if (currentRoute in routes) {
        val currentSection = sections.first { it.route == currentRoute }
        val shape: Shape = RectangleShape
        val border: BorderStroke? = null
        val elevation: Dp = 0.dp
        NavLayoutBox(
            elevation,
            shape,
            border,
            currentSection,
            routes,
            tabs,
            currentRoute,
            navController
        )
    }
}

@Composable
private fun NavLayoutBox(
    elevation: Dp,
    shape: Shape,
    border: BorderStroke?,
    currentSection: TabSections,
    routes: List<String>,
    tabs: Array<TabSections>,
    currentRoute: String?,
    navController: NavController
) {
    Box(
        modifier = Modifier
            .shadow(elevation = elevation, shape = shape, clip = false)
            .zIndex(elevation.value)
            .then(if (border != null) Modifier.border(border, shape) else Modifier)
            .clip(shape),
    ) {
        CompositionLocalProvider(content = {
            val springSpec = SpringSpec<Float>(
                stiffness = 800f,
                dampingRatio = 0.8f
            )
            BottomNavLayout(
                selectedIndex = currentSection.ordinal,
                itemCount = routes.size,
                indicator = {
                    Spacer(
                        modifier = Modifier
                            .fillMaxSize()
                            .then(BottomNavigationItemPadding)
                            .border(2.dp, Color.White, BottomNavIndicatorShape),

                    )
                },
                animSpec = springSpec,
                modifier = Modifier.navigationBarsPadding(start = false, end = false)
            ) {
                tabs.forEach { section ->
                    val selected = section == currentSection
                    NavItemBox(selected, section, currentRoute, navController, springSpec)
                }
            }
        })
    }
}

@Composable
private fun NavItemBox(
    selected: Boolean,
    section: TabSections,
    currentRoute: String?,
    navController: NavController,
    springSpec: SpringSpec<Float>
) {
    Box(
        modifier = BottomNavigationItemPadding
            .clip(BottomNavIndicatorShape)
            .selectable(selected = selected, onClick = {
                if (section.route != currentRoute) {
                    navController.navigate(section.route) {
                        launchSingleTop = true
                        restoreState = true
                        popUpTo(findStartDestination(navController.graph).id) {
                            saveState = true
                        }
                    }
                }
            }),
        contentAlignment = Alignment.Center
    ) {
        // Animate the icon/text positions within the item based on selection
        val animationProgress by animateFloatAsState(if (selected) 1f else 0f, springSpec)
        BottomNavItemLayout(
            icon = {
                Image(
                    painter = painterResource(id = section.icon),
                    contentDescription = null
                )
            },
            text = {
                Text(
                    text = section.title,
                    style = MaterialTheme.typography.button,
                    color = Color.White
                )
            },
            animationProgress = animationProgress
        )
    }
}
