package com.tilenpint.minivideojournalapp.di

import com.tilenpint.minivideojournalapp.ui.screen.camera.CameraViewModelImpl
import com.tilenpint.minivideojournalapp.ui.screen.camera.CameraViewModel
import com.tilenpint.minivideojournalapp.ui.screen.home.HomeViewModel
import com.tilenpint.minivideojournalapp.ui.screen.home.HomeViewModelImpl
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val viewModelModule = module {
    viewModelOf(::CameraViewModelImpl) {
        bind<CameraViewModel>()
    }

    viewModelOf(::HomeViewModelImpl) {
        bind<HomeViewModel>()
    }
}