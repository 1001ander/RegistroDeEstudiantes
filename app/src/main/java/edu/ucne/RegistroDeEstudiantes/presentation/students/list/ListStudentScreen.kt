package edu.ucne.RegistroDeEstudiantes.presentation.students.list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import edu.ucne.RegistroDeEstudiantes.domain.students.model.Estudiante

@Composable
fun ListEstudianteScreen(
    viewModel: ListEstudianteViewModel = hiltViewModel(),
    onNavigateToEdit: (Int?) -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    ListEstudianteBody(
        state = state,
        onEvent = viewModel::onEvent,
        onNavigateToEdit = onNavigateToEdit
    )
}

@Composable
private fun ListEstudianteBody(
    state: ListEstudianteUiState,
    onEvent: (ListEstudianteUiEvent) -> Unit,
    onNavigateToEdit: (Int?) -> Unit
) {
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = { onNavigateToEdit(null) }
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Agregar Estudiante"
                )
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(paddingValues = padding)
                .padding(all = 16.dp)
        ) {
            OutlinedTextField(
                value = state.searchQuery,
                onValueChange = { onEvent(ListEstudianteUiEvent.SearchQueryChanged(query = it)) },
                label = { Text(text = "Buscar estudiante") },
                modifier = Modifier.fillMaxWidth(),
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Buscar"
                    )
                },
                singleLine = true
            )

            Spacer(Modifier.height(height = 16.dp))

            if (state.isLoading) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            } else if (state.estudiantes.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = if (state.searchQuery.isBlank()) {
                            "No hay estudiantes registrados"
                        } else {
                            "No se encontraron resultados"
                        },
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            } else {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(
                        items = state.estudiantes,
                        key = { it.estudianteId ?: 0 }
                    ) { estudiante ->
                        EstudianteCard(
                            estudiante = estudiante,
                            onEdit = { onNavigateToEdit(estudiante.estudianteId) },
                            onDelete = { onEvent(ListEstudianteUiEvent.DeleteEstudiante(id = estudiante.estudianteId ?: 0)) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun EstudianteCard(
    estudiante: Estudiante,
    onEdit: () -> Unit,
    onDelete: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .padding(all = 16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = estudiante.nombres,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )

                Spacer(Modifier.height(height = 4.dp))

                Text(
                    text = estudiante.email,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Spacer(Modifier.height(height = 4.dp))

                Text(
                    text = "Edad: ${estudiante.edad} años",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            Row {
                IconButton(onClick = onEdit) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = "Editar",
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
                IconButton(onClick = onDelete) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Eliminar",
                        tint = MaterialTheme.colorScheme.error
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun ListEstudiantePreview() {
    MaterialTheme {
        ListEstudianteBody(
            state = ListEstudianteUiState(
                estudiantes = listOf(
                    Estudiante(1, "Juan Pérez", "juan@example.com", 20),
                    Estudiante(2, "María García", "maria@example.com", 22)
                )
            ),
            onEvent = {},
            onNavigateToEdit = {}
        )
    }
}