package com.braiso_22.terracambio.listing.application.port.out.dtos

import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@JvmInline
value class UserId @OptIn(ExperimentalUuidApi::class) constructor(
    val value: Uuid,
)