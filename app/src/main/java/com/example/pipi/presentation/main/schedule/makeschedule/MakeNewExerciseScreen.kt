package com.example.pipi.presentation.main.schedule.makeschedule

import android.annotation.SuppressLint
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.pipi.R
import com.example.pipi.global.constants.ui.Colors
import com.example.pipi.global.constants.ui.Colors.BRAND_SECOND
import com.example.pipi.global.constants.ui.Colors.PRIMARY_TEXT
import com.example.pipi.global.constants.ui.Colors.SECONDARY_TEXT_GHOST
import com.example.pipi.global.constants.ui.Components.DefaultTopAppbar
import com.example.pipi.global.constants.utils.CalendarUtils.getCurrentDateString
import com.example.pipi.global.constants.utils.hideModalBottomSheet
import com.example.pipi.global.constants.utils.showModalBottomSheet
import org.koin.androidx.compose.viewModel
import timber.log.Timber
import java.util.*


@ExperimentalMaterialApi
@SuppressLint("UnrememberedMutableState")
@Composable
fun MakeNewExerciseScreen(
    goBack: () -> Unit,
    calendar: Calendar,
    modalBottomSheetState: ModalBottomSheetState,
) {
    val viewModel: MakeNewExerciseViewModel by viewModel()
    val scope = rememberCoroutineScope()

    Scaffold(topBar = { DrawTopAppbar(calendar, goBack) }) {
        Row(
            Modifier
                .fillMaxWidth()
                .padding(start = 54.dp, end = 54.dp, top = 16.dp, bottom = 25.dp)
        ) {
            TextButton(
                onClick = {
                    viewModel.setModalDataType(ModalDataType.PARTS)
                    modalBottomSheetState.showModalBottomSheet(scope)
                }, modifier = Modifier
                    .border(
                        1.dp,
                        SECONDARY_TEXT_GHOST,
                        RoundedCornerShape(4.5.dp)
                    )
                    .height(22.dp)
                    .width(98.dp),
                contentPadding = PaddingValues(0.dp)
            ) {
                Text(
                    text = viewModel.exerciseToCreate.parts?.name ?: "부위 선택",
                    style = MaterialTheme.typography.subtitle2,
                    fontSize = 12.sp,
                    lineHeight = 14.sp,
                    color = PRIMARY_TEXT,
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 8.dp, end = 8.dp)
                )
                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.ic_drop_down),
                    contentDescription = "부위선택",
                    tint = Color.Unspecified
                )
            }

            TextButton(
                onClick = {
                    viewModel.exerciseToCreate.parts?.let {
                        viewModel.setModalDataType(ModalDataType.EQUIPMENT)

                        //TODO : 이것도 uiEvent로 다 빼야됨
                        modalBottomSheetState.showModalBottomSheet(scope)
                    }
                }, modifier = Modifier
                    .border(
                        1.dp,
                        SECONDARY_TEXT_GHOST,
                        RoundedCornerShape(4.5.dp)
                    )
                    .height(22.dp)
                    .width(98.dp),
                contentPadding = PaddingValues(0.dp)
            ) {
                Text(
                    text = viewModel.exerciseToCreate.equipment?.name ?: "장비 선택",
                    style = MaterialTheme.typography.subtitle2,
                    fontSize = 12.sp,
                    lineHeight = 14.sp,
                    color = PRIMARY_TEXT,
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 8.dp, end = 8.dp)
                )
                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.ic_drop_down),
                    contentDescription = "장비선택",
                    tint = Color.Unspecified
                )
            }
        }

        //운동 삭제부분만들 때 이것도 viewmodel로 빼야됨
        val tempItem = Exercise(
            parts = BottomSheetData("1", "등"),
            equipment = BottomSheetData("1", "바벨"),
            exercise = BottomSheetData("2", "운동이름"),
            name = "테스트운동",
            1, 1, 1
        )

        if (viewModel.state.dialogVisibility) {
            when (viewModel.state.dialogDataType) {
                DialogType.CREATE -> CreateExerciseDialog(
                    item = tempItem,
                    close = viewModel::closeDialog,
                    confirm = {/* TODO : 세 값 모두 입력되면? 이 운동 객체 추가하도록 함.*/ viewModel.createExercise() },
                    viewModel
                )
                DialogType.UPDATE -> DeleteExerciseDialog(
                    tempItem,
                    viewModel::closeDialog,
                    {},
                    { Timber.i("취소") })
                DialogType.DELETE -> DeleteExerciseDialog(
                    tempItem,
                    viewModel::closeDialog,
                    { exercise -> viewModel.deleteExercise(exercise) },
                    { Timber.i("취소") })
            }
        }
        if (viewModel.state.modalVisibility) {
            DrawModalBottomSheet(
                modalBottomSheetState,
                data = viewModel.state.modalBottomSheetDataList,
                close = { modalBottomSheetState.hideModalBottomSheet(scope) }
            ) { item ->
                viewModel.onItemSelect(item)
            }
        }
    }
}

