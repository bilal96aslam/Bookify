package org.app.bookify.core.domain

sealed interface DataError : Error {
    enum class Remote : DataError {
        REQ_TIMEOUT,
        TOO_MANY_REQ,
        NO_INTERNET,
        SERVER,
        SERIALIZATION,
        UNKNOWN
    }

    enum class Local : DataError {
        DISK_FULL,
        UNKNOWN
    }
}