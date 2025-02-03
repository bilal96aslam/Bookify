package org.app.bookify.book.data.repository

import androidx.sqlite.SQLiteException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.app.bookify.book.data.database.FavouriteBookDao
import org.app.bookify.book.data.mappers.toBook
import org.app.bookify.book.data.mappers.toBookEntity
import org.app.bookify.book.data.network.RemoteBookDataSource
import org.app.bookify.book.domain.Book
import org.app.bookify.book.domain.BookRepository
import org.app.bookify.core.domain.DataError
import org.app.bookify.core.domain.EmptyResult
import org.app.bookify.core.domain.Result
import org.app.bookify.core.domain.map

class DefaultBookRepository(
    private val remoteBookSource: RemoteBookDataSource,
    private val favouriteBookDao: FavouriteBookDao
) : BookRepository {

    override suspend fun searchBooks(query: String): Result<List<Book>, DataError.Remote> {
        return remoteBookSource
            .searchBooks(query)
            .map { dto ->
                dto.results.map { it.toBook() }
            }
    }

    override suspend fun getBookDescription(bookWorkId: String): Result<String?, DataError> {
        val localResult = favouriteBookDao.getFavouriteBook(bookWorkId)
        return if (localResult == null) {
            return remoteBookSource
                .getBookDetails(bookWorkId)
                .map { it.description }
        } else {
            Result.Success(localResult.description)
        }
    }

    override fun getFavouriteBooks(): Flow<List<Book>> {
        return favouriteBookDao.getFavouriteBooks()
            .map { entities ->
                entities.map {
                    it.toBook()
                }
            }
    }

    override fun isBookFavourite(bookId: String): Flow<Boolean> {
        return favouriteBookDao.getFavouriteBooks()
            .map { entities ->
                entities.any { it.id == bookId }
            }
    }

    override suspend fun markAsFavourite(book: Book): EmptyResult<DataError.Local> {
        return try {
            favouriteBookDao.upsert(book.toBookEntity())
            Result.Success(Unit)
        } catch (exception: SQLiteException) {
            Result.Error(DataError.Local.DISK_FULL)
        }
    }

    override suspend fun deleteFromFavourite(bookId: String) {
        return favouriteBookDao.deleteFavouriteBook(bookId)
    }
}