package com.example.bookshopapp.domain.usecase.bookList

import androidx.paging.Pager
import com.example.bookshopapp.data.local.entity.BookEntity
import com.example.bookshopapp.domain.repository.BookRepository
import javax.inject.Inject

class GetBookListUseCase @Inject constructor(
    private val bookRepository: BookRepository
) {
    operator fun invoke(): Pager<Int, BookEntity> {
        return bookRepository.getBookPage()
    }
}
