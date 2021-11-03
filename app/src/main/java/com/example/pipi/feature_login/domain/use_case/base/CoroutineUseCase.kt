package com.example.pipi.feature_login.domain.use_case.base

import com.example.pipi.global.result.Result
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import timber.log.Timber
import java.lang.Exception

abstract class CoroutineUseCase<in T,R>(private val coroutineDispatcher:CoroutineDispatcher){
    suspend operator fun invoke(params:T):Result<R>{
        return try {
            withContext(coroutineDispatcher){
                execute(params).let {
                    Result.Success(it)
                }
            }
        } catch (e:Exception){
            Timber.d(e)
            Result.Error(e)
        }
    }

    @Throws(RuntimeException::class)
    protected abstract suspend fun execute(parameters: T): R
}