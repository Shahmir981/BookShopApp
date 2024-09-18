package com.example.bookshopapp.domain.usecase.bookPreview

import com.example.bookshopapp.data.utils.Result
import com.example.bookshopapp.data.utils.map
import com.example.bookshopapp.data.utils.toBookModel
import com.example.bookshopapp.domain.model.BookModel
import com.example.bookshopapp.domain.repository.BookRepository
import javax.inject.Inject

class GetBookByIdUseCase @Inject constructor(
    private val bookRepository: BookRepository
) {
    suspend operator fun invoke(pageId: Int): Result<BookModel> {
        return bookRepository.getBookById(pageId).map { bookDto ->
            bookDto.toBookModel()
        }
    }
}