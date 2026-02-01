package edu.ucne.RegistroDeEstudiantes.presentation.navigation

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.DrawerState
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import kotlinx.coroutines.launch

@Composable
fun DrawerMenu(
    drawerState: DrawerState,
    navHostController: NavHostController,
    content: @Composable () -> Unit
) {
    val selectedItem = remember { mutableStateOf("Estudiantes") }
    val scope = rememberCoroutineScope()


    val currentBackStackEntry = navHostController.currentBackStackEntryAsState()
    val currentRoute = currentBackStackEntry.value?.destination?.route

    val drawerTitle = when {
        currentRoute?.contains("TipoPenalidad") == true -> "Lista de Penalidades"
        currentRoute?.contains("Asignatura") == true -> "Lista de Asignaturas"
        else -> "Registro de Estudiantes"
    }

    fun handleItemClick(destination: Screen, item: String) {
        navHostController.navigate(destination) {
            launchSingleTop = true
        }
        selectedItem.value = item
        scope.launch { drawerState.close() }
    }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(
                modifier = Modifier.width(280.dp)
            ) {
                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = drawerTitle,
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = Color.DarkGray,
                    modifier = Modifier.padding(16.dp)
                )

                HorizontalDivider()
                Spacer(modifier = Modifier.height(16.dp))

                LazyColumn {
                    item {
                        DrawerItem(
                            title = "Estudiantes",
                            icon = Icons.Filled.Person,
                            isSelected = selectedItem.value == "Estudiantes"
                        ) {
                            handleItemClick(Screen.EstudianteList, it)
                        }

                        DrawerItem(
                            title = "Asignaturas",
                            icon = Icons.Filled.Description,
                            isSelected = selectedItem.value == "Asignaturas"
                        ) {
                            handleItemClick(Screen.AsignaturaList, it)
                        }

                        DrawerItem(
                            title = "Tipos de Penalidades",
                            icon = Icons.Filled.Warning,
                            isSelected = selectedItem.value == "Tipos de Penalidades"
                        ) {
                            handleItemClick(Screen.TipoPenalidadList, it)
                        }
                    }
                }
            }
        }
    ) {
        content()
    }
}