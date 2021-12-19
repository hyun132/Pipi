package com.example.pipi.domain.use_case.base

import com.example.pipi.global.result.Result
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import timber.log.Timber

abstract class CoroutineUseCase<in T, R>(private val coroutineDispatcher: CoroutineDispatcher) {
    suspend operator fun invoke(params: T): Flow<Result<R>> = flow {
        var data: R? = null
        emit(Result.Loading(data))
        try {
            data = execute(params)
            emit(Result.Success(data = data))
        } catch (e: Exception) {
            Timber.d(e)
            emit(Result.Error(message = "Http Exception", data = data))
        }
    }.flowOn(coroutineDispatcher)

    @Throws(RuntimeException::class)
    protected abstract suspend fun execute(parameters: T): R
}