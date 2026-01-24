package edu.ucne.RegistroDeEstudiantes.domain.asignaturas.usecase

data class ValidationResult(
    val isValid: Boolean,
    val error: String? = null
)

fun validateCodigo(value: String): ValidationResult {
    if (value.isBlank()) return ValidationResult(false, "El código es requerido")
    if (value.length < 3) return ValidationResult(false, "Mínimo 3 caracteres")
    return ValidationResult(true)
}

fun validateNombre(value: String): ValidationResult {
    if (value.isBlank()) return ValidationResult(false, "El nombre es requerido")
    if (value.length < 3) return ValidationResult(false, "Mínimo 3 caracteres")
    return ValidationResult(true)
}

fun validateAula(value: String): ValidationResult {
    if (value.isBlank()) return ValidationResult(false, "El aula es requerida")
    return ValidationResult(true)
}

fun validateCreditos(value: String): ValidationResult {
    if (value.isBlank()) return ValidationResult(false, "Los créditos son requeridos")
    val number = value.toIntOrNull() ?: return ValidationResult(false, "Debe ser número entero")
    if (number <= 0) return ValidationResult(false, "Debe ser mayor que 0")
    if (number > 10) return ValidationResult(false, "Máximo 10 créditos")
    return ValidationResult(true)
}