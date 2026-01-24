package edu.ucne.RegistroDeEstudiantes.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import edu.ucne.RegistroDeEstudiantes.presentation.students.edit.EditEstudianteScreen
import edu.ucne.RegistroDeEstudiantes.presentation.students.list.ListEstudianteScreen
import edu.ucne.RegistroDeEstudiantes.presentation.asignaturas.edit.EditAsignaturaScreen
import edu.ucne.RegistroDeEstudiantes.presentation.asignaturas.list.ListAsignaturaScreen

@Composable
fun MainNavHost(
    navHostController: NavHostController
) {
    NavHost(
        navController = navHostController,
        startDestination = Screen.EstudianteList
    ) {

        composable<Screen.EstudianteList> {
            ListEstudianteScreen(
                onNavigateToEdit = { estudianteId ->
                    navHostController.navigate(Screen.EditEstudiante(estudianteId = estudianteId))
                }
            )
        }

        composable<Screen.EditEstudiante> {
            val args = it.toRoute<Screen.EditEstudiante>()
            EditEstudianteScreen(
                estudianteId = args.estudianteId,
                onNavigateBack = {
                    navHostController.navigateUp()
                }
            )
        }


        composable<Screen.AsignaturaList> {
            ListAsignaturaScreen(
                onAddAsignatura = {
                    navHostController.navigate(Screen.EditAsignatura(asignaturaId = null))
                },
                onSelectAsignatura = { asignaturaId ->
                    navHostController.navigate(Screen.EditAsignatura(asignaturaId = asignaturaId))
                }
            )
        }

        composable<Screen.EditAsignatura> {
            val args = it.toRoute<Screen.EditAsignatura>()
            EditAsignaturaScreen(
                asignaturaId = args.asignaturaId,
                onNavigateBack = {
                    navHostController.navigateUp()
                }
            )
        }
    }
}
