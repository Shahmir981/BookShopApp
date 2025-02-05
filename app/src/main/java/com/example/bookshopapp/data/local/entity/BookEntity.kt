package com.example.bookshopapp.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "books")
data class BookEntity(
    @PrimaryKey
    val id: Int,
    val title: String,
    val author: String,
    val imageUrl: String,
    val page: Int
)