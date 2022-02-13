package com.example.pipi.presentation.main.schedule.makeschedule

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.pipi.R
import com.example.pipi.global.constants.ui.Colors
import com.example.pipi.global.constants.ui.Colors.BRAND_SECOND
import com.example.pipi.global.constants.ui.Colors.PRIMARY_TEXT
import com.example.pipi.global.constants.ui.Colors.SECONDARY_TEXT_GHOST
import com.example.pipi.global.constants.ui.Components.DefaultTopAppbar
import com.example.pipi.global.constants.utils.CalendarUtils.getCurrentDateString
import com.example.pipi.global.constants.utils.showModalBottomSheet
import kotlinx.coroutines.CoroutineScope
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
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 30.dp, end = 30.dp)
        ) {
            DrawSelectCategoryArea(viewModel, modalBottomSheetState, scope)
            Text(
                text = "삭제",
                style = MaterialTheme.typography.body1,
                textAlign = TextAlign.End,
                modifier = Modifier.fillMaxWidth()
            )
            LazyColumn(modifier = Modifier.fillMaxSize(), content = {
                items(viewModel.state.currentExerciseList) { exerciseList ->
                    Text(text = exerciseList[0].id, style = MaterialTheme.typography.subtitle2)
                    LazyRow(modifier = Modifier.fillMaxWidth(), content = {
                        itemsIndexed(exerciseList) { idx, item ->
                            ExerciseItemView(idx, item, deleteMode = false)
                        }
                    })

                }
            })
        }
        SetModalAndDialog(viewModel, modalBottomSheetState, scope)
    }
}

/**
 * 운동 부위/장비 선택하는 영역
 */
@Composable
@ExperimentalMaterialApi
fun DrawSelectCategoryArea(
    viewModel: MakeNewExerciseViewModel,
    modalBottomSheetState: ModalBottomSheetState,
    scope: CoroutineScope
) {
    Row(
        Modifier
            .fillMaxWidth()
            .padding(start = 18.dp, end = 18.dp, top = 16.dp, bottom = 23.dp),
        horizontalArrangement = Arrangement.SpaceBetween
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
                style = MaterialTheme.typography.h5,
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
                tint = Color.Unspecified,
                modifier = Modifier.padding(end = 3.dp)
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
                style = MaterialTheme.typography.h5,
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
                tint = Color.Unspecified,
                modifier = Modifier.padding(end = 3.dp)
            )
        }
    }
}

@ExperimentalMaterialApi
@Composable
fun SetModalAndDialog(
    viewModel: MakeNewExerciseViewModel,
    modalBottomSheetState: ModalBottomSheetState,
    scope: CoroutineScope
) {
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
                confirm = { viewModel.createExercise() },
                viewModel = viewModel
            )
            DialogType.UPDATE -> DeleteExerciseDialog(
                item = tempItem,
                close = viewModel::closeDialog,
                confirm = {},
                { Timber.i("취소") })
            DialogType.DELETE -> DeleteExerciseDialog(
                item = tempItem,
                close = viewModel::closeDialog,
                confirm = { exercise -> viewModel.deleteExercise(exercise) },
                { Timber.i("취소") })
        }
    }
    if (viewModel.state.modalVisibility) {
        DrawModalBottomSheet(
            modalBottomSheetState,
            data = viewModel.state.modalBottomSheetDataList,
            modalType = viewModel.state.modalDataType,
            close = {
                viewModel.closeModal()
//                modalBottomSheetState.hideModalBottomSheet(scope)
            }
        ) { item ->
            viewModel.onModalItemSelect(item)
        }
    }
}

@ExperimentalMaterialApi
@Composable
fun DrawModalBottomSheet(
    sheetState: ModalBottomSheetState,
    data: List<BottomSheetData>,
    modalType: ModalDataType,
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
                Text(text = modalType.name, Modifier.weight(1f))
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
                        style = MaterialTheme.typography.subtitle1,
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
                style = MaterialTheme.typography.h4
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
                Text("취소",
                    color = Colors.ALERT,
                    modifier = Modifier
                        .wrapContentHeight()
                        .weight(1f)
                        .clickable {
                            cancel(item)
                            close()
                        })
                Text("삭제",
                    color = Colors.ALERT,
                    modifier = Modifier
                        .wrapContentHeight()
                        .weight(1f)
                        .clickable {
                            confirm(item)
                            close()
                        })
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
                    .background(
                        color = MaterialTheme.colors.surface,
                        shape = RoundedCornerShape(14.dp)
                    )
            ) {
                Row(
                    Modifier
                        .fillMaxWidth()
                        .height(48.dp), verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "운동 추가",
                        Modifier.weight(1f),
                        style = MaterialTheme.typography.h5
                    )
                    Icon(
                        imageVector = ImageVector.vectorResource(id = R.drawable.ic_cancel),
                        contentDescription = "닫기"
                    )
                }
                Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                    TextWithUnit(
                        viewModel.exerciseToCreate.weight?.toString() ?: "",
                        viewModel::setWeight,
                        "Kg"
                    )
                    TextWithUnit(
                        viewModel.exerciseToCreate.reps?.toString() ?: "",
                        viewModel::setReps,
                        "Reps"
                    )
                    TextWithUnit(
                        viewModel.exerciseToCreate.sets?.toString() ?: "",
                        viewModel::setSets,
                        "Sets"
                    )
                }
                Row(Modifier.fillMaxWidth()) {
                    Text("취소",
                        color = Colors.ALERT,
                        modifier = Modifier
                            .wrapContentHeight()
                            .clickable {
                                close()
                            })
                    Text("확인",
                        color = Colors.ALERT,
                        modifier = Modifier
                            .wrapContentHeight()
                            .clickable {
                                confirm(item)
                                close()
                            })
                }
            }
        }
    )
}

@Composable
fun TextWithUnit(text: String, onChange: (String) -> Unit, unit: String) {
    Row() {
        BasicTextField(
            value = text,
            onValueChange = { value -> onChange(value) },
            modifier = Modifier.width(64.dp)
        )
        Text(text = unit, style = MaterialTheme.typography.subtitle1)
    }
}

@Composable
fun ExerciseItemView(index: Int, exerciseItem: ExerciseItem, deleteMode: Boolean) {
    ConstraintLayout(
        modifier = Modifier
            .width(54.dp)
            .height(88.dp)
    ) {
        val (item, icon) = createRefs()

        Box(modifier = Modifier
            .width(54.dp)
            .height(84.dp)
            .padding(9.dp)
            .constrainAs(item) {
                bottom.linkTo(parent.bottom)
                start.linkTo(parent.start)
            }
            .border(1.dp, color = SECONDARY_TEXT_GHOST, shape = RoundedCornerShape(12.dp))) {
            Column() {
                Text(
                    text = index.toString(),
                    style = MaterialTheme.typography.body1,
                    modifier = Modifier.padding(bottom = 9.dp)
                )
                Text(text = "${exerciseItem.weight} kg", style = MaterialTheme.typography.body2)
                Text(text = "${exerciseItem.reps} reps", style = MaterialTheme.typography.body2)
            }
        }
        if (deleteMode) {
            Icon(
                imageVector = ImageVector.vectorResource(id = R.drawable.ic_cancel),
                contentDescription = "삭제",
                modifier = Modifier
                    .width(15.dp)
                    .height(15.dp)
                    .constrainAs(icon) {
                        top.linkTo(parent.top)
                        end.linkTo(parent.end)
                    }
            )
        }
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
//        Text(text = filter.title, style = MaterialTheme.typography.subtitle1)
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


data class BottomSheetData(val id: String, val name: String)



