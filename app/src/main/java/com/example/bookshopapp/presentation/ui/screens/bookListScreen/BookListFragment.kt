package com.example.bookshopapp.presentation.ui.screens.bookListScreen

import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.transition.TransitionInflater
import com.example.bookshopapp.R
import com.example.bookshopapp.data.utils.Result
import com.example.bookshopapp.databinding.BookListFragmentBinding
import com.example.bookshopapp.domain.model.BookModel
import com.example.bookshopapp.presentation.ui.screens.BaseFragment
import com.example.bookshopapp.presentation.ui.screens.bookListScreen.adapter.BookListAdapter
import com.example.bookshopapp.presentation.ui.screens.bookListScreen.adapter.BookLoadStateAdapter
import com.example.bookshopapp.presentation.ui.utils.ProgressDialogFragment
import com.example.bookshopapp.presentation.ui.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.launch

@AndroidEntryPoint
class BookListFragment : BaseFragment<BookListFragmentBinding>(R.layout.book_list_fragment) {

    private val bookListViewModel: BookListViewModel by viewModels()

    private val progressDialog = ProgressDialogFragment()

    private lateinit var bookListAdapter: BookListAdapter
    private lateinit var loadStateAdapter: BookLoadStateAdapter

    override val binding: BookListFragmentBinding by viewBinding(
        BookListFragmentBinding::bind
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedElementEnterTransition = context?.let {
            TransitionInflater.from(it)
                .inflateTransition(android.R.transition.move)
        }
    }

    override fun initViews() {
        super.initViews()

        activity?.intent?.let { intent ->
            bookListViewModel.handleDeepLink(intent)?.let { bookId ->
                activity?.intent?.data = null
                openBookPreview(bookId = bookId)
            }
        }

        initLoadData()
    }

    private fun openBookPreview(bookId: Int) {
        lifecycleScope.launch {
            bookListViewModel.dataFlow.take(1).collectLatest { data ->
                when (data) {
                    is Result.Success -> {
                        dismissProgress()
                        goToPreview(data.data)
                    }

                    is Result.Error -> {

                    }

                    is Result.Loading -> {
                        //ignore
                    }
                }
            }
        }
        bookListViewModel.getBookById(bookId)
    }


    private fun initLoadData() {
        var items = emptyList<BookModel>()

        if (::bookListAdapter.isInitialized) {
            items = bookListAdapter.snapshot().items
        } else {
            showProgress()
        }

        initAdapter(items)
    }

    private fun initAdapter(items: List<BookModel>) {
        bookListAdapter = BookListAdapter(onItemClick = { book ->
            goToPreview(book)
        })

        loadStateAdapter = BookLoadStateAdapter { bookListAdapter.retry() }

        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        binding.recyclerView.adapter = bookListAdapter.withLoadStateFooter(loadStateAdapter)

        viewLifecycleOwner.lifecycleScope.launch {
            if (items.isNotEmpty()) {
                bookListAdapter.submitData(PagingData.from(items))
            }

            bookListViewModel.getPagingData().cachedIn(lifecycleScope).collectLatest {
                bookListAdapter.submitData(it)
            }
        }

        initLoadStatListener()
    }

    private fun initLoadStatListener() {
        bookListAdapter.addLoadStateListener { loadState ->
            val refreshState = loadState.source.refresh

            when (refreshState) {
                is LoadState.Loading -> {
                    //ignore
                }

                is LoadState.NotLoading -> {
                    dismissProgress()
                }

                is LoadState.Error -> {
                    dismissProgress()
                }
            }
        }
    }

    private fun goToPreview(book: BookModel) {
        val navOptions = NavOptions.Builder()
            .setEnterAnim(R.anim.zoom_in)
            .setExitAnim(R.anim.zoom_out)
            .setPopEnterAnim(R.anim.zoom_in)
            .setPopExitAnim(R.anim.zoom_out)
            .build()

        findNavController().navigate(
            BookListFragmentDirections.actionBookListFragmentToPreview(
                book
            ), navOptions = navOptions
        )
    }

    private fun showProgress() {
        activity?.supportFragmentManager?.let { supportFragmentManager ->
            progressDialog.show(supportFragmentManager, "progress_dialog")
        }
    }

    private fun dismissProgress() {
        if (progressDialog.isVisible) {
            progressDialog.dismiss()
        }
    }
}