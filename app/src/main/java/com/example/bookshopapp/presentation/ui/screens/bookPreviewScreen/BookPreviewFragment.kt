package com.example.bookshopapp.presentation.ui.screens.bookPreviewScreen

import android.os.Bundle
import android.transition.TransitionInflater
import com.example.bookshopapp.R
import com.example.bookshopapp.databinding.BookPreviewFragmentBinding
import com.example.bookshopapp.presentation.ui.screens.BaseFragment
import com.example.bookshopapp.presentation.ui.viewBinding
import androidx.navigation.fragment.navArgs
import coil.load

class BookPreviewFragment :
    BaseFragment<BookPreviewFragmentBinding>(R.layout.book_preview_fragment) {

    private val args: BookPreviewFragmentArgs by navArgs()

    override val binding: BookPreviewFragmentBinding by viewBinding(
        BookPreviewFragmentBinding::bind
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedElementEnterTransition = TransitionInflater.from(context)
            .inflateTransition(android.R.transition.move)

    }

    override fun initViews() {
        super.initViews()

        binding.bookImage.load(args.book.imageUrl) {
            placeholder(R.drawable.book_placholder)
            error(R.drawable.book_placholder)
        }
    }
}