@ExperimentalMaterialApi
@Composable
fun DrawModalBottomSheet(
    sheetState: ModalBottomSheetState,
    data: List<BottomSheetData>,
    close: () -> Unit,
    itemClick: (BottomSheetData) -> Unit
) {
    ModalBottomSheetLayout(sheetContent = {
        Column(
            Modifier
                .fillMaxWidth()
                .height(270.dp)
                .padding(start = 16.dp, end = 16.dp, top = 12.dp)
        ) {
            Row(Modifier.fillMaxWidth()) {
                Text(text = "부위선택", Modifier.weight(1f))
                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.ic_cancel),
                    contentDescription = "닫기",
                    modifier = Modifier.clickable {
                        close()
                    })
            }

            LazyColumn(content = {
                items(data) { item ->
                    Text(
                        text = item.name,
                        Modifier
                            .padding(start = 16.dp, end = 16.dp)
                            .fillMaxWidth()
                            .height(50.dp)
                            .clickable {
                                itemClick(item)
                            }
                            .wrapContentHeight(),
                        style = MaterialTheme.typography.body2,
                    )
                    Divider(color = SECONDARY_TEXT_GHOST, thickness = 1.dp)
                }
            })
        }
    }, sheetState = sheetState, sheetShape = RoundedCornerShape(topEnd = 16.dp, topStart = 16.dp)) {
    }
}

@Composable
fun DrawTopAppbar(calendar: Calendar, goBack: () -> Unit) {
    DefaultTopAppbar(navComponent = {
        Icon(
            imageVector = ImageVector.vectorResource(id = R.drawable.ic_back),
            contentDescription = "뒤로가기",
            modifier = Modifier.clickable { goBack() }
        )
    }, title = {
        Row(
            Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = calendar.getCurrentDateString(),
                style = MaterialTheme.typography.subtitle2
            )
        }
    }, optionComponent = {
        Text(text = "확인", style = MaterialTheme.typography.subtitle1)
    })
}

@Composable
fun DeleteExerciseDialog(
    item: Exercise,
    close: () -> Unit,
    confirm: (Exercise) -> Unit,
    cancel: (Exercise) -> Unit
) {
    /**
     * TODO : Dialog로 바꾸고 나머지 만들 것.
     */
    Dialog(
        onDismissRequest = {
            // Dismiss the dialog when the user clicks outside the dialog or on the back
            // button. If you want to disable that functionality, simply use an empty
            // onCloseRequest.
            close()
        }, content = {
            Text(
                "해당 회원을 삭제하시겠습니까?",
                style = MaterialTheme.typography.h4,
                fontSize = 16.sp,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )

            Row(Modifier.fillMaxWidth()) {
                Text("삭제",
                    color = Colors.ALERT,
                    modifier = Modifier
                        .wrapContentHeight()
                        .weight(1f)
                        .clickable {
                            confirm(item)
                            close()
                        })
                Button(
                    onClick = {
                        cancel(item)
                        close()
                    },
                    modifier = Modifier
                        .weight(1f)
                        .clickable {
                            close()
                        }
                ) {
                    Text("취소")
                }
            }
        }
    )
}

