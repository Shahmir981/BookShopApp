package com.example.bookshopapp.data.utils

import com.example.bookshopapp.domain.exception.Failure

sealed class Result<out T> {
    data object Loading : Result<Nothing>()
    data class Success<out T>(val data: T) : Result<T>()
    data class Error(val error: Failure) : Result<Nothing>()
}

fun <A, B> Result<A>.map(transform: (A) -> B): Result<B> {
    return when (this) {
        is Result.Loading -> Result.Loading
        is Result.Success -> Result.Success(transform(this.data))
        is Result.Error -> Result.Error(this.error)
    }
}
