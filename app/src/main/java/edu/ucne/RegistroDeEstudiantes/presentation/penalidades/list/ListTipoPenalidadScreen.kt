package edu.ucne.RegistroDeEstudiantes.presentation.penalidades.list

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
import edu.ucne.RegistroDeEstudiantes.domain.penalidades.model.TipoPenalidad

@Composable
fun ListTipoPenalidadScreen(
    viewModel: ListTipoPenalidadViewModel = hiltViewModel(),
    onAddTipoPenalidad: () -> Unit,
    onSelectTipoPenalidad: (Int) -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    ListTipoPenalidadBody(
        state = state,
        onAddTipoPenalidad = onAddTipoPenalidad,
        onSelectTipoPenalidad = onSelectTipoPenalidad,
        onDeleteTipoPenalidad = { viewModel.deleteTipoPenalidad(it) }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ListTipoPenalidadBody(
    state: ListTipoPenalidadUiState,
    onAddTipoPenalidad: () -> Unit,
    onSelectTipoPenalidad: (Int) -> Unit,
    onDeleteTipoPenalidad: (Int) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Lista de Tipos de Penalidades") }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onAddTipoPenalidad) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Agregar Tipo de Penalidad"
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

                state.tiposPenalidades.isEmpty() -> {
                    Text(
                        text = "No hay tipos de penalidades registrados",
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }

                else -> {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(state.tiposPenalidades) { tipoPenalidad ->
                            TipoPenalidadCard(
                                tipoPenalidad = tipoPenalidad,
                                onSelectTipoPenalidad = onSelectTipoPenalidad,
                                onDeleteTipoPenalidad = onDeleteTipoPenalidad
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun TipoPenalidadCard(
    tipoPenalidad: TipoPenalidad,
    onSelectTipoPenalidad: (Int) -> Unit,
    onDeleteTipoPenalidad: (Int) -> Unit
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

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = tipoPenalidad.nombre,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )

                Spacer(Modifier.height(4.dp))

                Text(
                    text = tipoPenalidad.descripcion,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Spacer(Modifier.height(4.dp))

                Text(
                    text = "${tipoPenalidad.puntosDescuento} puntos de descuento",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.error,
                    fontWeight = FontWeight.Bold
                )
            }


            Row {
                IconButton(onClick = { onSelectTipoPenalidad(tipoPenalidad.tipoId) }) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = "Editar",
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
                IconButton(onClick = { onDeleteTipoPenalidad(tipoPenalidad.tipoId) }) {
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
private fun ListTipoPenalidadWithDataPreview() {
    MaterialTheme {
        ListTipoPenalidadBody(
            state = ListTipoPenalidadUiState(
                tiposPenalidades = listOf(
                    TipoPenalidad(
                        tipoId = 1,
                        nombre = "Falta Injustificada",
                        descripcion = "No asistir a clase sin justificación válida",
                        puntosDescuento = 5
                    ),
                    TipoPenalidad(
                        tipoId = 2,
                        nombre = "Comportamiento Inadecuado",
                        descripcion = "Conducta inapropiada en el aula",
                        puntosDescuento = 3
                    ),
                    TipoPenalidad(
                        tipoId = 3,
                        nombre = "Incumplimiento de Tareas",
                        descripcion = "No entregar trabajos asignados",
                        puntosDescuento = 2
                    )
                )
            ),
            onAddTipoPenalidad = {},
            onSelectTipoPenalidad = {},
            onDeleteTipoPenalidad = {}
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun ListTipoPenalidadEmptyPreview() {
    MaterialTheme {
        ListTipoPenalidadBody(
            state = ListTipoPenalidadUiState(tiposPenalidades = emptyList()),
            onAddTipoPenalidad = {},
            onSelectTipoPenalidad = {},
            onDeleteTipoPenalidad = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun TipoPenalidadCardPreview() {
    MaterialTheme {
        TipoPenalidadCard(
            tipoPenalidad = TipoPenalidad(
                tipoId = 1,
                nombre = "Falta Injustificada",
                descripcion = "No asistir a clase sin justificación válida",
                puntosDescuento = 5
            ),
            onSelectTipoPenalidad = {},
            onDeleteTipoPenalidad = {}
        )
    }
}