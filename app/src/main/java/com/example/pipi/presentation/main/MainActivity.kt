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
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.pipi.R
import com.example.pipi.global.constants.ui.Components.DefaultTopAppbar
import com.example.pipi.presentation.main.calendar.CalendarActivity
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.PagerState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

//https://levelup.gitconnected.com/implement-android-tablayout-in-jetpack-compose-e61c113add79
class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModel()

    @ExperimentalPagerApi
    @ExperimentalMaterialApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            MainScreen(viewModel, navController, ::goToCalendarActivity)
        }
    }

    private fun goToCalendarActivity() {
        startActivity(Intent(this, CalendarActivity::class.java))
    }
}

@ExperimentalMaterialApi
@ExperimentalPagerApi
@Composable
fun MainScreen(
    viewModel: MainViewModel,
    navController: NavHostController,
    goToCalendarActivity: () -> Unit
) {
    val bottomSheetScaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = BottomSheetState(BottomSheetValue.Collapsed)
    )
    val isBottomSheetExpanded by viewModel.isBottomSheetExpanded.observeAsState(false)
    val coroutineScope = rememberCoroutineScope()
    BottomSheetScaffold(
        scaffoldState = bottomSheetScaffoldState,
        sheetContent = { BottomSheetsContents(coroutineScope) { viewModel.hideBottomSheet() } },
        sheetPeekHeight = 0.dp
    )
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
        Column(Modifier.fillMaxSize()) {
            NavHost(navController = navController, startDestination = "membersScreen") {
                composable("membersScreen") {
                    MembersScreen(
                        viewModel = viewModel,
                        goToCalendarActivity = goToCalendarActivity,
                        { navController.navigate("memberRequestScreen") { popUpTo("memebersScreen") } }
                    )
                }
                composable("memberRequestScreen") {
                    MemberRequestScreen(
                        viewModel = viewModel
                    ) { navController.navigateUp() }
                }
            }
        }
    }
}

@Composable
fun DrawMainTopAppBar(showMemberRequestScreen: () -> Unit) {
    DefaultTopAppbar(
        title = {
            Text(
                text = "회원관리",
                style = MaterialTheme.typography.h1,
                fontSize = 24.sp
            )
        },
        optionComponent = {
            Icon(
                imageVector = ImageVector.vectorResource(id = R.drawable.ic_show),
                contentDescription = "회원추가",
                modifier = Modifier
                    .size(22.dp)
                    .clickable { showMemberRequestScreen() }
            )
            Icon(
                imageVector = ImageVector.vectorResource(id = R.drawable.ic_show),
                contentDescription = "회원추가",
                modifier = Modifier
                    .size(22.dp)
                    /**
                     * TODO : 회원정보 화면 만들어서 보여주기
                     */
                    .clickable { Timber.d("회원정보 화면 보여주기.") }
            )
        })
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

@Composable
fun BottomSheetsContents(coroutineScope: CoroutineScope, hideBottomSheet: () -> Unit) {
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
                        hideBottomSheet()
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
}