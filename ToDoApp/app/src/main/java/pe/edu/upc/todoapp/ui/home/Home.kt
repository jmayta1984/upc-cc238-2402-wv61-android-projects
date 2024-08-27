package pe.edu.upc.todoapp.ui.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import pe.edu.upc.todoapp.ui.taskdetail.TaskDetail
import pe.edu.upc.todoapp.ui.tasklist.TaskList

@Composable
fun Home() {
    val navController = rememberNavController()

    val tasks = remember {
        mutableStateOf(emptyList<String>())
    }

    NavHost(navController = navController, startDestination = Routes.TaskList.route) {
        composable(route = Routes.TaskList.route) {
            TaskList(tasks = tasks.value, onEditTask = { description ->
                navController.navigate(route = "${Routes.TaskDetail.route}/$description")
            }) {
                navController.navigate(route = Routes.TaskDetail.routeWithoutArgument)
            }
        }

        composable(
            route = Routes.TaskDetail.routeWithArgument,
            arguments = listOf(navArgument(Routes.TaskDetail.argument) {
                type = NavType.StringType
            })
        ) { backStackEntry ->
            val description = backStackEntry.arguments?.getString(Routes.TaskDetail.argument)
            TaskDetail(task = if (description == "0") null else description) { newTask ->
                if (description == "0") {
                    tasks.value += newTask
                } else {
                    val index = tasks.value.indexOf(description)
                    val mutableList = tasks.value.toMutableList()
                    mutableList[index] = newTask
                    tasks.value = mutableList
                }

                navController.popBackStack()
            }
        }
    }
}

sealed class Routes(val route: String) {
    data object TaskList : Routes(route = "TaskList")
    data object TaskDetail : Routes(route = "TaskDetail") {
        const val routeWithArgument = "TaskDetail/{description}"
        const val argument = "description"
        const val routeWithoutArgument = "TaskDetail/0"
    }
}


@Preview
@Composable
fun HomePreview() {
    Home()
}