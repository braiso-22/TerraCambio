package com.braiso_22.terracambio.listing.infrastructure.adapters.out

import com.braiso_22.terracambio.listing.application.port.out.UserLocalDataSource
import com.braiso_22.terracambio.listing.application.port.out.dtos.UserId
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

class FakeUserLocalDataSource: UserLocalDataSource {
    @OptIn(ExperimentalUuidApi::class)
    override suspend fun getUserId(): UserId {
        return UserId(Uuid.random())
    }
}