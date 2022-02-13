package com.example.pipi.domain.model.schedule


data class DaySchedule(
    val date: String,
    val name: String,
    val part: String,
    val setInfos: List<SetInfo>,
    val type: String,
    val unit: String
)