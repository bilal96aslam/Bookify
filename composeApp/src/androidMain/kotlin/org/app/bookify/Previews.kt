package org.app.bookify

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import org.app.bookify.book.domain.Book
import org.app.bookify.book.presentation.book_list.BookListScreen
import org.app.bookify.book.presentation.book_list.BookListState
import org.app.bookify.book.presentation.book_list.components.BookSearchBar

@Preview
@Composable
private fun BookSearchPreview() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
    ) {
        BookSearchBar(
            searchQuery = "",
            onSearchQueryChange = {},
            onImeSearch = {},
            modifier = Modifier.fillMaxWidth()
        )
    }
}

private val books = (1..100).map {
    Book(
        id = it.toString(),
        title = "Book $it",
        imageUrl = "https://www.test.com",
        authors = listOf("Author name"),
        description = "Description $it",
        languages = emptyList(),
        ratingCount = 5,
        numPages = 100,
        firstPublishYear = "2006",
        averageRating = 4.5,
        numEditions = 3,
    )
}

@Preview
@Composable
private fun BookListPreview() {
    BookListScreen(
        state = BookListState(
            searchResults = books
        ),
        onAction = {}
    )
}
