package com.example.bookshopapp.di

import android.content.Context
import androidx.room.Room
import com.example.bookshopapp.data.local.database.BookDao
import com.example.bookshopapp.data.local.database.BookDatabase
import com.example.bookshopapp.data.local.database.BookDatabase.Companion.DATABASE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): BookDatabase {
        return Room.databaseBuilder(context, BookDatabase::class.java, DATABASE_NAME)
            .build()
    }

    @Provides
    fun provideBookDao(database: BookDatabase): BookDao {
        return database.bookDao()
    }
}