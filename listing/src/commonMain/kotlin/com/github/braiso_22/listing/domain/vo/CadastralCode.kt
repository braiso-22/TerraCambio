package com.github.braiso_22.listing.domain.vo

import kotlin.jvm.JvmInline

@JvmInline
value class CadastralCode(val value: String) {
    init {
        require(value.length == 14 || value.length == 18 || value.length == 20) {
            "Cadastral code length must be of 14, 18 or 20"
        }
    }
}