package listing.application.port.`in`.validateCadastralCode

sealed interface ValidateCadastralCodeResult {
    data class InvalidFormat(val message: String) : ValidateCadastralCodeResult
    data object Valid : ValidateCadastralCodeResult
    data object NotFound : ValidateCadastralCodeResult
}