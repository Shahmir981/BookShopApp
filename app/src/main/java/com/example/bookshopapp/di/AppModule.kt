package com.example.bookshopapp.di

import com.example.bookshopapp.data.helper.NetworkHelper
import com.example.bookshopapp.data.local.database.BookDao
import com.example.bookshopapp.data.repositoryImpl.BookRepositoryImpl
import com.example.bookshopapp.domain.repository.BookRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import com.example.bookshopapp.data.remote.api.GutendexApi

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Provides
    @Singleton
    fun provideBookRepository(
        api: GutendexApi,
        bookDao: BookDao,
        networkHelper: NetworkHelper
    ): BookRepository {
        return BookRepositoryImpl(api, bookDao, networkHelper)
    }
}