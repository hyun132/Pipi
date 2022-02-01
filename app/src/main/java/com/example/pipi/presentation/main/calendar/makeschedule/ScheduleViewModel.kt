package com.example.pipi.presentation.main.calendar.makeschedule

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel

class ScheduleViewModel: ViewModel() {
    val partsData = listOf<Data>(
        Data("1", "가슴"),
        Data("2", "등"),
        Data("3", "어깨"),
        Data("4", "하체"),
        Data("5", "이두박근"),
        Data("6", "삼두박근"),
        Data("7", "복근"),
        Data("8", "유산소")
    )
    val equipmentData = listOf<Data>(
        Data("1", "전체"),
        Data("2", "바벨"),
        Data("3", "덤벨"),
        Data("4", "EZ바"),
        Data("5", "머신"),
        Data("6", "머신2"),
        Data("7", "머신3"),
    )

    val selectedPart:MutableState<Data?> = mutableStateOf(null)
    val selectedEquipmentData = mutableStateOf(equipmentData[0])
}