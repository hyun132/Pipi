package com.example.pipi.domain.model.schedule

import com.google.gson.annotations.SerializedName

data class ScheduleResponse(
    @SerializedName("data") val scheduleDto: List<DayScheduleDto>
)