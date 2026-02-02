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
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import edu.ucne.RegistroDeEstudiantes.domain.penalidades.model.TipoPenalidad
import kotlinx.coroutines.launch

@Composable
fun ListTipoPenalidadScreen(
    viewModel: ListTipoPenalidadViewModel = hiltViewModel(),
    drawerState: DrawerState,
    onAddTipoPenalidad: () -> Unit,
    onSelectTipoPenalidad: (Int) -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    ListTipoPenalidadBody(
        state = state,
        drawerState = drawerState,
        onAddTipoPenalidad = onAddTipoPenalidad,
        onSelectTipoPenalidad = onSelectTipoPenalidad,
        onDeleteTipoPenalidad = { viewModel.deleteTipoPenalidad(it) },
        onClearError = { viewModel.clearError() }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ListTipoPenalidadBody(
    state: ListTipoPenalidadUiState,
    drawerState: DrawerState,
    onAddTipoPenalidad: () -> Unit,
    onSelectTipoPenalidad: (Int) -> Unit,
    onDeleteTipoPenalidad: (Int) -> Unit,
    onClearError: () -> Unit = {}
) {
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }


    LaunchedEffect(state.errorMessage) {
        state.errorMessage?.let { message ->
            snackbarHostState.showSnackbar(
                message = message,
                duration = SnackbarDuration.Long
            )
            onClearError()
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            TopAppBar(
                title = { Text("Lista de Penalidades") },
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


            if (state.isDeleting) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
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
            drawerState = androidx.compose.material3.rememberDrawerState(
                initialValue = androidx.compose.material3.DrawerValue.Closed
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
            drawerState = androidx.compose.material3.rememberDrawerState(
                initialValue = androidx.compose.material3.DrawerValue.Closed
            ),
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