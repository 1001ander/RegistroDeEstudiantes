package edu.ucne.RegistroDeEstudiantes.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import edu.ucne.RegistroDeEstudiantes.data.students.repository.EstudianteRepositoryImpl
import edu.ucne.RegistroDeEstudiantes.domain.students.repository.EstudianteRepository
import edu.ucne.RegistroDeEstudiantes.data.asignaturas.repository.AsignaturaRepositoryImpl
import edu.ucne.RegistroDeEstudiantes.domain.asignaturas.repository.AsignaturaRepository
import edu.ucne.RegistroDeEstudiantes.data.penalidades.repository.TipoPenalidadRepositoryImpl
import edu.ucne.RegistroDeEstudiantes.domain.penalidades.repository.TipoPenalidadRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindEstudianteRepository(
        impl: EstudianteRepositoryImpl
    ): EstudianteRepository

    @Binds
    @Singleton
    abstract fun bindAsignaturaRepository(
        impl: AsignaturaRepositoryImpl
    ): AsignaturaRepository

    @Binds
    @Singleton
    abstract fun bindTipoPenalidadRepository(
        impl: TipoPenalidadRepositoryImpl
    ): TipoPenalidadRepository
}