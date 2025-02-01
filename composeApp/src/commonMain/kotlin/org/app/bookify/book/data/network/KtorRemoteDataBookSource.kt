package org.app.bookify.book.data.network

import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import org.app.bookify.book.data.dto.BookWorkDto
import org.app.bookify.book.data.dto.SearchResponseDto
import org.app.bookify.core.data.safeCall
import org.app.bookify.core.domain.DataError
import org.app.bookify.core.domain.Result

private const val BASE_URL = "https://openlibrary.org"

class KtorRemoteDataBookSource(
    private val httpClient: HttpClient
) : RemoteBookDataSource {

    override suspend fun searchBooks(
        query: String,
        resultLimit: Int?
    ): Result<SearchResponseDto, DataError.Remote> {
        return safeCall<SearchResponseDto> {
            httpClient.get(
                urlString = "$BASE_URL/search.json"
            ) {
                parameter("q", query)
                parameter("limit", resultLimit)
                parameter("language", "eng")
                // to tell which fields we are interested
                parameter(
                    "fields",
                    "key,title,author_name,author_key,cover_edition_key,cover_i,ratings_average,ratings_count,first_publish_year,language,number_of_pages_median,edition_count"
                )
            }
        }
    }

    override suspend fun getBookDetails(bookWorkId: String):
            Result<BookWorkDto, DataError.Remote> {
        return safeCall<BookWorkDto> {
            httpClient.get(
                urlString = "$BASE_URL/works/$bookWorkId.json"
            )
        }
    }

}