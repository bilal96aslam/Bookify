package org.app.bookify.app

import kotlinx.serialization.Serializable

sealed interface Route {
    /** bundle each feature in an app into a navigation graph,
     * graph is just a bundle with multiple screens that all belong together
    **/
    @Serializable
    data object BookGraph: Route

    // because in this way compose able to Serialize to another screen
    @Serializable
    data object BookList : Route

    @Serializable
    data class BookDetail(val id: String) : Route
}