/**
 * TODO : dialog 예쁘게 재사용할 수 있는 방법 ??
 */
@Composable
fun CreateExerciseDialog(
    item: Exercise,
    close: () -> Unit,
    confirm: (Exercise) -> Unit,
    viewModel: MakeNewExerciseViewModel
) {
    Dialog(
        onDismissRequest = { close() },
        content = {
            Column(
                Modifier
                    .fillMaxWidth()
                    .height(220.dp)
            ) {
                Row(
                    Modifier
                        .fillMaxWidth()
                        .height(48.dp), verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "운동 추가",
                        Modifier.weight(1f),
                        style = MaterialTheme.typography.subtitle2
                    )
                    Icon(
                        imageVector = ImageVector.vectorResource(id = R.drawable.ic_cancel),
                        contentDescription = "닫기"
                    )
                }
                Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                    TextWithUnit(
                        viewModel.exerciseToCreate.weight.toString(),
                        viewModel::setWeight,
                        "Kg"
                    )
                    TextWithUnit(
                        viewModel.exerciseToCreate.reps.toString(),
                        viewModel::setReps,
                        "Reps"
                    )
                    TextWithUnit(
                        viewModel.exerciseToCreate.sets.toString(),
                        viewModel::setSets,
                        "Sets"
                    )
                }
                Row(Modifier.fillMaxWidth()) {
                    Text("삭제",
                        color = Colors.ALERT,
                        modifier = Modifier
                            .wrapContentHeight()
                            .weight(1f)
                            .clickable {
                                confirm(item)
                                close()
                            })
                    Button(
                        onClick = {
                            close()
                        },
                        modifier = Modifier
                            .weight(1f)
                            .clickable {
                                close()
                            }
                    ) {
                        Text("취소")
                    }
                }
            }
        }
    )
}

@Composable
fun TextWithUnit(text: String, onChange: (String) -> Unit, unit: String) {
    Row() {
        TextField(
            value = text,
            onValueChange = { value -> onChange(value) },
            modifier = Modifier.width(64.dp)
        )
        Text(text = unit, style = MaterialTheme.typography.subtitle1)
    }
}

enum class ButtonClickState {
    CLICKED, NOT_CLICKED
}

@Composable
//버튼을 클릭하면 외부의 선택된 아이템을 이 아이템으로 변경
//선택된 아이템과 같은 아이템인 경우 색상을 변경하도록 함.
fun DrawFilteringButton(filterBottomSheetData: BottomSheetData, onClick: () -> Unit) {
    OutlinedButton(onClick = {
        onClick()
        /**
         * 클릭하면 observe하고 있던 상태를 변경해야함 (선택된 아이템)
         */
    }, Modifier.border(1.dp, color = BRAND_SECOND)) {
        Text(text = filterBottomSheetData.name)
    }
}

//@ExperimentalFoundationApi
//@Composable
//fun DrawFilterLayout(filter: Filter) {
//    Column(modifier = Modifier.fillMaxWidth()) {
//        Text(text = filter.title, style = MaterialTheme.typography.body2)
//        LazyVerticalGrid(cells = GridCells.Fixed(4)) {
//            items(filter.dataList) { filteritem ->
//                DrawFilteringButton(filterData = filteritem, onClick = {})
//            }
//        }
//    }
//}


/**
 * TODO :
 * api 나오면 entity, dto, model클래스 새로 정의할 것.
 */


//data class Filter(val title: String = "부위", val dataList: List<Data>)


data class BottomSheetData(val id: String, val name: String)



