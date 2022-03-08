package com.example.pipi.presentation.main.schedule.model

import androidx.compose.ui.graphics.Color
import com.example.pipi.global.constants.ui.Colors
import java.time.LocalDate
import java.util.*

data class ScheduleItem(
    val date: LocalDate? = null,
    val dayExercise: MutableList<ExerciseSchedule> = mutableListOf()
)
//data class ExerciseItem (
//    val id:String, // 얘는 운동id (이중배열일경우 어떤 row를 선택할지를 위한 id값 == 운동 id값)
////    val index:Int, // 얘는 삭제할 세트 구분하기 위한 인덱스값..이필요한가? 없을듯
//    val weight:Int,
//    val reps:Int
//)

//data class Exercise(
//    var parts: BottomSheetData,
//    var equipment: BottomSheetData,
//    var exercise: BottomSheetData,
//    var name: String = "",
//    var weight: Int,
//    var reps: Int,
//    var sets: Int
//)


enum class ScheduleType {
    PT,
    AT
}

/**
 * 운동별로 다른 단위를 표현하기 위한 class
 * Unit1 -> '0'
 * Unit2 -> '1'
 * Unit3 -> '2'
 */
sealed class ExerciseUnit {
    data class Unit1(var kg: Int, var reps: Int, var sets: Int)
    data class Unit2(var reps: Int, var sets: Int)
    data class Unit3(var step: Int, var time: Int, var sets: Int)
}

enum class Parts(title: String) {
    WHOLE_PART("전체"),
    CHEST("가슴"),
    BACK("등"),
    SHOULDER("어깨"),
    LOWER_BODY("하체"),
    BICEPS("이두박근"),
    TRICEPS("삼두박근"),
    ABS("복근"),
    AEROBIC("유산소")
}

fun setColorByParts(parts: Parts): Color {
    return when (parts) {
        Parts.WHOLE_PART -> Colors.E_WHOLE_PART
        Parts.CHEST -> Colors.E_CHEST
        Parts.BACK -> Colors.E_BACK
        Parts.SHOULDER -> Colors.E_SHOULDER
        Parts.LOWER_BODY -> Colors.E_LOWER_BODY
        Parts.BICEPS -> Colors.E_BICEPS
        Parts.TRICEPS -> Colors.E_TRICEPS
        Parts.ABS -> Colors.E_ABS
        Parts.AEROBIC -> Colors.E_AEROBIC
    }
}

fun StringToParts(name: String): Parts {
    return when (name) {
        Parts.CHEST.name -> Parts.CHEST
        Parts.BACK.name -> Parts.BACK
        Parts.SHOULDER.name -> Parts.SHOULDER
        Parts.LOWER_BODY.name -> Parts.LOWER_BODY
        Parts.BICEPS.name -> Parts.BICEPS
        Parts.TRICEPS.name -> Parts.TRICEPS
        Parts.ABS.name -> Parts.ABS
        Parts.AEROBIC.name -> Parts.AEROBIC
        else -> Parts.WHOLE_PART
    }
}

fun StringToType(type: String): ScheduleType {
    return when (type) {
        "pt" -> ScheduleType.PT
        "at" -> ScheduleType.AT
        else -> ScheduleType.PT
    }
}

