package com.example.pipi.presentation.main.schedule.makeschedule

/**
 * modalBottomSheet, Dialog의 visibility, type, data 현재 화면에 보여지는 운동목록을 가지고 있다.
 */
data class NewExerciseUiState(
    var modalVisibility: Boolean = false,
    var modalDataType: ModalDataType = ModalDataType.PARTS,
    var dialogVisibility: Boolean = false,
    var dialogDataType: DialogType = DialogType.CREATE,
    //TODO : api나오면 부위랑 장비 공통으로 상속받는 상위클래스 하나 만들것.
    var modalBottomSheetDataList: List<BottomSheetData> = emptyList(),
    //TODO: 이거 데이터도 어떻게 보내주실건지 백엔드개발자분께 확인할것 - 일단 리스트로
    var currentExerciseList: List<List<BottomSheetData>> = emptyList(),
)

enum class ModalDataType {
    PARTS,
    EQUIPMENT,
    EXERCISE
}

enum class DialogType {
    DELETE,
    CREATE,
    UPDATE
}
