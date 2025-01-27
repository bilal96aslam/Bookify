package org.app.bookify.book.presentation.book_detail

import org.app.bookify.book.domain.Book

data class BookDetailState(
    val isLoading: Boolean = false,
    val isFavourite: Boolean = false,
    val book: Book? = null
)