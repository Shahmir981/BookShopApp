package com.example.bookshopapp.domain.exception


sealed class Failure(message: String) : Exception(message) {
    class NetworkFailure : Failure("Network error")
    class UnknownFailure : Failure("Unknown error")
}
