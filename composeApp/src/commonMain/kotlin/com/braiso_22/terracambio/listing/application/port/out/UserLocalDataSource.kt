package com.braiso_22.terracambio.listing.application.port.out

import com.braiso_22.terracambio.listing.application.port.out.dtos.UserId

fun interface UserLocalDataSource {
    suspend fun getUserId(): UserId
}