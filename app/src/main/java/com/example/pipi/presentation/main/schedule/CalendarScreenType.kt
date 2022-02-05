package com.example.pipi.presentation.main.schedule

enum class CalendarScreenType {
    MonthlySchedule,
    Detail;

    companion object {
        fun fromRoute(route: String?): CalendarScreenType =
            when (route?.substringBefore("/")) {
                MonthlySchedule.name -> MonthlySchedule
                Detail.name -> Detail
                null -> MonthlySchedule
                else -> throw IllegalArgumentException("Route $route is not recognized.")
            }
    }
}