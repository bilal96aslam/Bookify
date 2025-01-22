package org.app.bookify.book.presentation.book_list.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.app.bookify.book.domain.Book

@Composable
fun BookList(
    modifier: Modifier = Modifier,
    books: List<Book>,
    onBookClick: (Book) -> Unit,
    scrollState: LazyListState = rememberLazyListState()
) {
    LazyColumn(
        modifier = modifier,
        state = scrollState,
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        items(
            items = books,
            key = {
                it.id  // in this way LazyColumn will recomposed only the changed item, like content are same
            }
        ) { item ->
            BookListItem(
                book = item,
                modifier = Modifier
                    .widthIn(700.dp) // for desktop
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                onClick = { onBookClick(item) }
            )
        }
    }
}