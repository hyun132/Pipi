package com.example.pipi.global.constants.utils

/**
 * TODO: Ui관련 event, navigate 모두 UIEvent로 처리하도록 변경하고
 * 모든 데이터 다루는 로직들 viewModel로 뺄것. viewModel은 Screen단위로 만들어서 해당하는 데이터만 다룰 수 있도록 하자.
 */
sealed class UiEvent {
    object Success : UiEvent()
    object NavigateUp : UiEvent()
    data class ShowSnackbar(val message: String) : UiEvent()
}