package org.app.bookify.book.data.database

import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

interface FavouriteBookDao {
    // insert it of it doesn't exist update it if exist
    @Upsert
    suspend fun upsert(bookEntity: BookEntity)

    /**  Reactivity it will automatically triggers when we change something about the favt book table
     * flow is already Async so that's why not suspend fun
     */
    @Query("SELECT * FROM BookEntity")
    fun getFavouriteBooks(): Flow<List<BookEntity>>

    @Query("SELECT * FROM BookEntity WHERE id = :id")
    suspend fun getFavouriteBook(id: String): BookEntity?

    @Query("DELETE FROM BookEntity WHERE id = :id")
    suspend fun deleteFavouriteBook(id: String)
}