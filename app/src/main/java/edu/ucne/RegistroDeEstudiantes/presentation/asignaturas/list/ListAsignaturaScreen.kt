package edu.ucne.RegistroDeEstudiantes.presentation.asignaturas.list

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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import edu.ucne.RegistroDeEstudiantes.domain.asignaturas.model.Asignatura

@Composable
fun ListAsignaturaScreen(
    viewModel: ListAsignaturaViewModel = hiltViewModel(),
    onAddAsignatura: () -> Unit,
    onSelectAsignatura: (Int) -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    ListAsignaturaBody(
        state = state,
        onAddAsignatura = onAddAsignatura,
        onSelectAsignatura = onSelectAsignatura,
        onDeleteAsignatura = { viewModel.deleteAsignatura(it) }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ListAsignaturaBody(
    state: ListAsignaturaUiState,
    onAddAsignatura: () -> Unit,
    onSelectAsignatura: (Int) -> Unit,
    onDeleteAsignatura: (Int) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Lista de Asignaturas") }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onAddAsignatura) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Agregar Asignatura"
                )
            }
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues = padding)
        ) {
            when {
                state.isLoading -> {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center)
                    )
                }

                state.asignaturas.isEmpty() -> {
                    Text(
                        text = "No hay asignaturas registradas",
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }

                else -> {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(state.asignaturas) { asignatura ->
                            AsignaturaCard(
                                asignatura = asignatura,
                                onSelectAsignatura = onSelectAsignatura,
                                onDeleteAsignatura = onDeleteAsignatura
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun AsignaturaCard(
    asignatura: Asignatura,
    onSelectAsignatura: (Int) -> Unit,
    onDeleteAsignatura: (Int) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 4.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Contenido de la asignatura
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = asignatura.codigo,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )

                Spacer(Modifier.height(4.dp))

                Text(
                    text = asignatura.nombre,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Medium
                )

                Spacer(Modifier.height(4.dp))

                Text(
                    text = "Aula: ${asignatura.aula}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Spacer(Modifier.height(4.dp))

                Text(
                    text = "${asignatura.creditos} créditos",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.secondary
                )
            }

            // Botones de acción
            Row {
                IconButton(onClick = { onSelectAsignatura(asignatura.asignaturaId) }) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = "Editar",
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
                IconButton(onClick = { onDeleteAsignatura(asignatura.asignaturaId) }) {
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
private fun ListAsignaturaWithDataPreview() {
    MaterialTheme {
        ListAsignaturaBody(
            state = ListAsignaturaUiState(
                asignaturas = listOf(
                    Asignatura(
                        asignaturaId = 1,
                        codigo = "MAT101",
                        nombre = "Matemáticas I",
                        aula = "A-201",
                        creditos = 4
                    ),
                    Asignatura(
                        asignaturaId = 2,
                        codigo = "FIS201",
                        nombre = "Física II",
                        aula = "B-105",
                        creditos = 5
                    ),
                    Asignatura(
                        asignaturaId = 3,
                        codigo = "PROG301",
                        nombre = "Programación Aplicada",
                        aula = "LAB-3",
                        creditos = 4
                    )
                )
            ),
            onAddAsignatura = {},
            onSelectAsignatura = {},
            onDeleteAsignatura = {}
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun ListAsignaturaEmptyPreview() {
    MaterialTheme {
        ListAsignaturaBody(
            state = ListAsignaturaUiState(asignaturas = emptyList()),
            onAddAsignatura = {},
            onSelectAsignatura = {},
            onDeleteAsignatura = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun AsignaturaCardPreview() {
    MaterialTheme {
        AsignaturaCard(
            asignatura = Asignatura(
                asignaturaId = 1,
                codigo = "MAT101",
                nombre = "Matemáticas I",
                aula = "A-201",
                creditos = 4
            ),
            onSelectAsignatura = {},
            onDeleteAsignatura = {}
        )
    }
}