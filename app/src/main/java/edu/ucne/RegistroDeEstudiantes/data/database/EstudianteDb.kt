package edu.ucne.RegistroDeEstudiantes.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import edu.ucne.RegistroDeEstudiantes.data.students.local.EstudianteDao
import edu.ucne.RegistroDeEstudiantes.data.students.local.EstudianteEntity
import edu.ucne.RegistroDeEstudiantes.data.asignaturas.local.AsignaturaDao
import edu.ucne.RegistroDeEstudiantes.data.asignaturas.local.AsignaturaEntity

@Database(
    entities = [
        EstudianteEntity::class,
        AsignaturaEntity::class
    ],
    version = 2,
    exportSchema = false
)
abstract class EstudianteDb : RoomDatabase() {
    abstract fun estudianteDao(): EstudianteDao
    abstract fun asignaturaDao(): AsignaturaDao
}