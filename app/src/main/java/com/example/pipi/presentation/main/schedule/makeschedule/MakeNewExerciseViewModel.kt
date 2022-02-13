package com.example.pipi.presentation.main.schedule.makeschedule

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.text.isDigitsOnly
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
    private fun setEquipment(equipment: BottomSheetData) {
        exerciseToCreate = exerciseToCreate.copy(equipment = equipment)
        setModalDataType(ModalDataType.EXERCISE)
    }

    private fun setExercise(exercise: BottomSheetData) {
        exerciseToCreate = exerciseToCreate.copy(exercise = exercise)
    }

    fun setWeight(weight: String) {
        if (weight.isValidIntType()) exerciseToCreate =
            exerciseToCreate.copy(weight = weight.toIntOrNull())
    }

    fun setSets(sets: String) {
        if (sets.isValidIntType()) exerciseToCreate =
            exerciseToCreate.copy(sets = sets.toIntOrNull())
    }

    fun setReps(reps: String) {
        if (reps.isValidIntType()) exerciseToCreate =
            exerciseToCreate.copy(reps = reps.toIntOrNull())
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
        Timber.i("운동을 삭제하였습니다.")
    }

    fun updateExercise(exercise: Exercise) {
        //먼저 화면목록에서만 지우고
        //저장을 누르면 서버에 요청할것.
        Timber.i("해당 운동을 수정 하였습니다.")
    }

    fun createExercise() {
        //먼저 화면목록에만 추가하고
        //저장을 누르면 서버에 요청할것.
        if (exerciseToCreate.equipment != null && exerciseToCreate.parts != null && exerciseToCreate.sets != null &&
            (exerciseToCreate.sets!! >= 0)
        ) {
            /**
             * 데이터를 어떤 row에 넣을것인지 찾는 방법에 대한 고민이 필요(운동마다 고유값이 있는가? 아니라면 무엇이 각 운동의 분류 기준이 될 것인가?
             *
             * 예상
             * 운동1 -> 1set - 2set - 3set
             * 운동2 -> 1set - 2set
             * 운동3 -> 1set - 2set - 3set - 4set
             */

            /**
             * 이럴 경우 서버에 어떻게 데이터 전달할건지?
             */
            val tempItem = ExerciseItem(
                id = "${exerciseToCreate.parts!!.name}_${exerciseToCreate.equipment!!.name} ${exerciseToCreate.exercise!!.name}",
                weight = exerciseToCreate.weight?:0,
                reps = exerciseToCreate.reps?:0
            )
            val tempList = List(exerciseToCreate.sets!!) { tempItem }

            state = state.copy(currentExerciseList = state.currentExerciseList.apply {
                add(tempList)
            })
            state.currentExerciseList.forEach { it.forEach {item-> print(item) } }
            //운동 등록 후 state 초기화
            exerciseToCreate =  NewExerciseState()
            Timber.i("새로운 운동계획을 추가하였습니다.")
        }
    }

    fun showDialog() {
        state = state.copy(dialogVisibility = true)
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
    fun onModalItemSelect(item: BottomSheetData) {
        when (state.modalDataType) {
            ModalDataType.PARTS -> {
                setParts(item)
                state = state.copy(modalDataType = ModalDataType.EQUIPMENT)
            }
            ModalDataType.EQUIPMENT -> {
                setEquipment(item)
                state = state.copy(modalDataType = ModalDataType.EXERCISE)
            }
            ModalDataType.EXERCISE -> {
                setExercise(item)
                showDialog()
            }
        }
        changeModalBottomSheetData()
    }

    /**
     * item클릭했을때 삭제를 누른 상태이면 삭제 다이얼로그, 아니면 수정 다이얼로그 띄울 것.
     */
    fun onExerciseItemClick(exercise: Exercise) {

    }
}

fun String.isValidIntType(): Boolean {
    return this.isDigitsOnly() && this.toInt() >= 0
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
    var name: String = "",
    var weight: Int,
    var reps: Int,
    var sets: Int
)