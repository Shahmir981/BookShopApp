package com.example.bookshopapp.data.utils

import com.example.bookshopapp.data.local.entity.BookEntity
import com.example.bookshopapp.data.remote.dto.BookDto
import com.example.bookshopapp.domain.model.BookModel

fun BookDto.toBookModel(): BookModel {
    return BookModel(
        id ?: 0, title.toString(), authors?.joinToString(" ") { it.name.orEmpty() }.orEmpty(), formats?.imageUrl.orEmpty()
    )
}

fun BookDto.isValidBook(): Boolean {
    return id != null && title != null && authors != null && formats != null && formats.imageUrl != null
}

fun BookDto.toBookEntity(page: Int): BookEntity {
    return BookEntity(
        id ?: 0,
        title.toString(),
        authors?.joinToString(" ") { it.name.orEmpty() }.orEmpty(),
        formats?.imageUrl.orEmpty(),
        page
    )
}

fun BookEntity.toBookModel(): BookModel {
    return BookModel(id, title, author, imageUrl)
}
