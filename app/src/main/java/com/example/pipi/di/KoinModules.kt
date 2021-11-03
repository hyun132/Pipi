package com.example.pipi.di

import com.example.pipi.feature_login.data.data_source.remote.PipiService
import com.example.pipi.feature_login.data.repository.LoginRepositoryImpl
import com.example.pipi.feature_login.data.repository.MemberRepositoryImpl
import com.example.pipi.feature_login.data.repository.SignUpRepositoryImpl
import com.example.pipi.feature_login.domain.use_case.CheckPhoneAuthUseCase
import com.example.pipi.feature_login.domain.use_case.LogInUseCase
import com.example.pipi.feature_login.domain.use_case.RequestPhoneAuthMessageUseCase
import com.example.pipi.feature_login.domain.use_case.SignUpUseCase
import com.example.pipi.feature_login.presentation.login.LoginViewModel
import com.example.pipi.feature_login.presentation.main.MainViewModel
import com.example.pipi.feature_login.presentation.signup.SignupViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewmodelModules = module {
    viewModel { LoginViewModel(get()) }
    viewModel { MainViewModel(get()) }
    viewModel { SignupViewModel(get(),get(),get()) }
}

val repositoryModules = module {
    single { LoginRepositoryImpl(get()) }
    single { SignUpRepositoryImpl(get()) }
    single { MemberRepositoryImpl(get()) }
    single { PipiService().loginApi }
}

val useCaseModules = module {
    single { LogInUseCase(get()) }
    single { SignUpUseCase(get()) }
    single { RequestPhoneAuthMessageUseCase(get()) }
    single { CheckPhoneAuthUseCase(get()) }
}

val pipiModules = listOf(viewmodelModules, repositoryModules,useCaseModules)