package com.example.bookshopapp.data.local.database

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.bookshopapp.data.local.entity.BookEntity

@Dao
interface BookDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(data: List<BookEntity>)

    @Query("SELECT * FROM books ORDER BY id ASC")
    fun getPagingSource(): PagingSource<Int, BookEntity>
}