package org.app.bookify.book.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters


@Database(entities = [BookEntity::class], version = 1)
@TypeConverters(StringListTypeConverter::class)
abstract class  FavouriteBookDatabase : RoomDatabase() {
    abstract val favouriteBookDao: FavouriteBookDao

    companion object {
        const val DB_NAME = "book.db"
    }
}