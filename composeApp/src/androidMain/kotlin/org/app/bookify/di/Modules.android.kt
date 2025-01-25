package org.app.bookify.di

import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.okhttp.OkHttp
import org.koin.core.module.Module
import org.koin.dsl.module

/** This KMP keywords expect to have certain objects, now this
 * will act as interface now KMP wanted us the implementation of this expect
 * **/
actual val platformModule: Module
    get() = module {
        single<HttpClientEngine> { OkHttp.create() }
    }