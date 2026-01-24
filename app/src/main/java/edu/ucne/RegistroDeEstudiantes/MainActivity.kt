package edu.ucne.RegistroDeEstudiantes

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import edu.ucne.RegistroDeEstudiantes.presentation.navigation.DrawerMenu
import edu.ucne.RegistroDeEstudiantes.presentation.navigation.MainNavHost
import edu.ucne.RegistroDeEstudiantes.ui.theme.RegistroDeEstudiantesTheme
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RegistroDeEstudiantesTheme {
                val navController = rememberNavController()
                val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
                val scope = rememberCoroutineScope()

                DrawerMenu(
                    drawerState = drawerState,
                    navHostController = navController
                ) {
                    Scaffold(
                        topBar = {
                            TopAppBar(
                                title = { Text("Registro de Estudiantes") },
                                navigationIcon = {
                                    IconButton(onClick = {
                                        scope.launch {
                                            drawerState.open()
                                        }
                                    }) {
                                        Icon(
                                            imageVector = Icons.Default.Menu,
                                            contentDescription = "Abrir menú"
                                        )
                                    }
                                }
                            )
                        }
                    ) { padding ->
                        androidx.compose.foundation.layout.Box(
                            modifier = androidx.compose.ui.Modifier.padding(padding)
                        ) {
                            MainNavHost(navHostController = navController)
                        }
                    }
                }
            }
        }
    }
}