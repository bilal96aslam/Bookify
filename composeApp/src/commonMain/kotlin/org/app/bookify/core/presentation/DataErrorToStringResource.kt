package org.app.bookify.core.presentation

import bookify.composeapp.generated.resources.Res
import bookify.composeapp.generated.resources.error_disk_full
import bookify.composeapp.generated.resources.error_no_internet
import bookify.composeapp.generated.resources.error_request_timeout
import bookify.composeapp.generated.resources.error_serialization
import bookify.composeapp.generated.resources.error_too_many_requests
import bookify.composeapp.generated.resources.error_unknown
import org.app.bookify.core.domain.DataError

fun DataError.toUiText(): UiText {
    val stringRes = when(this) {
        DataError.Local.DISK_FULL -> Res.string.error_disk_full
        DataError.Local.UNKNOWN -> Res.string.error_unknown
        DataError.Remote.REQ_TIMEOUT -> Res.string.error_request_timeout
        DataError.Remote.TOO_MANY_REQ -> Res.string.error_too_many_requests
        DataError.Remote.NO_INTERNET -> Res.string.error_no_internet
        DataError.Remote.SERVER -> Res.string.error_unknown
        DataError.Remote.SERIALIZATION -> Res.string.error_serialization
        DataError.Remote.UNKNOWN -> Res.string.error_unknown
    }
    return UiText.StringResourceId(stringRes)
}
