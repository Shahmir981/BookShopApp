package com.example.bookshopapp.data.remote.api

import com.example.bookshopapp.data.remote.dto.BookDto
import com.example.bookshopapp.data.remote.dto.BookPageDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface GutendexApi {
    @GET("books/")
    suspend fun getBooksPage(@Query("page") page: Int): Response<BookPageDto>

    @GET("books/{bookId}")
    suspend fun getBookById(@Path("bookId") bookId: Int): Response<BookDto>
}