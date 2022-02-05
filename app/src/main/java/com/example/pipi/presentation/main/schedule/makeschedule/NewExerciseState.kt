package com.example.pipi.presentation.main.schedule.makeschedule

/**
 * 새로 추가할 운동 아이템의 정보를 임시로 가지고 있는 state
 */
data class NewExerciseState(
    var parts: BottomSheetData? = null,
    var equipment: BottomSheetData? = null,
    var exercise: BottomSheetData? = null,
    var weight: Int? = null,
    var reps: Int? = null,
    var sets: Int? = null
)