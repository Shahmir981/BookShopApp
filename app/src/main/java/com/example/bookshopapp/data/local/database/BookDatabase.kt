package com.example.bookshopapp.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.bookshopapp.data.local.entity.BookEntity

@Database(entities = [BookEntity::class], version = 1)
abstract class BookDatabase : RoomDatabase() {

    abstract fun bookDao(): BookDao

    companion object {
        const val DATABASE_NAME = "book_database"
    }

}