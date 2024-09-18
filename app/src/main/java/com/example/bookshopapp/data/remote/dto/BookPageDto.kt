package com.example.bookshopapp.data.remote.dto

import com.google.gson.annotations.SerializedName

data class BookPageDto(
    @SerializedName("next")
    val next: String,
    @SerializedName("results")
    val books: List<BookDto>
)

data class BookDto(
    @SerializedName("id")
    val id: Int?,
    @SerializedName("title")
    val title: String?,
    @SerializedName("authors")
    val authors: ArrayList<Author>?,
    @SerializedName("formats")
    val formats: Formats?
)

data class Author(
    @SerializedName("name")
    val name: String?
)

data class Formats(
    @SerializedName("image/jpeg")
    val imageUrl: String?
)
