package com.example.pipi.domain.repository

import com.example.pipi.domain.model.schedule.ScheduleResponse

interface ScheduleRepository {
    suspend fun getMonthlySchdule(
        phoneNumber: String,
        isTrainer: Boolean,
        yearMonth: String
    ): ScheduleResponse

    /**
     * 새로 일정을 만들던, 수정을 하던 전체 데이터를 보냄
     */
    suspend fun saveDaySchdule(scheduleData:ScheduleResponse): ScheduleResponse
}