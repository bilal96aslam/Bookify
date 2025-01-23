package org.app.bookify.book.presentation.book_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.app.bookify.book.domain.Book
import org.app.bookify.book.domain.BookRepository
import org.app.bookify.core.domain.onError
import org.app.bookify.core.domain.onSuccess
import org.app.bookify.core.presentation.toUiText

/** presentation -> data <- domain **/

class BookListViewModel(
    private val bookRepository: BookRepository
) : ViewModel() {

    private var cachedBook = emptyList<Book>()
    private var searchJob: Job? = null
    private val _state = MutableStateFlow(BookListState())
    val state = _state
        .onStart {
            /** as soon we start listening to this flow
             * then this will trigger for the first time
             **/
            if (cachedBook.isEmpty()) {
                observeSearchQuery()
            }
        }.stateIn(
            viewModelScope,
            // while there is active subscriber of our state we will execute this flow chain here
            SharingStarted.WhileSubscribed(5000L),
            _state.value
        )

    fun onAction(action: BookListAction) {
        when (action) {
            is BookListAction.OnBookClick -> {

            }

            is BookListAction.OnSearchQueryChange -> {
                // update is a method that updates the state in a thread safe manner
                // so can call this from multiple diff threads
                _state.update {
                    it.copy(searchQuery = action.query)
                }
            }

            is BookListAction.OnTabSelected -> {
                _state.update {
                    it.copy(selectedTabIndex = action.index)
                }
            }
        }
    }

    private fun observeSearchQuery() {
        state
            .map { it.searchQuery }
            .distinctUntilChanged()  // is a method used to filter out consecutive duplicate emissions
            .debounce(500L)
            .onEach { query ->
                when {
                    query.isBlank() -> {
                        _state.update {
                            it.copy(
                                errorMessage = null,
                                searchResults = cachedBook
                            )
                        }
                    }

                    query.length >= 2 -> {
                        /** while search is happens user type something again to cancel the
                         * previous one we can cancel this
                         **/
                        searchJob?.cancel()
                        searchJob = searchBooks(query)
                    }

                }
            }.launchIn(viewModelScope)
    }

    private fun searchBooks(query: String) = viewModelScope.launch {
        _state.update {
            it.copy(isLoading = true)
        }
        bookRepository.searchBooks(query)
            .onSuccess { results ->
                _state.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = null,
                        searchResults = results
                    )
                }
            }
            .onError { error ->
                _state.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = error.toUiText(),
                        searchResults = emptyList()
                    )
                }
            }
    }

}