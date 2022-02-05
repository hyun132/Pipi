package com.example.pipi.presentation.main.ui.member

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
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.consumePositionChange
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pipi.R
import com.example.pipi.global.constants.ui.Colors
import com.example.pipi.global.constants.ui.Colors.BRAND_SECOND
import com.example.pipi.global.constants.ui.Colors.FONT_GRAY
import com.example.pipi.global.constants.ui.Colors.PRIMARY_TEXT
import com.example.pipi.global.constants.ui.Colors.SECONDARY_TEXT_GHOST
import com.example.pipi.global.constants.ui.Colors.SIDE_BAR_BACKGROUND
import com.example.pipi.global.constants.ui.Colors.WHITE
import com.example.pipi.presentation.main.DrawMainTopAppBar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.androidx.compose.viewModel
import java.io.Serializable
import kotlin.math.roundToInt

@InternalCoroutinesApi
@ExperimentalMaterialApi
@Composable
fun MembersScreen(
    modalBottomSheetState: ModalBottomSheetState,
    scope: CoroutineScope,
    goToCalendarActivity: (Member) -> Unit,
    showMemberRequestScreen: () -> Unit,
    myInfo: Member
) {
    val viewModel: MemberViewModel by viewModel()
    val state = viewModel.memberState
    val searchQuery = viewModel.searchQuery
    val revealedCardIds = viewModel.revealedCardIdsList.collectAsState()

    val modalContents: MutableState<@Composable (() -> Unit) -> Unit> =
        remember {
            mutableStateOf({ toggle -> MemberFilterModalContent(toggle) })
        }

    LaunchedEffect(key1 = true) {
        viewModel.getMyMembers()
        viewModel.uiEvnet.collect { event ->
            when (event) {
                else -> Unit
            }
        }
    }

    /**
     * scroll 어떻게 해야 할 지 생각해봐야함.
     */

    Column(
        Modifier
            .fillMaxSize()
            .height(69.dp)
    ) {
        DrawMainTopAppBar { showMemberRequestScreen() }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            DrawSearchBar(
                placeholder = "회원을 검색해 주세요.",
                onChange = { query: String -> searchQuery.value = query },
                query = searchQuery
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(text = "나의 프로필", style = MaterialTheme.typography.subtitle2, color = FONT_GRAY)
                Spacer(modifier = Modifier.weight(1F))
                Row(modifier = Modifier.clickable(onClick = {
                    modalContents.value =
                        { toggle ->
                            MemberFilterModalContent(
                                toggle
                            ).run { toggle() }
                        }

                })) {
                    Text(text = "전체 회원")
                    Icon(
                        imageVector = ImageVector.vectorResource(id = R.drawable.ic_list_setting),
                        contentDescription = "사용중",
                        tint = Color.Unspecified
                    )
                }
            }
            MemberItem(member = myInfo, onClick = {
                // 달력 화면으로 이동하기
//                              goToCalendarActivity()
                modalContents.value =
                    { toggle ->
                        MyInfoModalContent(
                            toggle,
                            myInfo,
                            { member -> goToCalendarActivity(member) }
                        ).run { toggle() }
                    }
            })
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(text = "나의 회원", style = MaterialTheme.typography.subtitle2, color = FONT_GRAY)
                Text(
                    text = "13 명",
                    style = MaterialTheme.typography.subtitle2,
                    color = Colors.PRIMARY_TEXT
                )
            }
            LazyColumn(
                Modifier
                    .fillMaxWidth()
            ) {
                itemsIndexed(state.memberList) { index, item ->
                    Box(Modifier.fillMaxWidth(), contentAlignment = Alignment.CenterEnd) {
                        Row(
                            Modifier
                                .padding(4.dp)
                                .height(68.dp)
                                .background(Color.Red)
                        ) {
                            Text(
                                text = "삭제",
                                Modifier
                                    .height(60.dp)
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(10.dp)),
                                textAlign = TextAlign.End,
                                color = Color.White
                            )
                        }
                        DraggableItem(
                            member = item,
                            isRevealed = revealedCardIds.value.contains(index),
                            cardOffset = 200F, // 스와이프 될 너비
                            onExpand = { viewModel.onItemExpanded(index) },
                            onCollapse = { viewModel.onItemCollapsed(index) },
                            onClick = {
                                // 달력 화면으로 이동하기
//                              goToCalendarActivity()
                                modalContents.value =
                                    { toggle ->
                                        MyInfoModalContent(
                                            toggle,
                                            item
                                        ) { member -> goToCalendarActivity(member) }.run { toggle() }
                                    }
                            }
                        )
                    }
                }
            }
            if (state.memberList.isEmpty()) Box(
                Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "검색된 회원이 없네요!\n회원을 추가해 주세요!",
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.subtitle2,
                    color = SECONDARY_TEXT_GHOST
                )
            }

        }

    }
    ModalBottomSheet(modalBottomSheetState, scope, modalContents.value)
}

