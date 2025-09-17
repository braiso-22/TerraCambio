package com.github.braiso_22.listing.application.port.`in`.validateCadastralCode

import listing.application.port.`in`.validateCadastralCode.ValidateCadastralCodeCommand
import listing.application.port.`in`.validateCadastralCode.ValidateCadastralCodeResult

fun interface ValidateCadastralCode {
    suspend operator fun invoke(command: ValidateCadastralCodeCommand): ValidateCadastralCodeResult
}
