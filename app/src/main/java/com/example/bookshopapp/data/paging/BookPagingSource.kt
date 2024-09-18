package com.example.bookshopapp.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.bookshopapp.data.local.database.BookDao
import com.example.bookshopapp.data.local.entity.BookEntity
import com.example.bookshopapp.data.remote.api.GutendexApi
import com.example.bookshopapp.data.utils.isValidBook
import com.example.bookshopapp.data.utils.toBookEntity

class BookPagingSource(
    private val apiService: GutendexApi,
    private val bookDao: BookDao
) : PagingSource<Int, BookEntity>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, BookEntity> {
        try {
            val currentPage = params.key ?: 1 // Default to page 1 if undefined
            val response = apiService.getBooksPage(
                page = currentPage
            )

            if (response.isSuccessful) {
                val books =
                    response.body()?.books?.filter { it.isValidBook() }
                        ?.map { it.toBookEntity(currentPage) }
                        ?: emptyList()

                val nextKey = if (books.isEmpty()) {
                    null // No more pages to load
                } else {
                    currentPage + 1
                }

                bookDao.insertAll(books)
                return LoadResult.Page(
                    data = books,
                    prevKey = if (currentPage == 1) null else currentPage - 1,
                    nextKey = nextKey
                )
            } else {
                return LoadResult.Error(Throwable("Error fetching data"))
            }

        } catch (exception: Exception) {
            return LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, BookEntity>): Int? {
        // Return the key for the closest page to the current viewport position
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}