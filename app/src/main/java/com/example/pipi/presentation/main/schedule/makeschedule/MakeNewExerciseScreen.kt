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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.input.KeyboardType
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
import com.example.pipi.presentation.main.ui.theme.PipiTheme
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

    Scaffold(topBar = { DrawTopAppbar(calendar, goBack, textAlign = TextAlign.Center) }) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 30.dp, end = 30.dp)
        ) {
            DrawSelectCategoryArea(viewModel, modalBottomSheetState, scope)
            Text(
                text = "??????",
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
 * ?????? ??????/?????? ???????????? ??????
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
                text = viewModel.exerciseToCreate.parts?.name ?: "?????? ??????",
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
                contentDescription = "????????????",
                tint = Color.Unspecified,
                modifier = Modifier.padding(end = 3.dp)
            )
        }

        TextButton(
            onClick = {
                viewModel.exerciseToCreate.parts?.let {
                    viewModel.setModalDataType(ModalDataType.EQUIPMENT)

                    //TODO : ????????? uiEvent??? ??? ?????????
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
                text = viewModel.exerciseToCreate.equipment?.name ?: "?????? ??????",
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
                contentDescription = "????????????",
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
    //?????? ?????????????????? ??? ????????? viewmodel??? ?????????
    val tempItem = Exercise(
        parts = BottomSheetData("1", "???"),
        equipment = BottomSheetData("1", "??????"),
        exercise = BottomSheetData("2", "????????????"),
        name = "???????????????",
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
                { Timber.i("??????") })
            DialogType.DELETE -> DeleteExerciseDialog(
                item = tempItem,
                close = viewModel::closeDialog,
                confirm = { exercise -> viewModel.deleteExercise(exercise) },
                { Timber.i("??????") })
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
                    contentDescription = "??????",
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
fun DrawTopAppbar(calendar: Calendar, goBack: () -> Unit, textAlign: TextAlign = TextAlign.Start) {
    DefaultTopAppbar(navComponent = {
        Icon(
            imageVector = ImageVector.vectorResource(id = R.drawable.ic_back),
            contentDescription = "????????????",
            modifier = Modifier.clickable { goBack() }
        )
    }, title = {
        Row(
            Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = calendar.getCurrentDateString() + "????????????????????????",
                style = MaterialTheme.typography.h4,
                textAlign = textAlign
            )
        }
    }, optionComponent = {
        Text(text = "??????", style = MaterialTheme.typography.subtitle1)
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
     * TODO : Dialog??? ????????? ????????? ?????? ???.
     */
    Dialog(
        onDismissRequest = {
            close()
        }, content = {
            Text(
                "?????? ????????? ?????????????????????????",
                style = MaterialTheme.typography.h4,
                fontSize = 16.sp,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )

            Row(Modifier.fillMaxWidth()) {
                Text("??????",
                    color = Colors.ALERT,
                    modifier = Modifier
                        .wrapContentHeight()
                        .weight(1f)
                        .clickable {
                            cancel(item)
                            close()
                        })
                Text("??????",
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
 * TODO : dialog ????????? ???????????? ??? ?????? ?????? ??
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
                    .padding(12.dp)
                    .background(
                        color = MaterialTheme.colors.surface,
                        shape = RoundedCornerShape(14.dp)
                    )
            ) {
                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(bottom = 12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "?????? ??????",
                        Modifier.weight(1f),
                        style = MaterialTheme.typography.h5
                    )
                    Icon(
                        imageVector = ImageVector.vectorResource(id = R.drawable.ic_cancel),
                        contentDescription = "??????",
                        modifier = Modifier.padding(end = 15.dp)
                    )
                }
                Spacer(modifier = Modifier.height(19.dp))
                Text(
                    text = item.name,
                    style = MaterialTheme.typography.body1,
                    modifier = Modifier.padding(start = 4.dp)
                )
                Spacer(modifier = Modifier.height(19.dp))
                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(start = 4.dp, end = 4.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
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
                Spacer(modifier = Modifier.weight(1f))
                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(end = 13.dp),
                    verticalAlignment = Alignment.Bottom,
                    horizontalArrangement = Arrangement.End
                ) {
                    Text("??????",
                        color = Colors.ALERT,
                        modifier = Modifier
                            .wrapContentHeight()
                            .clickable {
                                close()
                            })
                    Spacer(modifier = Modifier.width(22.dp))
                    Text(
                        "??????",
                        color = Colors.PRIMARY_TEXT,
                        modifier = Modifier
                            .wrapContentHeight()
                            .clickable {
                                confirm(item)
                                close()
                            },
                        style = MaterialTheme.typography.subtitle1
                    )
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
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier
                .width(64.dp)
                .drawBehind {
                    val y = size.height
                    drawLine(
                        Color.Black,
                        Offset(0f, y),
                        Offset(size.width, y),
                        3F
                    )
                }
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
            .constrainAs(item) {
                bottom.linkTo(parent.bottom)
                start.linkTo(parent.start)
            }
            .border(1.dp, color = SECONDARY_TEXT_GHOST, shape = RoundedCornerShape(12.dp))) {
            Column(Modifier.padding(9.dp)) {
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
                contentDescription = "??????",
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
//????????? ???????????? ????????? ????????? ???????????? ??? ??????????????? ??????
//????????? ???????????? ?????? ???????????? ?????? ????????? ??????????????? ???.
fun DrawFilteringButton(filterBottomSheetData: BottomSheetData, onClick: () -> Unit) {
    OutlinedButton(onClick = {
        onClick()
        /**
         * ???????????? observe?????? ?????? ????????? ??????????????? (????????? ?????????)
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
 * api ????????? entity, dto, model????????? ?????? ????????? ???.
 */


data class BottomSheetData(val id: String, val name: String)



