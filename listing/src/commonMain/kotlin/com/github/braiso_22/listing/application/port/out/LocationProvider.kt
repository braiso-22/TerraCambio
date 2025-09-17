package com.github.braiso_22.listing.application.port.out

import com.github.braiso_22.listing.domain.vo.CadastralCode
import com.github.braiso_22.listing.domain.vo.Location

fun interface LocationProvider {
    suspend fun getByCadastralCode(code: CadastralCode): Location?
}