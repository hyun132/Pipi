package com.example.pipi.presentation.main.calendar.makeschedule

import android.annotation.SuppressLint
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
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
    val viewModel: ScheduleViewModel by viewModel()
    val scope = rememberCoroutineScope()
    val modalDataType = remember { mutableStateOf(DataType.PARTS) }
    val openDialog = remember { mutableStateOf(false) }
    Scaffold(topBar = { DrawTopAppbar(calendar, goBack) }) {
        Row(
            Modifier
                .fillMaxWidth()
                .padding(start = 54.dp, end = 54.dp, top = 16.dp, bottom = 25.dp)
        ) {
            TextButton(
                onClick = {
                    modalDataType.value = DataType.PARTS
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
                    text = viewModel.selectedPart.value?.name ?: "부위 선택",
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
                    viewModel.selectedPart.value?.let {
                        modalDataType.value = DataType.EQUIPMENT
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
                    text = viewModel.selectedPart.value?.name ?: "장비 선택",
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


        val tempItem = ExerciseItem(
            Data("1", "등"),
            Data("1", "바벨"), exercise = Data
                ("2", "운동이름"), 1, 1, 1
        )
        ///// 임시
        Button(onClick = {
            openDialog.value = true
        }) {
            Text(text = "temp")
        }
        DrawAlertDialog(
            item = tempItem,
            openDialog,
            { Timber.i("${it.exercise.name} 가 삭제됨") },
            { Timber.i("취소") })
        //////////
        DrawModalBottomSheet(
            modalBottomSheetState,
            data = if (modalDataType.value == DataType.PARTS) viewModel.partsData else viewModel.equipmentData,
            close = { modalBottomSheetState.hideModalBottomSheet(scope) }
        ) { item ->
            if (modalDataType.value == DataType.PARTS) viewModel.selectedPart.value = item
            else viewModel.selectedEquipmentData.value = item

            modalBottomSheetState.hideModalBottomSheet(scope)
        }
    }
}

@ExperimentalMaterialApi
@Composable
fun DrawModalBottomSheet(
    sheetState: ModalBottomSheetState,
    data: List<Data>,
    close: () -> Unit,
    itemClick: (Data) -> Unit
) {
    ModalBottomSheetLayout(sheetContent = {
        Column(
            Modifier
                .fillMaxWidth()
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
fun DrawAlertDialog(
    item: ExerciseItem,
    openDialog: MutableState<Boolean>,
    confirm: (ExerciseItem) -> Unit,
    cancel: (ExerciseItem) -> Unit
) {
    /**
     * TODO : Dialog로 바꾸고 나머지 만들 것.
     */
    Dialog(
        onDismissRequest = {
            // Dismiss the dialog when the user clicks outside the dialog or on the back
            // button. If you want to disable that functionality, simply use an empty
            // onCloseRequest.
            openDialog.value = false
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
                            openDialog.value = false
                        })
                Button(
                    onClick = {
                        cancel(item)
                        openDialog.value = false
                    },
                    modifier = Modifier
                        .weight(1f)
                        .clickable {
                            openDialog.value = false
                        }
                ) {
                    Text("취소")
                }
            }
        }
    )
}

data class ExerciseItem(
    val parts: Data,
    val equipment: Data,
    val exercise: Data,
    var reps: Int,
    var weight: Int,
    var set: Int
)

enum class ButtonClickState {
    CLICKED, NOT_CLICKED
}

@Composable
//버튼을 클릭하면 외부의 선택된 아이템을 이 아이템으로 변경
//선택된 아이템과 같은 아이템인 경우 색상을 변경하도록 함.
fun DrawFilteringButton(filterData: Data, onClick: () -> Unit) {
    OutlinedButton(onClick = {
        onClick()
        /**
         * 클릭하면 observe하고 있던 상태를 변경해야함 (선택된 아이템)
         */
    }, Modifier.border(1.dp, color = BRAND_SECOND)) {
        Text(text = filterData.name)
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


data class Data(val id: String, val name: String)

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

enum class DataType {
    PARTS,
    EQUIPMENT
}