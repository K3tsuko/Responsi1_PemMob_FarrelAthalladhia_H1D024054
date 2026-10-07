package com.example.openlibrary.data.model

import com.google.gson.annotations.SerializedName

data class SearchResponse(
    val docs: List<Book>?
)

data class Book(
    val key: String?,
    val title: String?,
    @SerializedName("author_name") val authorName: List<String>?,
    @SerializedName("first_publish_year") val firstPublishYear: Int?,
    @SerializedName("edition_count") val editionCount: Int?,
    val language: List<String>?
)

// Extension function: turns a nullable list into display text
fun List<String>?.toDisplayText(fallback: String = "Unknown"): String =
    if (this.isNullOrEmpty()) fallback else this.joinToString(", ")