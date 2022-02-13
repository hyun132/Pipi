package com.example.pipi.domain.use_case

import com.example.pipi.domain.model.schedule.ScheduleResponse
import com.example.pipi.domain.repository.ScheduleRepository
import com.example.pipi.domain.use_case.base.CoroutineUseCase
import kotlinx.coroutines.Dispatchers

class GetMonthlyScheduleUseCase(private val repository: ScheduleRepository) :
    CoroutineUseCase<GetMonthlyScheduleUseCase.Params, ScheduleResponse>(Dispatchers.IO) {
    class Params(
        val phoneNumber: String,
        val isTrainer: Boolean,
        val yearMonth: String
    )

    override suspend fun execute(parameters: Params) =
        repository.getMonthlySchdule(
            parameters.phoneNumber,
            parameters.isTrainer,
            parameters.yearMonth
        )
}