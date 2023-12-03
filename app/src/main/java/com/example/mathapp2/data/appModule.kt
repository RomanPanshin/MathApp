package com.example.mathapp2.data

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import com.example.mathapp2.data.db.AppDatabase
import com.example.mathapp2.data.repository.MathRepository
import com.example.mathapp2.viewmodel.MyViewModel

val appModule = module {

    // Single instance of AppDatabase
    single { AppDatabase.getDatabase(get()) }

    // Single instance of MathProblemDao
    single { get<AppDatabase>().mathProblemDao() }

    // Single instance of MathRepository
    single { MathRepository(get()) }

    // MyViewModel ViewModel
    viewModel { MyViewModel(get()) }
}
