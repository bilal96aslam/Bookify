package org.app.bookify.book.data.network

import org.app.bookify.book.data.dto.BookWorkDto
import org.app.bookify.book.data.dto.SearchResponseDto
import org.app.bookify.core.domain.DataError
import org.app.bookify.core.domain.Result

interface RemoteBookDataSource {
    suspend fun searchBooks(
        query: String,
        resultLimit: Int? = null
    ): Result<SearchResponseDto, DataError.Remote>

    suspend fun getBookDetails(
       bookWorkId: String
    ): Result<BookWorkDto, DataError.Remote>
}