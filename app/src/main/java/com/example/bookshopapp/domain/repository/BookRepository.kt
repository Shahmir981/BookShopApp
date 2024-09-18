package com.example.bookshopapp.domain.repository

import androidx.paging.Pager
import com.example.bookshopapp.data.local.entity.BookEntity
import com.example.bookshopapp.data.remote.dto.BookDto
import com.example.bookshopapp.data.utils.Result

interface BookRepository {
    fun getBookPage(): Pager<Int, BookEntity>
    suspend fun getBookById(bookId: Int): Result<BookDto>
}