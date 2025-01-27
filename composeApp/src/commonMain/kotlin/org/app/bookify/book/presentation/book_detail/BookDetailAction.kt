package org.app.bookify.book.presentation.book_detail

import org.app.bookify.book.domain.Book

sealed interface BookDetailAction {
    data object OnBackClick : BookDetailAction
    data object OnFavouriteClick : BookDetailAction
    data class OnBookSelectedChange(val book: Book) : BookDetailAction
}