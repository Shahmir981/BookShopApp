package com.example.bookshopapp.data.repositoryImpl

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.example.bookshopapp.data.helper.NetworkHelper
import com.example.bookshopapp.data.local.database.BookDao
import com.example.bookshopapp.data.local.entity.BookEntity
import com.example.bookshopapp.data.paging.BookPagingSource
import com.example.bookshopapp.data.remote.api.GutendexApi
import com.example.bookshopapp.data.remote.dto.BookDto
import com.example.bookshopapp.data.utils.Result
import com.example.bookshopapp.domain.exception.Failure
import com.example.bookshopapp.domain.repository.BookRepository
import retrofit2.Response
import javax.inject.Inject

class BookRepositoryImpl @Inject constructor(
    private val api: GutendexApi,
    private val bookDao: BookDao,
    private val networkHelper: NetworkHelper
) : BookRepository {

    override fun getBookPage(): Pager<Int, BookEntity> {
        val pagingSourceFactory = if (networkHelper.isNetworkConnected()) {
            BookPagingSource(api, bookDao)
        } else {
            bookDao.getPagingSource()
        }

        return Pager(
            config = PagingConfig(pageSize = 32, enablePlaceholders = false),
            pagingSourceFactory = { pagingSourceFactory }
        )
    }

    override suspend fun getBookById(bookId: Int): Result<BookDto> {
        return executeRequest {
            api.getBookById(bookId)
        }
    }

    private suspend fun <T> executeRequest(call: suspend () -> Response<T>): Result<T> {
        return try {
            val response = call()

            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    Result.Success(body)
                } else {
                    Result.Error(Failure.UnknownFailure())
                }
            } else {
                Result.Error(Failure.NetworkFailure())
            }
        } catch (e: Exception) {
            Result.Error(Failure.NetworkFailure())
        }
    }
}