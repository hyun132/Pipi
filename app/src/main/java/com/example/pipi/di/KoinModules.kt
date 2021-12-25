package com.example.pipi.di

import com.example.pipi.data.remote.PipiService
import com.example.pipi.data.repository.LoginRepositoryImpl
import com.example.pipi.data.repository.MemberRepositoryImpl
import com.example.pipi.data.repository.SignUpRepositoryImpl
import com.example.pipi.domain.repository.LogInRepository
import com.example.pipi.domain.use_case.CheckPhoneAuthUseCase
import com.example.pipi.domain.use_case.LogInUseCase
import com.example.pipi.domain.use_case.RequestPhoneAuthMessageUseCase
import com.example.pipi.domain.use_case.SignUpUseCase
import com.example.pipi.presentation.login.LoginViewModel
import com.example.pipi.presentation.main.MainViewModel
import com.example.pipi.presentation.main.calendar.CalendarViewModel
import com.example.pipi.presentation.signup.SignupViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewmodelModules = module {
    viewModel { LoginViewModel(get()) }
    viewModel { MainViewModel(get()) }
    viewModel { SignupViewModel(get(),get(),get()) }
    viewModel { CalendarViewModel() }
}

val repositoryModules = module {
    factory<LogInRepository> { LoginRepositoryImpl(get()) }
//    single { LoginRepositoryImpl(get()) }
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