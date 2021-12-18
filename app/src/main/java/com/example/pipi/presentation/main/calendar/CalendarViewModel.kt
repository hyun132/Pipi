package com.example.pipi.presentation.main.calendar

import BaseCalendar
import androidx.lifecycle.ViewModel
import timber.log.Timber

class CalendarViewModel:ViewModel() {

    val calendar = com.example.pipi.global.constants.utils.BaseCalendar()

    init {
        calendar.initBaseCalendar {
            Timber.d("init")
        }
    }


}