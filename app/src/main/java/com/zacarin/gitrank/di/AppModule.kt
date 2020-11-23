package com.zacarin.gitrank.di

import com.zacarin.gitrank.api.ApiAccess
import com.zacarin.gitrank.api.RepositoryRepository
import com.zacarin.gitrank.viewmodel.RepositoryViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.core.qualifier.named
import org.koin.dsl.module

val apiAccessModules = module {
    single { ApiAccess() }
}

val repositoryModules = module {
    single { RepositoryRepository(get()) }
}

val viewModelModules = module {
    viewModel { RepositoryViewModel(get()) }
}

val appContext = module {
    single(named("appContext")) { androidContext() }
}

val appComponent: List<Module> = listOf(
    apiAccessModules, repositoryModules, viewModelModules, appContext)
