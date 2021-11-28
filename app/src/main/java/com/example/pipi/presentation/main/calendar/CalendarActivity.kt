package com.example.pipi.presentation.main.calendar

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import com.example.pipi.global.constants.utils.BaseCalendar
import com.example.pipi.presentation.main.ui.theme.PipiTheme
import timber.log.Timber

class CalendarActivity : ComponentActivity() {
    val baseCalendar = BaseCalendar()

    init {
        baseCalendar.initBaseCalendar {
            Timber.d("init")
        }
    }

    @ExperimentalFoundationApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PipiTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    CalendarScreen(baseCalendar, ::onCalendarItemClicked)
//                    DrawBaseCalendar(baseCalendar, ::onCalendarItemClicked)
                }
            }
        }

    }

    /**
     * 클릭 이벤트는 이후 디자인 나오면 적용할것.
     */
    private fun onCalendarItemClicked(item: BaseCalendar.DataModel) {
        Timber.d(item.day.toString() + " clicked!!!")
    }

}