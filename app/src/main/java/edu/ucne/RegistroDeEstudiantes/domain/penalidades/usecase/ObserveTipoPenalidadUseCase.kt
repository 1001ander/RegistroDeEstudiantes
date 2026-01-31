package edu.ucne.RegistroDeEstudiantes.domain.penalidades.usecase

import edu.ucne.RegistroDeEstudiantes.domain.penalidades.model.TipoPenalidad
import edu.ucne.RegistroDeEstudiantes.domain.penalidades.repository.TipoPenalidadRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ObserveTiposPenalidadesUseCase @Inject constructor(
    private val repository: TipoPenalidadRepository
) {
    operator fun invoke(): Flow<List<TipoPenalidad>> {
        return repository.observeTiposPenalidades()
    }
}

