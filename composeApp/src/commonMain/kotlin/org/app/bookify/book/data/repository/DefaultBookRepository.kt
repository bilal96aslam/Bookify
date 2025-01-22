package org.app.bookify.book.data.repository

import org.app.bookify.book.data.mappers.toBook
import org.app.bookify.book.data.network.RemoteBookDataSource
import org.app.bookify.book.domain.Book
import org.app.bookify.book.domain.BookRepository
import org.app.bookify.core.domain.DataError
import org.app.bookify.core.domain.Result
import org.app.bookify.core.domain.map

class DefaultBookRepository(
    private val remoteBookSource: RemoteBookDataSource
) : BookRepository {

    override suspend fun searchBooks(query: String): Result<List<Book>, DataError.Remote> {
        return remoteBookSource
            .searchBooks(query)
            .map { dto ->
                dto.results.map { it.toBook() }
            }
    }
}