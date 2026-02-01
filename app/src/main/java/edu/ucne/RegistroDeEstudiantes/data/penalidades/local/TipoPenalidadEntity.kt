package edu.ucne.RegistroDeEstudiantes.data.penalidades.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "TiposPenalidades")
data class TipoPenalidadEntity(
    @PrimaryKey(autoGenerate = true)
    val tipoId: Int? = null,
    val nombre: String = "",
    val descripcion: String = "",
    val puntosDescuento: Int = 0
)