package edu.ucne.RegistroDeEstudiantes.presentation.penalidades.edit

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
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun EditTipoPenalidadScreen(
    viewModel: EditTipoPenalidadViewModel = hiltViewModel(),
    onNavigateBack: () -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()


    if (state.isSaved || state.deleted) {
        onNavigateBack()
    }

    EditTipoPenalidadBody(
        state = state,
        onEvent = viewModel::onEvent
    )
}

@Composable
private fun EditTipoPenalidadBody(
    state: EditTipoPenalidadUiState,
    onEvent: (EditTipoPenalidadUiEvent) -> Unit
) {
    Scaffold { padding ->
        Column(
            modifier = Modifier
                .padding(paddingValues = padding)
                .padding(all = 16.dp)
        ) {

            OutlinedTextField(
                value = state.nombre,
                onValueChange = { onEvent(EditTipoPenalidadUiEvent.NombreChanged(value = it)) },
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
                value = state.descripcion,
                onValueChange = { onEvent(EditTipoPenalidadUiEvent.DescripcionChanged(value = it)) },
                label = { Text(text = "Descripción") },
                isError = state.descripcionError != null,
                modifier = Modifier.fillMaxWidth(),
                minLines = 3
            )
            if (state.descripcionError != null) {
                Text(
                    text = state.descripcionError,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(start = 16.dp, top = 4.dp)
                )
            }

            Spacer(Modifier.height(height = 12.dp))


            OutlinedTextField(
                value = state.puntosDescuento,
                onValueChange = { onEvent(EditTipoPenalidadUiEvent.PuntosDescuentoChanged(value = it)) },
                label = { Text(text = "Puntos de Descuento") },
                isError = state.puntosDescuentoError != null,
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )
            if (state.puntosDescuentoError != null) {
                Text(
                    text = state.puntosDescuentoError,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(start = 16.dp, top = 4.dp)
                )
            }

            Spacer(Modifier.height(height = 24.dp))


            Row {
                Button(
                    onClick = { onEvent(EditTipoPenalidadUiEvent.Save) },
                    enabled = !state.isSaving,
                    modifier = Modifier.weight(1f)
                ) {
                    Text(text = if (state.isSaving) "Guardando..." else "Guardar")
                }

                if (!state.isNew) {
                    Spacer(Modifier.width(width = 8.dp))

                    OutlinedButton(
                        onClick = { onEvent(EditTipoPenalidadUiEvent.Delete) },
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
private fun NewTipoPenalidadPreview() {
    MaterialTheme {
        EditTipoPenalidadBody(
            state = EditTipoPenalidadUiState(isNew = true),
            onEvent = {}
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun EditTipoPenalidadPreview() {
    MaterialTheme {
        EditTipoPenalidadBody(
            state = EditTipoPenalidadUiState(
                tipoId = 1,
                nombre = "Falta Injustificada",
                descripcion = "No asistir a clase sin justificación válida",
                puntosDescuento = "5",
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
        EditTipoPenalidadBody(
            state = EditTipoPenalidadUiState(
                nombre = "Fa",
                descripcion = "D",
                puntosDescuento = "0",
                nombreError = "Mínimo 3 caracteres",
                descripcionError = "Mínimo 3 caracteres",
                puntosDescuentoError = "Debe ser mayor que 0"
            ),
            onEvent = {}
        )
    }
}

