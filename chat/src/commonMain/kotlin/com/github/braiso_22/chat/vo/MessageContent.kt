package com.github.braiso_22.chat.vo

sealed interface MessageContent {
    data class Text(val text: String) : MessageContent {
        init {
            require(text.isNotBlank()) { "Text message cannot be blank" }
        }
    }

    @ConsistentCopyVisibility
    data class Image private constructor(val imageUrl: String, val caption: String?) :
        MessageContent {
        init {
            require(imageUrl.isNotBlank()) { "Image URL cannot be blank" }
        }

        companion object {
            fun create(imageUrl: String, caption: String?): Image {
                val normalizedCaption = caption?.takeIf { it.isNotBlank() }
                return Image(imageUrl, normalizedCaption)
            }
        }
    }

    data class TextAndImage(val text: String, val imageUrl: String) : MessageContent {
        init {
            require(text.isNotBlank()) { "Text cannot be blank in TextAndImage" }
            require(imageUrl.isNotBlank()) { "Image URL cannot be blank in TextAndImage" }
        }
    }
}