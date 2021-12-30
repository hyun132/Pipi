package com.example.pipi.di

import com.example.pipi.data.remote.PipiService
import com.example.pipi.data.repository.LoginRepositoryImpl
import com.example.pipi.data.repository.MemberRepositoryImpl
import com.example.pipi.data.repository.SignUpRepositoryImpl
import com.example.pipi.data.repository.UserInfoRepositoryImpl
import com.example.pipi.domain.repository.LogInRepository
import com.example.pipi.domain.repository.UserInfoRepository
import com.example.pipi.domain.use_case.*
import com.example.pipi.presentation.duplicated.PhoneAuthViewModel
import com.example.pipi.presentation.login.LoginViewModel
import com.example.pipi.presentation.main.MainViewModel
import com.example.pipi.presentation.main.calendar.CalendarViewModel
import com.example.pipi.presentation.setting.ResetUserPasswordViewModel
import com.example.pipi.presentation.signup.SignupViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModules = module {
    viewModel { LoginViewModel(get(), get()) }
    viewModel { MainViewModel(get()) }
    viewModel { SignupViewModel(get()) }
    viewModel { CalendarViewModel() }
    viewModel { PhoneAuthViewModel(get(), get()) }
    viewModel { ResetUserPasswordViewModel(get()) }
}

val repositoryModules = module {
    factory<LogInRepository> { LoginRepositoryImpl(get()) }
    factory<UserInfoRepository> { UserInfoRepositoryImpl(get()) }
//    single { LoginRepositoryImpl(get()) }
    single { SignUpRepositoryImpl(get()) }
    single { MemberRepositoryImpl(get()) }
    single { PipiService().loginApi }
}

val useCaseModules = module {
    single { LogInUseCase(get()) }
    single { AutoLogInUseCase(get()) }
    single { SignUpUseCase(get()) }
    single { RequestPhoneAuthMessageUseCase(get()) }
    single { CheckPhoneAuthUseCase(get()) }
    single { ResetPasswordUseCase(get()) }
}

val pipiModules = listOf(viewModelModules, repositoryModules, useCaseModules)