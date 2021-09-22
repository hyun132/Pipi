package com.example.pipi.di

import com.example.pipi.feature_login.presentation.login.LoginViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewmodelModules = module {
    viewModel { LoginViewModel() }
}