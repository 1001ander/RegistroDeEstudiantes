package edu.ucne.RegistroDeEstudiantes.domain.penalidades.usecase

data class ValidationResult(
    val isValid: Boolean,
    val error: String? = null
)

fun validateNombre(value: String): ValidationResult {
    if (value.isBlank()) return ValidationResult(false, "El nombre es requerido")
    if (value.length < 3) return ValidationResult(false, "Mínimo 3 caracteres")
    return ValidationResult(true)
}

fun validateDescripcion(value: String): ValidationResult {
    if (value.isBlank()) return ValidationResult(false, "La descripción es requerida")
    if (value.length < 3) return ValidationResult(false, "Mínimo 3 caracteres")
    return ValidationResult(true)
}

fun validatePuntosDescuento(value: String): ValidationResult {
    if (value.isBlank()) return ValidationResult(false, "Los puntos de descuento son requeridos")
    val number = value.toIntOrNull() ?: return ValidationResult(false, "Debe ser número entero")
    if (number <= 0) return ValidationResult(false, "Debe ser mayor que 0")
    return ValidationResult(true)
}
