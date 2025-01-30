package org.app.bookify.book.presentation.book_detail

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.app.bookify.book.presentation.book_detail.components.BlurredImageBackground
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun BookDetailRoot(
    modifier: Modifier = Modifier,
    viewModel: BookDetailViewModel = koinViewModel(),
    onBackClick: ()->Unit
) {
    val state by viewModel.bookDetailState.collectAsStateWithLifecycle()

    BookDetailScreen(
        state = state,
        onAction = { action->
            when(action) {
                BookDetailAction.OnBackClick -> onBackClick()
                else -> Unit
            }
            viewModel.onAction(action)
        }
    )
}

@Composable
private fun BookDetailScreen(
    state: BookDetailState,
    onAction: (BookDetailAction) -> Unit
) {
    BlurredImageBackground(
        imageUrl = state.book?.imageUrl,
        isFavourite = state.isFavourite,
        onFavouriteClick = {
            onAction(BookDetailAction.OnFavouriteClick)
        },
        onBackClick = {
            onAction(BookDetailAction.OnBackClick)
        },
        modifier = Modifier.fillMaxSize()
    ) {

    }
}