package com.example.pipi.feature_login.domain.use_case

import com.example.pipi.feature_login.domain.repository.SignUpRepository

class SignUpUseCase(private val repository: SignUpRepository) {
    suspend operator fun invoke(id: String, password: String, name: String) {
        repository.signUp(id, password, name)
    }
}