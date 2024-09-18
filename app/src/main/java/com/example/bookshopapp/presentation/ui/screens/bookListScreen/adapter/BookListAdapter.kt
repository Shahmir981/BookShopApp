package com.example.bookshopapp.presentation.ui.screens.bookListScreen.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.bookshopapp.R
import com.example.bookshopapp.databinding.BookListItemBinding
import com.example.bookshopapp.domain.model.BookModel

class BookListAdapter(var onItemClick: (BookModel) -> Unit) :
    PagingDataAdapter<BookModel, BookListAdapter.BookViewHolder>(BookDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        val binding =
            BookListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BookViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        val book = getItem(position)
        if (book != null) {
            holder.bind(book, onItemClick = {
                onItemClick.invoke(it)
            })
        }
    }

    class BookViewHolder(val binding: BookListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(book: BookModel, onItemClick: (BookModel) -> Unit) {
            binding.bookTitle.text = book.title
            binding.bookAuthor.text = book.author

            binding.bookImage.load(book.imageUrl) {
                placeholder(R.drawable.book_placholder)
                error(R.drawable.book_placholder)
            }

            binding.root.setOnClickListener {
                onItemClick.invoke(book)
            }
        }
    }
}

class BookDiffCallback : DiffUtil.ItemCallback<BookModel>() {
    override fun areItemsTheSame(oldItem: BookModel, newItem: BookModel): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: BookModel, newItem: BookModel): Boolean {
        return oldItem == newItem
    }
}
