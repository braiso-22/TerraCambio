package com.github.braiso_22.chat.vo

import kotlin.jvm.JvmInline
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@JvmInline
value class UserId @OptIn(ExperimentalUuidApi::class) constructor(val value: Uuid) {

    companion object {
        @OptIn(ExperimentalUuidApi::class)
        fun example() = UserId(Uuid.random())
    }
}