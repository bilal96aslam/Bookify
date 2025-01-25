package org.app.bookify

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import org.app.bookify.app.App
import org.app.bookify.di.initKoin

fun main() {
    initKoin()
    application {
        Window(
            onCloseRequest = ::exitApplication,
            title = "Bookify",
        ) {
            App()
        }
    }
}