package org.app.bookify

import androidx.compose.runtime.Composable
import org.app.bookify.book.presentation.book_list.BookListScreenRoot
import org.app.bookify.book.presentation.book_list.BookListViewModel
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel

@Composable
@Preview
fun App() {
    val viewModel = koinViewModel<BookListViewModel>()
    BookListScreenRoot(
        viewModel = viewModel,
        onBookClick = {}
    )
}