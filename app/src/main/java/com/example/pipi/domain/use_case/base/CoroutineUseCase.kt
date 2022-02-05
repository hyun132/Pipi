package com.example.pipi.domain.use_case.base

import com.bumptech.glide.load.HttpException
import com.example.pipi.global.result.Result
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.io.IOException

abstract class CoroutineUseCase<in T, R>(private val coroutineDispatcher: CoroutineDispatcher) {
    suspend operator fun invoke(params: T): Flow<Result<R>> = flow {
        var data: R? = null
        emit(Result.Loading(data))
        try {
            data = execute(params)
            emit(Result.Success(data = data))
        } catch (e: HttpException) {
            emit(Result.Error(message = "Http Exception", data = data))
        } catch (e: IOException) {
            emit(Result.Error(message = "IOException, check your internet connection", data = data))
        } catch (e: Exception) {
            emit(Result.Error(message = "Something wrong", data = data))
        }
    }.flowOn(coroutineDispatcher)

    @Throws(RuntimeException::class)
    abstract suspend fun execute(parameters: T): R
}