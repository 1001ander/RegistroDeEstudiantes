package edu.ucne.RegistroDeEstudiantes.presentation.students

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.NavType
import edu.ucne.RegistroDeEstudiantes.presentation.students.list.ListEstudianteScreen
import edu.ucne.RegistroDeEstudiantes.presentation.students.edit.EditEstudianteScreen

sealed class EstudianteDestination(val route: String) {
    data object List : EstudianteDestination("estudiante/list")
    data object Edit : EstudianteDestination("estudiante/edit/{estudianteId}") {
        fun createRoute(estudianteId: Int?) =
            "estudiante/edit/${estudianteId ?: 0}"
    }
}

@Composable
fun EstudianteScreen(
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = EstudianteDestination.List.route
    ) {
        composable(EstudianteDestination.List.route) {
            ListEstudianteScreen(
                onNavigateToEdit = { estudianteId ->
                    navController.navigate(
                        EstudianteDestination.Edit.createRoute(estudianteId)
                    )
                }
            )
        }

        composable(
            route = EstudianteDestination.Edit.route,
            arguments = listOf(
                navArgument("estudianteId") {
                    type = NavType.IntType
                    defaultValue = 0
                }
            )
        ) { backStackEntry ->
            val estudianteId = backStackEntry.arguments?.getInt("estudianteId")

            EditEstudianteScreen(
                estudianteId = if (estudianteId == 0) null else estudianteId,
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}