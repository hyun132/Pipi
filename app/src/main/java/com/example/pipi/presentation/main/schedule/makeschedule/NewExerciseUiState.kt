package com.example.pipi.presentation.main.schedule.makeschedule

/**
 * modalBottomSheet, Dialog의 visibility, type, data 현재 화면에 보여지는 운동목록을 가지고 있다.
 */
data class NewExerciseUiState(
    var modalVisibility: Boolean = false,
    var modalDataType: ModalDataType = ModalDataType.PARTS,
    var dialogVisibility: Boolean = false,
    var dialogDataType: DialogType = DialogType.CREATE,
    var dataClickType: DataClickType = DataClickType.NORMAL,
    //TODO : api나오면 부위랑 장비 공통으로 상속받는 상위클래스 하나 만들것.
    var modalBottomSheetDataList: List<BottomSheetData> = emptyList(),
    //TODO: 이거 데이터도 어떻게 보내주실건지 백엔드개발자분께 확인할것 - 일단 리스트로
    var currentExerciseList:MutableList<List<ExerciseItem>> = mutableListOf()
)

/**
 * 화면에 뿌려줄 데이터로 각 운동의 하나의 세트 정보를 담고 있음.
 * Exercise의 모든데이터 가지고 있을 필요 없다고 생각해서.
 */
data class ExerciseItem (
    val id:String, // 얘는 운동id (이중배열일경우 어떤 row를 선택할지를 위한 id값 == 운동 id값)
//    val index:Int, // 얘는 삭제할 세트 구분하기 위한 인덱스값..이필요한가? 없을듯
    val weight:Int,
    val reps:Int
)

enum class DataClickType {
    NORMAL,
    DELETE
}

enum class ModalDataType(name:String) {
    PARTS("부위 선택"),
    EQUIPMENT("장비 선택"),
    EXERCISE("운동 선택")
}

enum class DialogType {
    DELETE,
    CREATE,
    UPDATE
}
