package com.example.pipi.presentation.main.calendar

enum class CalendarScreenType {
    Calendar,
    Detail;

    companion object {
        fun fromRoute(route: String?): CalendarScreenType =
            when (route?.substringBefore("/")) {
                Calendar.name -> Calendar
                Detail.name -> Detail
                null -> Calendar
                else -> throw IllegalArgumentException("Route $route is not recognized.")
            }
    }
}