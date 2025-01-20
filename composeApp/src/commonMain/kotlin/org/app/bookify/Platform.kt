package org.app.bookify

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform