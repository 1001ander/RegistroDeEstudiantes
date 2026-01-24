package edu.ucne.RegistroDeEstudiantes.presentation.asignaturas.list

import androidx.compose.foundation.clickable
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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
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
        onSelectAsignatura = onSelectAsignatura
    )
}

@Composable
private fun ListAsignaturaBody(
    state: ListAsignaturaUiState,
    onAddAsignatura: () -> Unit,
    onSelectAsignatura: (Int) -> Unit
) {
    Scaffold(
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
                                onSelectAsignatura = onSelectAsignatura
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
    onSelectAsignatura: (Int) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 4.dp)
            .clickable { onSelectAsignatura(asignatura.asignaturaId) },
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = asignatura.codigo,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
                Text(
                    text = "${asignatura.creditos} créditos",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.secondary
                )
            }

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
            onSelectAsignatura = {}
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
            onSelectAsignatura = {}
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
            onSelectAsignatura = {}
        )
    }
}

