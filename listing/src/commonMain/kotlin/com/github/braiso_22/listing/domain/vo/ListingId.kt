package com.github.braiso_22.listing.domain.vo

import kotlin.jvm.JvmInline
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid


@JvmInline
value class ListingId @OptIn(ExperimentalUuidApi::class) constructor(val value: Uuid)