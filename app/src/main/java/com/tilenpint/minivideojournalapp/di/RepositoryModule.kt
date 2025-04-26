package com.tilenpint.minivideojournalapp.di

import com.tilenpint.minivideojournalapp.repository.VideoRepository
import com.tilenpint.minivideojournalapp.repository.VideoRepositoryImpl
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val repositoryModule = module {
    singleOf(::VideoRepositoryImpl) {
        bind<VideoRepository>()
    }
}