package edu.ucne.RegistroDeEstudiantes

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import edu.ucne.RegistroDeEstudiantes.presentation.students.EstudianteScreen
import edu.ucne.RegistroDeEstudiantes.ui.theme.RegistroDeEstudiantesTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RegistroDeEstudiantesTheme {
                val navController = rememberNavController()
                EstudianteScreen(navController)
            }
        }
    }
}