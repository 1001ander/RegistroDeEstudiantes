package edu.ucne.RegistroDeEstudiantes.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import edu.ucne.RegistroDeEstudiantes.data.students.local.EstudianteDao
import edu.ucne.RegistroDeEstudiantes.data.students.local.EstudianteEntity

@Database(
    entities = [
        EstudianteEntity::class],
    version = 1,
    exportSchema = false
)
abstract class EstudianteDb : RoomDatabase() {

    abstract fun estudianteDao(): EstudianteDao
}
