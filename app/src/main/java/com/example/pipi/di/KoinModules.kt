package com.example.pipi.di

import com.example.pipi.feature_login.data.data_source.remote.PipiService
import com.example.pipi.feature_login.data.repository.LoginRepositoryImpl
import com.example.pipi.feature_login.data.repository.SignUpRepositoryImpl
import com.example.pipi.feature_login.domain.use_case.CoroutineUseCase
import com.example.pipi.feature_login.domain.use_case.LogInUseCase
import com.example.pipi.feature_login.domain.use_case.RequestPhoneAuthMessageUseCase
import com.example.pipi.feature_login.domain.use_case.SignUpUseCase
import com.example.pipi.feature_login.presentation.login.LoginViewModel
import com.example.pipi.feature_login.presentation.signup.SignupViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.factory
import org.koin.dsl.module

val viewmodelModules = module {
    viewModel { LoginViewModel(get()) }
    viewModel { SignupViewModel(get()) }
}

val repositoryModules = module {
    single { LoginRepositoryImpl(get()) }
    single { SignUpRepositoryImpl(get()) }
    single { PipiService().loginApi }
}

val useCaseModules = module {
    single { LogInUseCase(get()) }
    single { SignUpUseCase(get()) }
    single { RequestPhoneAuthMessageUseCase(get()) }
}

val pipiModules = listOf(viewmodelModules, repositoryModules,useCaseModules)