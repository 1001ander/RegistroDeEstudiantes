package edu.ucne.RegistroDeEstudiantes.domain.penalidades.usecase

import edu.ucne.RegistroDeEstudiantes.domain.penalidades.repository.TipoPenalidadRepository
import javax.inject.Inject

class DeleteTipoPenalidadUseCase @Inject constructor(
    private val repository: TipoPenalidadRepository
) {
    suspend operator fun invoke(id: Int) {
        repository.delete(id)
    }
}