package edu.ucne.RegistroDeEstudiantes.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import edu.ucne.RegistroDeEstudiantes.data.students.repository.EstudianteRepositoryImpl
import edu.ucne.RegistroDeEstudiantes.domain.students.repository.EstudianteRepository
import javax.inject.Singleton



@Module
@InstallIn(SingletonComponent::class)
abstract class EstudianteRepositoryModule {

    @Binds
    @Singleton
    abstract fun bindEstudianteRepository(
        estudianteRepositoryImpl: EstudianteRepositoryImpl
    ): EstudianteRepository
}
