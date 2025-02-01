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

    override suspend fun getBookDescription(bookWorkId: String): Result<String?, DataError> {
        return remoteBookSource
            .getBookDetails(bookWorkId)
            .map { it.description }
    }
}