package com.example.pipi.data.repository

import com.example.pipi.data.remote.PipiApi
import com.example.pipi.domain.model.schedule.ScheduleResponse
import com.example.pipi.domain.repository.ScheduleRepository

class ScheduleRepositoryImpl(private val api: PipiApi) : ScheduleRepository {

    override suspend fun getMonthlySchdule(
        phoneNumber: String,
        isTrainer: Boolean,
        yearMonth: String
    ): ScheduleResponse = api.getMonthlySchedule(phoneNumber, isTrainer, yearMonth)


    override suspend fun saveDaySchdule(scheduleData: ScheduleResponse): ScheduleResponse {
        return api.saveDaySchedule(scheduleData)
    }

}