enum class MemberModalType {
    FILTER,
    USER_INFO
}

@Composable
fun DrawSearchBar(
    placeholder: String = "",
    onChange: (String) -> Unit,
    query: MutableState<String>
) {
    BasicTextField(
        value = query.value,
        onValueChange = { textFieldValue ->
            query.value = textFieldValue
            onChange(query.value)
        },
        textStyle = MaterialTheme.typography.body2,
        modifier = Modifier
            .height(30.dp)
            .clip(RoundedCornerShape(4.dp))
            .fillMaxWidth()
            .height(30.dp)
            .background(SIDE_BAR_BACKGROUND),
        decorationBox = { innerTextField ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(start = 12.dp, end = 12.dp)
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.ic_search),
                    contentDescription = "검색",
                    Modifier.size(14.dp)
                )
                Spacer(modifier = Modifier.width(12.dp))
                Box() {
                    innerTextField()
                    // 여기 innerTextField앞에 가리던지 기본값을 0으로 하던지
                    if (query.value.isEmpty()) Text(
                        text = placeholder,
                        style = MaterialTheme.typography.subtitle1,
                        fontSize = 12.sp,
                        color = SECONDARY_TEXT_GHOST
                    )
                }
            }
//            TextFieldDefaults.textFieldColors(
//                textColor = PRIMARY_TEXT,
//                disabledTextColor = Color.Transparent,
//                backgroundColor = SIDE_BAR_BACKGROUND,
//                focusedIndicatorColor = Color.Transparent,
//                unfocusedIndicatorColor = Color.Transparent,
//                disabledIndicatorColor = Color.Transparent
//            )
        }
    )
}

@ExperimentalMaterialApi
@Composable
fun MyInfoModalContent(
    toggle: () -> Unit,
    member: Member,
    goToCalendarActivity: (Member) -> Unit
) {
    Column(
        Modifier
            .height(224.dp)
            .fillMaxWidth()
            .defaultMinSize(minHeight = 1.dp)
    ) {
        Row(
            Modifier
                .fillMaxWidth()
                .height(47.dp)
                .padding(start = 16.dp, end = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "${member.nickname} 회원님", style = MaterialTheme.typography.subtitle2)
            Spacer(modifier = Modifier.weight(1F))
            Icon(
                imageVector = ImageVector.vectorResource(id = R.drawable.ic_delete),
                contentDescription = "취소",
                modifier = Modifier.clickable(onClick = {
                    toggle()
                }),
                tint = Color.Unspecified
            )
        }
        Row(modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .drawBehind {
                val strokeWidth = 1f
                drawLine(
                    SIDE_BAR_BACKGROUND,
                    Offset(0f, 0f),
                    Offset(size.width, 0f),
                    strokeWidth
                )
            }) {
            Image(
                imageVector = ImageVector.vectorResource(id = member.profileImage),
                contentDescription = "회원 프로필",
                Modifier.clip(RoundedCornerShape(8.dp))
            )
            Spacer(modifier = Modifier.weight(1f))
            Column(
                Modifier
                    .fillMaxWidth()
            ) {
                Text(
                    text = "(회 원)", fontSize = 12.sp,
                    style = MaterialTheme.typography.body2,
                    color = PRIMARY_TEXT
                )
                Spacer(modifier = Modifier.weight(1f))
                Row(horizontalArrangement = Arrangement.End, modifier = Modifier.fillMaxWidth()) {
                    TextButton(
                        onClick = { goToCalendarActivity(member) },
                        modifier = Modifier
                            .height(26.dp)
                            .width(84.dp),
                        contentPadding = PaddingValues(0.dp)
                    ) {
                        Text(
                            text = "관리하러 가기",
                            fontSize = 12.sp,
                            style = MaterialTheme.typography.body2,
                            color = PRIMARY_TEXT
                        )
                    }
                    /**
                     * TODO : switch track size 변경 방법 찾아보기
                     */
                    Switch(
                        checked = true,
                        onCheckedChange = {},
                        modifier = Modifier
                            .width(50.dp)
                            .height(26.dp),
                        colors = SwitchDefaults.colors(
                            checkedTrackColor = BRAND_SECOND,
                            uncheckedTrackColor = SECONDARY_TEXT_GHOST,
                            checkedThumbColor = WHITE,
                            uncheckedThumbColor = WHITE
                        )
                    )
                }
            }
        }
    }
}


