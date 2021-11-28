package com.example.pipi.presentation.main

import android.annotation.SuppressLint
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.consumePositionChange
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.example.pipi.R
import com.example.pipi.global.constants.ui.Colors.BL_USE
import com.example.pipi.global.constants.ui.Colors.FONT_GRAY
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

@ExperimentalMaterialApi
@Composable
fun MemebersScreen(viewModel: MainViewModel, goToCalendarActivity: () -> Unit) {
    val members = viewModel.members
    val revealedCardIds = viewModel.revealedCardIdsList.collectAsState()
    val scope = rememberCoroutineScope()
    val modalBottomSheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden
    )
    viewModel.getMyMembers()
    Column(modifier = Modifier.fillMaxSize()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
                .padding(start = 16.dp, end = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(text = "나의 회원", style = MaterialTheme.typography.subtitle2, color = FONT_GRAY)
            Text(text = "13 명", style = MaterialTheme.typography.subtitle2, color = BL_USE)
            Spacer(modifier = Modifier.weight(1F))
            Row(modifier = Modifier.clickable(onClick = { viewModel.setBottomSheetState(true) })) {
                Text(text = "사용중")
                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.ic_delete),
                    contentDescription = "사용중"
                )
            }
        }
        //lazycolum
        LazyColumn(contentPadding = PaddingValues(start = 16.dp, end = 16.dp)) {
//            items(dummyMemebers) { item: Member ->
//                MemberItem(member = item)
//            }
            members.value?.let {
                itemsIndexed(it) { index, item ->
                    Box(Modifier.fillMaxWidth(), contentAlignment = Alignment.CenterEnd) {
                        Row(
                            Modifier
                                .padding(4.dp)
                                .height(86.dp)
                                .background(Color.Red)
                        ) {
                            Text(
                                text = "삭제",
                                Modifier
                                    .height(72.dp)
                                    .fillMaxWidth()
                                    .padding(23.dp)
                                    .clip(RoundedCornerShape(10.dp)), textAlign = TextAlign.Center,
                                color = Color.White
                            )
                        }
                        DraggableItem(
                            member = item,
                            isRevealed = revealedCardIds.value.contains(index),
                            cardOffset = 100F, // 스와이프 될 너비
                            onExpand = { viewModel.onItemExpanded(index) },
                            onCollapse = { viewModel.onItemCollapsed(index) },
                            onClick = {
//                            scope.launch { modalBottomSheetState.show() }
                                // 달력 화면으로 이동하기
                                goToCalendarActivity()
                            }
                        )
                    }
                }
            }
        }
    }
    ModalBottomSheet(modalBottomSheetState = modalBottomSheetState, member = Member("", 0))
}

@Composable
@ExperimentalMaterialApi
fun ModalBottomSheet(modalBottomSheetState: ModalBottomSheetState, member: Member) {
    val scope = rememberCoroutineScope()
    ModalBottomSheetLayout(sheetState = modalBottomSheetState, sheetContent = {
        Column(
            Modifier
                .fillMaxWidth()
                .height(200.dp)
        ) {
            Row(
                Modifier
                    .fillMaxWidth()
                    .height(47.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "회원 구분")
                Spacer(modifier = Modifier.weight(1F))
                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.ic_delete),
                    contentDescription = "취소",
                    modifier = Modifier.clickable(onClick = {
                        scope.launch {
                            modalBottomSheetState.hide()
                        }
                    })
                )
            }

            Image(
                imageVector = ImageVector.vectorResource(id = R.drawable.ic_onboarding),
                contentDescription = "회원사진",
                modifier = Modifier
                    .width(100.dp)
                    .height(100.dp)
            )
            Text(text = member.nickname ?: "알 수 없음.")

        }
    }) {}
}

/**
 * 아이템을 드래그 해서 삭제할 수 있음
 */
@SuppressLint("UnusedTransitionTargetStateParameter")
@Composable
fun DraggableItem(
    member: Member,
    isRevealed: Boolean,
    cardOffset: Float,
    onExpand: () -> Unit,
    onCollapse: () -> Unit,
    onClick: () -> Unit
) {
    val offsetX = remember { mutableStateOf(0f) }
    val transitionState = remember {
        MutableTransitionState(isRevealed).apply {
            targetState = !isRevealed
        }
    }
    val transition = updateTransition(transitionState, label = "")
    val offsetTransition by transition.animateFloat(
        label = "cardOffsetTransition",
//        transitionSpec = { tween(durationMillis = ANIMATION_DURATION) },
        transitionSpec = { tween(durationMillis = 1000) },
        targetValueByState = { if (isRevealed) -cardOffset else 0F },
    )

    Card(
        modifier = Modifier
            .offset { IntOffset((offsetX.value + offsetTransition).roundToInt(), 0) }
            .pointerInput(Unit) {
                detectHorizontalDragGestures { change, dragAmount ->
                    val original = Offset(offsetX.value, 0f)
                    val summed = original + Offset(x = dragAmount, y = 0f)
                    val newValue = Offset(x = summed.x.coerceIn(0f, cardOffset), y = 0f)
                    if (newValue.x >= 10) {
                        onCollapse()
                        return@detectHorizontalDragGestures
                    } else if (newValue.x <= 0) {
                        onExpand()
                        return@detectHorizontalDragGestures
                    }
                    change.consumePositionChange()
                    offsetX.value = newValue.x
                }
            },
        content = { MemberItem(member, onClick) }
    )
}

@Composable
fun MemberItem(
    member: Member,
    onClick: () -> Unit,
    rightEndComponent: @Composable () -> Unit = {}
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(86.dp)
            .clickable {
                onClick()
            },
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Image(
            imageVector = ImageVector.vectorResource(id = member.profileImage),
            contentDescription = "프로필사진",
            Modifier.size(54.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(text = member.nickname, style = MaterialTheme.typography.subtitle2, color = BL_USE)
        Spacer(modifier = Modifier.weight(1F))
        rightEndComponent()
    }
}


/**
 * TODO
 * 나중에 친구 목록 api 만들어지면 그때 model파일에 클래스 만들것. 현재는 ui데모 위한 임시객체
 */
data class Member(val nickname: String, val profileImage: Int = R.drawable.ic_launcher_background)