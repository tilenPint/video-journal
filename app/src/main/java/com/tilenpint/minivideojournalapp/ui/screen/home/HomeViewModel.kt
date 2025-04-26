package com.tilenpint.minivideojournalapp.ui.screen.home

import kotlinx.coroutines.flow.Flow

interface HomeViewModel {
    val state: Flow<HomeUiState?>
}