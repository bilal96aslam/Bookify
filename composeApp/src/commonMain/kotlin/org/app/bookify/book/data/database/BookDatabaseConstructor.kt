package org.app.bookify.book.data.database

import androidx.room.RoomDatabaseConstructor

/** we need this constructor in our shared code to be able to initialize this db
the actual impl of that differs depending on the platform **/
@Suppress("NO_ACTUAL_FOR_EXPECT")
expect object BookDatabaseConstructor : RoomDatabaseConstructor<FavouriteBookDatabase> {
    override fun initialize(): FavouriteBookDatabase
}