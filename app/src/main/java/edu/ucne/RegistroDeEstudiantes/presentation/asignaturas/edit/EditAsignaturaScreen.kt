package edu.ucne.RegistroDeEstudiantes.presentation.asignaturas.edit

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
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
fun EditAsignaturaScreen(
    asignaturaId: Int?,
    viewModel: EditAsignaturaViewModel = hiltViewModel(),
    onNavigateBack: () -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(asignaturaId) {
        if (asignaturaId != null && asignaturaId > 0) {
            viewModel.onEvent(EditAsignaturaUiEvent.LoadAsignatura(asignaturaId))
        }
    }

    LaunchedEffect(state.isSaved) {
        if (state.isSaved) {
            onNavigateBack()
        }
    }

    LaunchedEffect(state.deleted) {
        if (state.deleted) {
            onNavigateBack()
        }
    }

    EditAsignaturaBody(
        state = state,
        onEvent = viewModel::onEvent
    )
}

@Composable
private fun EditAsignaturaBody(
    state: EditAsignaturaUiState,
    onEvent: (EditAsignaturaUiEvent) -> Unit
) {
    Scaffold { padding ->
        Column(
            modifier = Modifier
                .padding(paddingValues = padding)
                .padding(all = 16.dp)
        ) {

            OutlinedTextField(
                value = state.codigo,
                onValueChange = { onEvent(EditAsignaturaUiEvent.CodigoChanged(value = it)) },
                label = { Text(text = "Código") },
                isError = state.codigoError != null,
                modifier = Modifier.fillMaxWidth()
            )
            if (state.codigoError != null) {
                Text(
                    text = state.codigoError,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(start = 16.dp, top = 4.dp)
                )
            }

            Spacer(Modifier.height(height = 12.dp))


            OutlinedTextField(
                value = state.nombre,
                onValueChange = { onEvent(EditAsignaturaUiEvent.NombreChanged(value = it)) },
                label = { Text(text = "Nombre") },
                isError = state.nombreError != null,
                modifier = Modifier.fillMaxWidth()
            )
            if (state.nombreError != null) {
                Text(
                    text = state.nombreError,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(start = 16.dp, top = 4.dp)
                )
            }

            Spacer(Modifier.height(height = 12.dp))


            OutlinedTextField(
                value = state.aula,
                onValueChange = { onEvent(EditAsignaturaUiEvent.AulaChanged(value = it)) },
                label = { Text(text = "Aula") },
                isError = state.aulaError != null,
                modifier = Modifier.fillMaxWidth()
            )
            if (state.aulaError != null) {
                Text(
                    text = state.aulaError,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(start = 16.dp, top = 4.dp)
                )
            }

            Spacer(Modifier.height(height = 12.dp))


            OutlinedTextField(
                value = state.creditos,
                onValueChange = { onEvent(EditAsignaturaUiEvent.CreditosChanged(value = it)) },
                label = { Text(text = "Créditos") },
                isError = state.creditosError != null,
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )
            if (state.creditosError != null) {
                Text(
                    text = state.creditosError,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(start = 16.dp, top = 4.dp)
                )
            }

            Spacer(Modifier.height(height = 24.dp))


            Row {
                Button(
                    onClick = { onEvent(EditAsignaturaUiEvent.Save) },
                    enabled = !state.isSaving,
                    modifier = Modifier.weight(1f)
                ) {
                    Text(text = if (state.isSaving) "Guardando..." else "Guardar")
                }

                if (!state.isNew) {
                    Spacer(Modifier.width(width = 8.dp))

                    OutlinedButton(
                        onClick = { onEvent(EditAsignaturaUiEvent.Delete) },
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
private fun NewAsignaturaPreview() {
    MaterialTheme {
        EditAsignaturaBody(
            state = EditAsignaturaUiState(isNew = true),
            onEvent = {}
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun EditAsignaturaPreview() {
    MaterialTheme {
        EditAsignaturaBody(
            state = EditAsignaturaUiState(
                asignaturaId = 1,
                codigo = "MAT101",
                nombre = "Matemáticas I",
                aula = "A-201",
                creditos = "4",
                isNew = false
            ),
            onEvent = {}
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun WithErrorsPreview() {
    MaterialTheme {
        EditAsignaturaBody(
            state = EditAsignaturaUiState(
                codigo = "M",
                nombre = "Ma",
                aula = "",
                creditos = "15",
                codigoError = "Mínimo 3 caracteres",
                nombreError = "Mínimo 3 caracteres",
                aulaError = "El aula es requerida",
                creditosError = "Máximo 10 créditos"
            ),
            onEvent = {}
        )
    }
}