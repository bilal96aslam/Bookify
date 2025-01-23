package org.app.bookify

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import io.ktor.client.engine.HttpClientEngine
import org.app.bookify.book.data.network.KtorRemoteDataBookSource
import org.app.bookify.book.data.repository.DefaultBookRepository
import org.app.bookify.book.presentation.book_list.BookListScreenRoot
import org.app.bookify.book.presentation.book_list.BookListViewModel
import org.app.bookify.core.data.HttpClientFactory
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App(engine: HttpClientEngine) {
    BookListScreenRoot(
        viewModel = remember { BookListViewModel(
            bookRepository = DefaultBookRepository(
                remoteBookSource = KtorRemoteDataBookSource(
                    httpClient = HttpClientFactory.create(engine)
                )
            )
        ) },
        onBookClick = {}
    )
}