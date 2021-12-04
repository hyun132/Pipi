package com.example.pipi.presentation.main

import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pipi.R
import com.example.pipi.global.constants.ui.Components.DefaultTopAppbar
import com.example.pipi.global.constants.ui.Components.drawTextTitleTopAppbar
import com.example.pipi.presentation.main.calendar.CalendarActivity
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

//https://levelup.gitconnected.com/implement-android-tablayout-in-jetpack-compose-e61c113add79
class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModel()

    @ExperimentalPagerApi
    @ExperimentalMaterialApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainScreen(viewModel, ::goToCalendarActivity)
        }
    }

    fun goToCalendarActivity() {
        startActivity(Intent(this, CalendarActivity::class.java))
    }
}

@ExperimentalMaterialApi
@ExperimentalPagerApi
@Composable
fun MainScreen(viewModel: MainViewModel, goToCalendarActivity: () -> Unit) {
    val tabs = listOf(
        "회원관리",
        "친구요청"
    )

    val pagerState = rememberPagerState(pageCount = tabs.size)

    //appbar도 통일 할 수 있도록 기존 컴포넌트 수정 필요.
    val bottomSheetScaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = BottomSheetState(BottomSheetValue.Collapsed)
    )

    val isBottomSheetExpanded by viewModel.isBottomSheetExpanded.observeAsState(false)
    val coroutineScope = rememberCoroutineScope()
    BottomSheetScaffold(
        scaffoldState = bottomSheetScaffoldState,
        sheetContent = {
            Column(Modifier.fillMaxWidth()) {
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
                            coroutineScope.launch {
                                viewModel.setBottomSheetState(
                                    false
                                )
                            }
                        })
                    )
                }
                Row(
                    Modifier
                        .fillMaxWidth()
                        .height(60.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "사용중", style = MaterialTheme.typography.subtitle2)
                    Spacer(modifier = Modifier.width(16.dp))
                    Text(text = "회원권이 있는 회원", style = MaterialTheme.typography.subtitle2)
                }
                Row(
                    Modifier
                        .fillMaxWidth()
                        .height(60.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "만료됨", style = MaterialTheme.typography.subtitle2)
                    Spacer(modifier = Modifier.width(16.dp))
                    Text(text = "회원권이 없거나 만료된 회원")
                }
            }
        },
        sheetPeekHeight = 0.dp,
        topBar = {
            DefaultTopAppbar(
                title = {
                    Text(
                        text = "회원관리",
                        style = MaterialTheme.typography.h2,
                        fontSize = 24.sp,
                    )
                },
                optionComponent = {
                    Icon(
                        imageVector = ImageVector.vectorResource(id = R.drawable.ic_show),
                        contentDescription = "회원추가",
                        modifier = Modifier
                            .size(22.dp)
                            .clickable { println("add member clicked") }
                    )
                })
        },
    )
//    Scaffold(topBar = { drawTextTitleTopAppbar("sam트레이너", {}) })
    {
        if (isBottomSheetExpanded) {
            if (bottomSheetScaffoldState.bottomSheetState.isCollapsed) {
                coroutineScope.launch {
                    bottomSheetScaffoldState.bottomSheetState.expand()
                }
            }
        } else {
            if (bottomSheetScaffoldState.bottomSheetState.isExpanded) {
                coroutineScope.launch {
                    bottomSheetScaffoldState.bottomSheetState.collapse()
                }
            }
        }
        Column(modifier = Modifier.fillMaxSize()) {
            Tabs(tabs = tabs, state = pagerState)
            HorizontalPager(state = pagerState) { page ->
                when (page) {
                    0 -> MemebersScreen(viewModel = viewModel, goToCalendarActivity)
                    1 -> MemeberRequestScreen(viewModel = viewModel)
                }
            }
        }
    }
}

@ExperimentalMaterialApi
fun expandBottomSheet(
    bottomSheetScaffoldState: BottomSheetScaffoldState,
    coroutineScope: CoroutineScope
) {
    coroutineScope.launch {
        if (bottomSheetScaffoldState.bottomSheetState.isCollapsed) {
            bottomSheetScaffoldState.bottomSheetState.expand()
        }
    }

}


@ExperimentalPagerApi
@Composable
fun Tabs(tabs: List<String>, state: PagerState) {
//    var currentTabId by remember { mutableStateOf(0) }
    val scope = rememberCoroutineScope()
    Column() {
        TabRow(selectedTabIndex = state.currentPage, backgroundColor = Color.White) {
            tabs.forEachIndexed() { index, tab ->
                Tab(
                    selected = state.currentPage == index,
                    text = {
                        Text(
                            text = tab,
                            style = MaterialTheme.typography.subtitle2,
                            fontSize = 14.sp,
                            color = MaterialTheme.colors.primary
                        )
                    },
                    onClick = {
                        scope.launch {
                            state.animateScrollToPage(index)
                        }
                    },
                )
            }
        }
//        tabs[state.currentPage].screen
    }
}