package org.app.bookify.book.data.dto

import kotlinx.serialization.Serializable

/** this dto should be serialize and deserialize with the diff type of serializer
 *  then where we can define our own logic
 **/
@Serializable(with = BookWorkDtoSerializer::class) // to find the des inside this object
data class BookWorkDto(
    val description: String? = null
)