package com.example.pipi.presentation.main.schedule.makeschedule

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import timber.log.Timber

class MakeNewExerciseViewModel : ViewModel() {

    var exerciseToCreate by mutableStateOf(NewExerciseState())
    var state by mutableStateOf(NewExerciseUiState())

    private fun setParts(parts: BottomSheetData) {
        exerciseToCreate = exerciseToCreate.copy(parts = parts)
    }

    /**
     * 장비를 고르면 바로 운동선택이 나와야 함.
     */
    private fun setEquitment(equipment: BottomSheetData) {
        exerciseToCreate = exerciseToCreate.copy(equipment = equipment)
        setModalDataType(ModalDataType.EXERCISE)
    }

    private fun setExercise(exercise: BottomSheetData) {
        exerciseToCreate = exerciseToCreate.copy(exercise = exercise)
    }

    fun setWeight(weight: String) {
        exerciseToCreate = exerciseToCreate.copy(weight = weight.toIntOrNull())
    }

    fun setSets(sets: String) {
        exerciseToCreate = exerciseToCreate.copy(sets = sets.toIntOrNull())
    }

    fun setReps(reps: String) {
        exerciseToCreate = exerciseToCreate.copy(reps = reps.toIntOrNull())
    }

    private fun changeModalBottomSheetData() {
        state = state.copy(
            modalBottomSheetDataList =
            when (state.modalDataType) {
                ModalDataType.PARTS -> partsData
                ModalDataType.EQUIPMENT -> equipmentData
                ModalDataType.EXERCISE -> exerciseData
            }
        )
    }

    fun deleteExercise(exercise: Exercise) {
        //먼저 화면목록에서만 지우고
        //저장을 누르면 서버에 요청할것.
    }

    fun createExercise() {
        //먼저 화면목록에만 추가하고
        //저장을 누르면 서버에 요청할것.
        Timber.i("새로운 운동계획을 추가하였습니다.")
    }

    fun closeDialog() {
        state = state.copy(dialogVisibility = false)
    }

    fun closeModal() {
        state = state.copy(modalVisibility = false)
    }

    /**
     * bottomSheet의 타입을 설정
     */
    fun setModalDataType(type: ModalDataType) {
        state = state.copy(modalDataType = type, modalVisibility = true)
        changeModalBottomSheetData()
    }

    /**
     * dialog의 타입을 설정
     */
    fun setDialogDataType(type: DialogType) {
        state = state.copy(dialogDataType = type)
    }

    /**
     * TODO : 여기 exercise 이상함 다시 작성할ㅇ것.
     */
    fun onItemSelect(item: BottomSheetData) {
        when (state.modalDataType) {
            ModalDataType.PARTS -> {
                setParts(item)
                state=state.copy(modalDataType= ModalDataType.EQUIPMENT)
            }
            ModalDataType.EQUIPMENT -> {
                setEquitment(item)
                state=state.copy(modalDataType= ModalDataType.EXERCISE)
            }
            ModalDataType.EXERCISE -> setExercise(item)
        }
        changeModalBottomSheetData()
    }
}

val partsData = listOf<BottomSheetData>(
    BottomSheetData("1", "가슴"),
    BottomSheetData("2", "등"),
    BottomSheetData("3", "어깨"),
    BottomSheetData("4", "하체"),
    BottomSheetData("5", "이두박근"),
    BottomSheetData("6", "삼두박근"),
    BottomSheetData("7", "복근"),
    BottomSheetData("8", "유산소")
)
val equipmentData = listOf<BottomSheetData>(
    BottomSheetData("1", "전체"),
    BottomSheetData("2", "바벨"),
    BottomSheetData("3", "덤벨"),
    BottomSheetData("4", "EZ바"),
    BottomSheetData("5", "머신"),
    BottomSheetData("6", "머신2"),
    BottomSheetData("7", "머신3"),
)
val exerciseData = listOf<BottomSheetData>(
    BottomSheetData("1", "전체"),
    BottomSheetData("2", "운동1"),
    BottomSheetData("3", "운동2"),
    BottomSheetData("4", "운동3"),
    BottomSheetData("5", "머신"),
    BottomSheetData("6", "머신2"),
    BottomSheetData("7", "머신3"),
)

data class Exercise(
    var parts: BottomSheetData,
    var equipment: BottomSheetData,
    var exercise: BottomSheetData,
    var name:String="",
    var weight: Int,
    var reps: Int,
    var sets: Int
)