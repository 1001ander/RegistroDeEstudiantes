package edu.ucne.RegistroDeEstudiantes.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import edu.ucne.RegistroDeEstudiantes.data.database.EstudianteDb
import edu.ucne.RegistroDeEstudiantes.data.students.local.EstudianteDao
import edu.ucne.RegistroDeEstudiantes.data.asignaturas.local.AsignaturaDao
import edu.ucne.RegistroDeEstudiantes.data.penalidades.local.TipoPenalidadDao
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideEstudianteDatabase(
        @ApplicationContext context: Context
    ): EstudianteDb {
        return Room.databaseBuilder(
            context,
            EstudianteDb::class.java,
            "estudiante_database"
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideEstudianteDao(database: EstudianteDb): EstudianteDao {
        return database.estudianteDao()
    }

    @Provides
    @Singleton
    fun provideAsignaturaDao(database: EstudianteDb): AsignaturaDao {
        return database.asignaturaDao()
    }

    @Provides
    @Singleton
    fun provideTipoPenalidadDao(database: EstudianteDb): TipoPenalidadDao {
        return database.tipoPenalidadDao()
    }
}