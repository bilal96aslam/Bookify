package org.app.bookify

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import org.app.bookify.book.presentation.book_list.BookListScreenRoot
import org.app.bookify.book.presentation.book_list.BookListViewModel
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    BookListScreenRoot(
        viewModel = remember { BookListViewModel() },
        onBookClick = {}
    )
}