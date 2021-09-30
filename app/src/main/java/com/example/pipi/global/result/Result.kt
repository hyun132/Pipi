package com.example.pipi.global.result

import java.lang.Exception

sealed class Result<out R> {
    data class Success<out T>(val data:T) : Result<T>()
    data class Error(val exception: Exception) : Result<Nothing>()
    object loading : Result<Nothing>()
}

val <T> Result<T>.data:T?
    get() = (this as? Result.Success)?.data