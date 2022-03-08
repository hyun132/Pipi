package com.example.pipi.presentation.main.schedule.model

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.pipi.domain.model.schedule.DayScheduleDto
import com.example.pipi.domain.model.schedule.SetInfo
import java.time.LocalDate


/**
 * 서버에서 받아온 DatyScheduleDto의 데이터타입을 변환해준 클래스
 * 하루에 지정된 운동 스케줄 정보를 담고 있음.
 */
@RequiresApi(Build.VERSION_CODES.O)
fun List<DayScheduleDto>.toScheduleItem(): MutableMap<String, MutableList<ExerciseSchedule>> {
    val scheduleItemMap: MutableMap<String, MutableList<ExerciseSchedule>> = mutableMapOf()

    //여기서 같은 날짜는 같이 묶어야 함.
    this.forEach { exerciseSchedule ->
        val schedule = ExerciseSchedule(
            name = exerciseSchedule.name,
            parts = StringToParts(exerciseSchedule.part),
            type = StringToType(exerciseSchedule.type),
            exerciseUnit = exerciseSchedule.unit.toInt(),
            sets = exerciseSchedule.setInfos
        )
//        val date = LocalDate.parse(exerciseSchedule.date)
        if (scheduleItemMap.containsKey(exerciseSchedule.date)) {
            scheduleItemMap[exerciseSchedule.date]?.add(schedule)
        } else {
            scheduleItemMap[exerciseSchedule.date] = mutableListOf(schedule)
        }
    }
    return scheduleItemMap
}
//fun ExerciseSchedule.toScheduleItem(date: String):ScheduleItem{
//    return ScheduleItem(
//        date = date,
//
//    )
//}


//data class ScheduleItem(
//    val date: Calendar? = null,
//    val dayExercise: MutableList<ExerciseSchedule> = mutableListOf()
//)

data class DaySchedule(
    val date: String,
    val name: String,
    val part: String,
    val setInfos: MutableList<SetInfo>,
    val type: String,
    val unit: String
)

data class ExerciseSchedule(
    val name: String,
    val parts: Parts,
    val exerciseUnit: Int,
    val type: ScheduleType,
    val sets: List<SetInfo>
)
//data class ScheduleItem(
//    val date: LocalDate? = null,
//    val dayExercise: MutableList<ExerciseSchedule> = mutableListOf()
//)
//exerciseSchedule 을 각 운동의 세트수를 표시하기 위한