@ExperimentalMaterialApi
@Composable
fun MemberFilterModalContent(toggle: () -> Unit) {
    Column(
        Modifier
            .height(252.dp)
            .padding(start = 16.dp, end = 16.dp)
            .fillMaxWidth()
            .defaultMinSize(minHeight = 1.dp)
    ) {
        Row(
            Modifier
                .fillMaxWidth()
                .height(47.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "회원 유형", style = MaterialTheme.typography.subtitle2)
            Spacer(modifier = Modifier.weight(1F))
            Icon(
                imageVector = ImageVector.vectorResource(id = R.drawable.ic_delete),
                contentDescription = "취소",
                modifier = Modifier.clickable(onClick = {
                    toggle()
                }),
                tint = Color.Unspecified
            )
        }
        /**
         * TODO : 회원 유형도 타입 나오면 Enum이나 Sealed로 바꾸기
         */
        Row(
            Modifier
                .fillMaxWidth()
                .height(60.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "전 체",
                style = MaterialTheme.typography.subtitle2,
                modifier = Modifier.width(80.dp),
                textAlign = TextAlign.Justify
            )
            Text(text = "회원권이 있는 회원", style = MaterialTheme.typography.body2)
        }
        Row(
            Modifier
                .fillMaxWidth()
                .height(60.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "수강중 회원",
                style = MaterialTheme.typography.subtitle2,
                modifier = Modifier.width(80.dp),
                textAlign = TextAlign.Justify
            )
            Text(text = "회원권이 있는 회원", style = MaterialTheme.typography.body2)
        }
        Row(
            Modifier
                .fillMaxWidth()
                .height(60.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "만료된 회원",
                style = MaterialTheme.typography.subtitle2,
                modifier = Modifier.width(80.dp),
                textAlign = TextAlign.Justify
            )
            Text(text = "회원권이 없거나 만료된 회원", style = MaterialTheme.typography.body2)
        }
    }
}


/**
 * TODO : 얘한테 어떻게 데이터 넘겨줄건지 고민해봐야함.
 */
@Composable
@ExperimentalMaterialApi
fun ModalBottomSheet(
    modalBottomSheetState: ModalBottomSheetState,
    scope: CoroutineScope,
    modalContents: @Composable (() -> Unit) -> Unit
) {
    val toggle: () -> Unit = {
        scope.launch {
            if (modalBottomSheetState.isVisible) modalBottomSheetState.hide()
            else modalBottomSheetState.show()
        }
    }
    ModalBottomSheetLayout(
        sheetState = modalBottomSheetState,
        sheetContent = { modalContents(toggle) }
    ) {
    }

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
        transitionSpec = { tween(durationMillis = 600) },
        targetValueByState = { if (isRevealed) -cardOffset else 0F },
    )

    Box(
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
            }
            .background(Color.White),
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
            .height(68.dp)
            .clickable {
                onClick()
            },
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Image(
            imageVector = ImageVector.vectorResource(id = member.profileImage),
            contentDescription = "프로필사진",
            Modifier
                .size(54.dp)
                .clip(RoundedCornerShape(16.dp))
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = member.nickname,
            style = MaterialTheme.typography.subtitle2,
            color = Colors.PRIMARY_TEXT,
            fontSize = 14.sp
        )
        Spacer(modifier = Modifier.weight(1F))
        rightEndComponent()
    }
}


/**
 * TODO
 * 나중에 친구 목록 api 만들어지면 그때 model파일에 클래스 만들것. 현재는 ui데모 위한 임시객체
 */
data class Member(
    val nickname: String,
    val profileImage: Int = R.drawable.ic_launcher_background,
    val inbody: Int? = null
) : Serializable

enum class BottomSheetType {
    FILTER, MY_PROFILE, USER_PROFILE
}

data class ModalStateHandler @ExperimentalMaterialApi constructor(
    var modalBottomSheetState: ModalBottomSheetState,
    var bottomSheetType: BottomSheetType,
    var bottomSheetContent: @Composable () -> Unit
)