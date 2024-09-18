package com.example.bookshopapp.presentation.ui.screens.bookListScreen

import android.content.Intent
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.map
import com.example.bookshopapp.data.local.entity.BookEntity
import com.example.bookshopapp.data.utils.Result
import com.example.bookshopapp.data.utils.toBookModel
import com.example.bookshopapp.domain.model.BookModel
import com.example.bookshopapp.domain.usecase.bookList.GetBookListUseCase
import com.example.bookshopapp.domain.usecase.bookPreview.GetBookByIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookListViewModel @Inject constructor(
    private val getBookListUseCase: GetBookListUseCase,
    private val getBookByIdUseCase: GetBookByIdUseCase
) : ViewModel() {

    private val _dataFlow = MutableSharedFlow<Result<BookModel>>(
        replay = 0
    )
    val dataFlow: SharedFlow<Result<BookModel>> get() = _dataFlow

    fun getBookById(bookId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            _dataFlow.emit(getBookByIdUseCase.invoke(bookId))
        }
    }

    fun getPagingData(): Flow<PagingData<BookModel>> {
        return getBookListUseCase.invoke().flow.map { pagingData: PagingData<BookEntity> ->
            pagingData.map { entity -> entity.toBookModel() }
        }
    }

    fun handleDeepLink(intent: Intent): Int? {
        if (Intent.ACTION_VIEW == intent.action) {
            val uri = intent.data
            if (uri != null && uri.scheme == "booksapp") {
                val bookIdString = uri.lastPathSegment
                if (bookIdString != null) {
                    try {
                        val bookId = bookIdString.toInt()
                        return bookId
                    } catch (nfe: NumberFormatException) {
                        return null
                    }
                }
            }
        }

        return null
    }
}