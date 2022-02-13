package com.example.pipi.di

import com.example.pipi.data.remote.PipiService
import com.example.pipi.data.repository.LoginRepositoryImpl
import com.example.pipi.data.repository.MemberRepositoryImpl
import com.example.pipi.data.repository.SignUpRepositoryImpl
import com.example.pipi.data.repository.UserInfoRepositoryImpl
import com.example.pipi.domain.repository.LogInRepository
import com.example.pipi.domain.repository.MemberRepository
import com.example.pipi.domain.repository.UserInfoRepository
import com.example.pipi.domain.use_case.*
import com.example.pipi.presentation.duplicated.PhoneAuthViewModel
import com.example.pipi.presentation.login.LoginViewModel
import com.example.pipi.presentation.main.MainViewModel
import com.example.pipi.presentation.main.schedule.CalendarViewModel
import com.example.pipi.presentation.main.schedule.makeschedule.MakeNewExerciseViewModel
import com.example.pipi.presentation.main.schedule.makeschedule.ScheduleViewModel
import com.example.pipi.presentation.main.ui.member.MemberRequestViewModel
import com.example.pipi.presentation.main.ui.member.MemberViewModel
import com.example.pipi.presentation.setting.ResetUserPasswordViewModel
import com.example.pipi.presentation.signup.SignupViewModel
import com.example.pipi.presentation.start.StartViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModules = module {
    viewModel { LoginViewModel(get()) }
    viewModel { StartViewModel(get()) }
    viewModel { MainViewModel() }
    viewModel { MemberViewModel(get()) }
    viewModel { MemberRequestViewModel(get(), get(), get()) }
    viewModel { SignupViewModel(get()) }
    viewModel { CalendarViewModel() }
    viewModel { ScheduleViewModel() }
    viewModel { PhoneAuthViewModel(get(), get()) }
    viewModel { ResetUserPasswordViewModel(get()) }
    viewModel { MakeNewExerciseViewModel() }
}

val repositoryModules = module {
    single<LogInRepository> { LoginRepositoryImpl(get()) }
    single<UserInfoRepository> { UserInfoRepositoryImpl(get()) }
//    single { LoginRepositoryImpl(get()) }
    single { SignUpRepositoryImpl(get()) }
    single<MemberRepository> { MemberRepositoryImpl(get()) }
    single { PipiService().loginApi }
}

val useCaseModules = module {
    single { LogInUseCase(get()) }
    single { AutoLogInUseCase(get()) }
    single { SignUpUseCase(get()) }
    single { RequestPhoneAuthMessageUseCase(get()) }
    single { CheckPhoneAuthUseCase(get()) }
    single { ResetPasswordUseCase(get()) }
    single { GetMemberRequestsUseCase(get()) }
    single { GetMyMembersUseCase(get()) }
    single { DenyMemberRequestUseCase(get()) }
    single { ApproveMemberRequestUseCase(get()) }
    single { GetMonthlyScheduleUseCase(get()) }
}

val pipiModules = listOf(viewModelModules, repositoryModules, useCaseModules)