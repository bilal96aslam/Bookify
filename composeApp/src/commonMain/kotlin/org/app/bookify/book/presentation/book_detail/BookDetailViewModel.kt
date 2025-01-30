package org.app.bookify.book.presentation.book_detail

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class BookDetailViewModel : ViewModel() {
    private val _bookDetailState = MutableStateFlow(BookDetailState())
    val bookDetailState = _bookDetailState.asStateFlow()

    fun onAction(action: BookDetailAction) {
        when(action) {
            is BookDetailAction.OnBookSelectedChange -> {
                _bookDetailState.update {
                    it.copy(book = action.book)
                }
            }
            BookDetailAction.OnFavouriteClick -> {

            }
            else -> Unit
        }
    }
}