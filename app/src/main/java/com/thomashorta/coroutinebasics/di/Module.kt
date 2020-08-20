package com.thomashorta.coroutinebasics.di

import com.thomashorta.coroutinebasics.WordViewModel
import com.thomashorta.coroutinebasics.data.FakeWordService
import com.thomashorta.coroutinebasics.data.WordRepository
import com.thomashorta.coroutinebasics.domain.GetWordListUseCase
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val koinModule = module {
    single {
        FakeWordService()
    }

    single {
        WordRepository(get())
    }

    single {
        GetWordListUseCase(get())
    }

    viewModel {
        WordViewModel(get())
    }
}