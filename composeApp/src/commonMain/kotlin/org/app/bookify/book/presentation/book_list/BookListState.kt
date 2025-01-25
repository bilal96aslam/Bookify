package org.app.bookify.book.presentation.book_list

import org.app.bookify.book.domain.Book
import org.app.bookify.core.presentation.UiText

data class BookListState(
    val searchQuery: String = "",
    val searchResults: List<Book> = emptyList(),
    val favoriteBooks: List<Book> = emptyList(),
    val isLoading: Boolean = false,
    val selectedTabIndex: Int = 0,
    val errorMessage: UiText? = null
)
 val books = (1..100).map {
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