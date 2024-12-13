package com.reminder.presentation.navigation

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavDeepLink
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.reminder.presentation.common.theme.ReminderTheme
import com.reminder.presentation.features.add_task.AddTaskScreenRoot
import com.reminder.presentation.features.task_details.TaskDetailsScreenRoot
import com.reminder.presentation.features.task_list.TaskListScreenRoot
import kotlin.reflect.KType

@Composable
fun AppNavigationRoot(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    AppNavigation(
        navController,
        modifier
    )
}

@Composable
fun AppNavigation(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
    ) {
        NavHost(
            navController = navController,
            startDestination = Screen.TaskList,
            modifier = Modifier.weight(1f)
        ) {
            composableNoTransition<Screen.TaskList> {
                TaskListScreenRoot(navController)
            }
            composableNoTransition<Screen.TaskDetails> {
                TaskDetailsScreenRoot()
            }
            composableNoTransition<Screen.AddTask> {
                AddTaskScreenRoot(navController)
            }
        }
    }
}

@Preview
@Composable
fun AppNavigationPreview(modifier: Modifier = Modifier) {
    ReminderTheme {
        AppNavigation(
            navController = rememberNavController()
        )
    }
}

inline fun <reified T : Any> NavGraphBuilder.composableNoTransition(
    typeMap: Map<KType, @JvmSuppressWildcards NavType<*>> = emptyMap(),
    deepLinks: List<NavDeepLink> = emptyList(),
    noinline content: @Composable AnimatedContentScope.(NavBackStackEntry) -> Unit
) {
    composable<T>(
        typeMap = typeMap,
        deepLinks = deepLinks,
        enterTransition = { EnterTransition.None },
        exitTransition = { ExitTransition.None },
        content = content
    )
}
