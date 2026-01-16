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
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object AppModule {

    @Provides
    @Singleton
    fun provideEstudianteDb(@ApplicationContext appContext: Context) =
        Room.databaseBuilder(
            appContext,
            klass = EstudianteDb::class.java,
            name = "Estudiante.db"
        ).fallbackToDestructiveMigration()
            .build()

    @Provides
    @Singleton
    fun provideEstudianteDao(database: EstudianteDb): EstudianteDao =
        database.estudianteDao()
}