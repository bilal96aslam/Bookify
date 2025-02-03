package org.app.bookify.book.domain

import kotlinx.coroutines.flow.Flow
import org.app.bookify.core.domain.DataError
import org.app.bookify.core.domain.EmptyResult
import org.app.bookify.core.domain.Result

interface BookRepository {
    suspend fun searchBooks(query: String): Result<List<Book>, DataError.Remote>
    suspend fun getBookDescription(bookWorkId: String): Result<String?, DataError>

    fun getFavouriteBooks(): Flow<List<Book>>
    fun isBookFavourite(bookId: String): Flow<Boolean>
    suspend fun markAsFavourite(book: Book): EmptyResult<DataError.Local>
    suspend fun deleteFromFavourite(bookId: String)
}