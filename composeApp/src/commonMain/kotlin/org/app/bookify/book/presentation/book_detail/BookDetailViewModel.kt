package org.app.bookify.book.presentation.book_detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.app.bookify.app.Route
import org.app.bookify.book.domain.BookRepository
import org.app.bookify.core.domain.onSuccess

class BookDetailViewModel(
    private val bookRepository: BookRepository,
    /** that allow us to get nav args, nothing else
    then persistent map of item or bundles of item **/
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val bookId = savedStateHandle.toRoute<Route.BookDetail>().id
    private val _bookDetailState = MutableStateFlow(BookDetailState())
    val bookDetailState = _bookDetailState
        .onStart {
            fetchBookDescription()
            observeFavouriteBook()
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            _bookDetailState.value
        )

    fun onAction(action: BookDetailAction) {
        when (action) {
            is BookDetailAction.OnBookSelectedChange -> {
                _bookDetailState.update {
                    it.copy(book = action.book)
                }
            }

            BookDetailAction.OnFavouriteClick -> {
                viewModelScope.launch {
                    if (bookDetailState.value.isFavourite) {
                        bookRepository.deleteFromFavourite(bookId)
                    } else {
                        bookDetailState.value.book?.let {
                            bookRepository.markAsFavourite(it)
                        }
                    }
                }
            }

            else -> Unit
        }
    }

    private fun observeFavouriteBook() {
        bookRepository.isBookFavourite(bookId)
            .onEach { isFavourite ->
                _bookDetailState.update {
                    it.copy(isFavourite = isFavourite)
                }
            }.launchIn(viewModelScope)
    }

    private fun fetchBookDescription() {
        viewModelScope.launch {
            bookRepository.getBookDescription(bookId)
                .onSuccess { des ->
                    _bookDetailState.update {
                        it.copy(
                            book = it.book?.copy(description = des),
                            isLoading = false
                        )
                    }
                }
        }
    }
}