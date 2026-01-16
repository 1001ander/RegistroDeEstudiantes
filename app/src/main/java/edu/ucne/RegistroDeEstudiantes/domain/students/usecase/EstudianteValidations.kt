package edu.ucne.RegistroDeEstudiantes.domain.students.usecase

import android.util.Patterns

data class ValidationResult(
    val isValid: Boolean,
    val error: String? = null
)

fun validateNombres(value: String): ValidationResult =
    if (value.isBlank())
        ValidationResult(false, "El nombre no puede estar vacío")
    else
        ValidationResult(true)

fun validateEmail(value: String): ValidationResult =
    if (!Patterns.EMAIL_ADDRESS.matcher(value).matches())
        ValidationResult(false, "Email inválido")
    else
        ValidationResult(true)

fun validateEdad(value: Int): ValidationResult =
    if (value <= 0)
        ValidationResult(false, "La edad debe ser mayor a 0")
    else
        ValidationResult(true)
