package org.app.bookify

import androidx.compose.ui.window.ComposeUIViewController
import org.app.bookify.di.initKoin

fun MainViewController() = ComposeUIViewController(
    configure = {
        initKoin()
    }
) {
    App()
}