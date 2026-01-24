package edu.ucne.RegistroDeEstudiantes.presentation.students.edit

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun EditEstudianteScreen(
    estudianteId: Int?,
    viewModel: EditEstudianteViewModel = hiltViewModel(),
    onNavigateBack: () -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(estudianteId) {
        if (estudianteId != null && estudianteId > 0) {
            viewModel.onEvent(EditEstudianteUiEvent.LoadEstudiante(estudianteId))
        }
    }

    LaunchedEffect(state.isSaved) {
        if (state.isSaved) {
            onNavigateBack()
        }
    }

    EditEstudianteBody(
        state = state,
        onEvent = viewModel::onEvent,
        onNavigateBack = onNavigateBack
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun EditEstudianteBody(
    state: EditEstudianteUiState,
    onEvent: (EditEstudianteUiEvent) -> Unit,
    onNavigateBack: () -> Unit = {}
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = if (state.isNew) "Nuevo Estudiante" else "Editar Estudiante"
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Volver"
                        )
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(paddingValues = padding)
                .padding(all = 16.dp)
        ) {
            OutlinedTextField(
                value = state.nombres,
                onValueChange = { onEvent(EditEstudianteUiEvent.NombresChanged(value = it)) },
                label = { Text(text = "Nombres") },
                isError = state.nombresError != null,
                modifier = Modifier.fillMaxWidth()
            )
            if (state.nombresError != null) {
                Text(
                    text = state.nombresError,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(start = 16.dp, top = 4.dp)
                )
            }

            Spacer(Modifier.height(height = 12.dp))

            OutlinedTextField(
                value = state.email,
                onValueChange = { onEvent(EditEstudianteUiEvent.EmailChanged(value = it)) },
                label = { Text(text = "Email") },
                isError = state.emailError != null,
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
            )
            if (state.emailError != null) {
                Text(
                    text = state.emailError,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(start = 16.dp, top = 4.dp)
                )
            }

            Spacer(Modifier.height(height = 12.dp))

            OutlinedTextField(
                value = state.edad,
                onValueChange = { onEvent(EditEstudianteUiEvent.EdadChanged(value = it)) },
                label = { Text(text = "Edad") },
                isError = state.edadError != null,
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )
            if (state.edadError != null) {
                Text(
                    text = state.edadError,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(start = 16.dp, top = 4.dp)
                )
            }

            Spacer(Modifier.height(height = 24.dp))

            Row {
                Button(
                    onClick = { onEvent(EditEstudianteUiEvent.Save) },
                    enabled = !state.isSaving,
                    modifier = Modifier.weight(1f)
                ) {
                    Text(text = if (state.isSaving) "Guardando..." else "Guardar")
                }

                if (!state.isNew) {
                    Spacer(Modifier.width(width = 8.dp))

                    OutlinedButton(
                        onClick = { onEvent(EditEstudianteUiEvent.Delete) },
                        enabled = !state.isDeleting,
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(text = if (state.isDeleting) "Eliminando..." else "Eliminar")
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun NewEstudiantePreview() {
    MaterialTheme {
        EditEstudianteBody(
            state = EditEstudianteUiState(
                isNew = true
            ),
            onEvent = {}
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun EditEstudiantePreview() {
    MaterialTheme {
        EditEstudianteBody(
            state = EditEstudianteUiState(
                nombres = "Juan Pérez",
                email = "juan@example.com",
                edad = "20",
                isNew = false
            ),
            onEvent = {}
        )
    }
}