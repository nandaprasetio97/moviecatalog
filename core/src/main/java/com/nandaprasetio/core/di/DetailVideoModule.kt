package com.nandaprasetio.core.di

import com.nandaprasetio.core.domain.usecase.detailvideo.DefaultDetailVideoUseCase
import com.nandaprasetio.core.domain.usecase.detailvideo.DetailVideoUseCase
import org.koin.core.module.Module
import org.koin.dsl.module

val detailVideoModule: Module = module {
    factory<DetailVideoUseCase> { DefaultDetailVideoUseCase(get()) }
}
