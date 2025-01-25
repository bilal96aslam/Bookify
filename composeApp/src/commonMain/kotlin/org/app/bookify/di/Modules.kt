package org.app.bookify.di

import org.app.bookify.book.data.network.KtorRemoteDataBookSource
import org.app.bookify.book.data.network.RemoteBookDataSource
import org.app.bookify.book.data.repository.DefaultBookRepository
import org.app.bookify.book.domain.BookRepository
import org.app.bookify.book.presentation.book_list.BookListViewModel
import org.app.bookify.core.data.HttpClientFactory
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

/** This KMP keywords expect to have certain objects, now this
 * will act as interface now KMP wanted us the implementation of this expect
 * **/
expect val platformModule: Module

val sharedModule = module {
    single { HttpClientFactory.create(get()) }
    /** 'singleOf()' this will instruct koin to try to create the instance
     * with dependencies it already have present 'bind<RemoteBookDataSource>()'
     * this is actual dependency we have pass 'KtorRemoteDataBookSource' this is the impl.
     **/
    singleOf(::KtorRemoteDataBookSource).bind<RemoteBookDataSource>()
    singleOf(::DefaultBookRepository).bind<BookRepository>()
    viewModelOf(::BookListViewModel)
}