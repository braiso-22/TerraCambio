package com.braiso_22.terracambio.listing.application.port.out

import com.github.braiso_22.listing.vo.CadastralCode
import com.github.braiso_22.listing.vo.Location

fun interface LocationProvider {
    suspend fun getByCadastralCode(code: CadastralCode): Location?
}