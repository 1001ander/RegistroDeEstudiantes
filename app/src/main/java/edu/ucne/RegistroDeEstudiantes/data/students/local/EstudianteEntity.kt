package edu.ucne.RegistroDeEstudiantes.data.students.local

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "Estudiantes")
data class EstudianteEntity (
    @PrimaryKey(autoGenerate = true)
    val estudianteId: Int = 0,
    val nombres: String,
    val email: String,
    val edad: Int

)
