package de.jensklingenberg.androidonlyexample

import kotlinx.serialization.SerialName

@kotlinx.serialization.Serializable
data class KtorfitApplicationShort(
    @SerialName("_id") val id: String,
    @SerialName("category_id") val categoryId: String,
    @SerialName("alias") val alias: String,
    @SerialName("name") val name: String
)
