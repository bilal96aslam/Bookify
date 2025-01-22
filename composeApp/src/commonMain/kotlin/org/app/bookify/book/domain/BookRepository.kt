package org.app.bookify.book.domain

import org.app.bookify.core.domain.DataError
import org.app.bookify.core.domain.Result

interface BookRepository {
    suspend fun searchBooks(query: String): Result<List<Book>, DataError.Remote>
}