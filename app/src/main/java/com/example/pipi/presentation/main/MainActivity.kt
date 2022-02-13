package com.example.pipi.presentation.main

import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.pipi.R
import com.example.pipi.global.constants.ui.Components.DefaultTopAppbar
import com.example.pipi.global.constants.ui.setProjectTheme
import com.example.pipi.presentation.main.schedule.CalendarActivity
import com.example.pipi.presentation.main.ui.UserInfoScreen
import com.example.pipi.presentation.main.ui.member.Member
import com.example.pipi.presentation.main.ui.member.MemberRequestScreen
import com.example.pipi.presentation.main.ui.member.MembersScreen
import com.google.accompanist.pager.ExperimentalPagerApi
import kotlinx.coroutines.InternalCoroutinesApi
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

//https://levelup.gitconnected.com/implement-android-tablayout-in-jetpack-compose-e61c113add79
class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModel()

    @InternalCoroutinesApi
    @ExperimentalPagerApi
    @ExperimentalMaterialApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            setProjectTheme {
                val navController = rememberNavController()
                val scaffoldState = rememberScaffoldState()
                Scaffold(scaffoldState = scaffoldState) {
                    MainScreen(
                        viewModel,
                        navController,
                        { member -> goToCalendarActivity(member) },
                        member = Member("계정 본인", R.drawable.ic_launcher_foreground),
                        scaffoldState
                    )
                }
            }
        }
    }

    @ExperimentalMaterialApi
    private fun goToCalendarActivity(member: Member) {
        val intent = Intent(this, CalendarActivity::class.java)
        intent.putExtra("member", member)
        startActivity(intent)
    }
}


@InternalCoroutinesApi
@ExperimentalMaterialApi
@ExperimentalPagerApi
@Composable
fun MainScreen(
    viewModel: MainViewModel,
    navController: NavHostController,
    goToCalendarActivity: (Member) -> Unit,
    member: Member,
    state: ScaffoldState
) {
    val coroutineScope = rememberCoroutineScope()
    val modalBottomSheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden
    )
    val snackbarHostState = remember { SnackbarHostState() }
    NavHost(navController = navController, startDestination = "membersScreen") {
        composable("membersScreen") {
            MembersScreen(
                modalBottomSheetState,
                coroutineScope,
                goToCalendarActivity = { member -> goToCalendarActivity(member) },
                showMemberRequestScreen = {
                    navController.navigate("memberRequestScreen") {
                        popUpTo(
                            "memebersScreen"
                        )
                    }
                },
                myInfo = member
            )
        }
        composable("memberRequestScreen") {
            MemberRequestScreen(state) { navController.navigateUp() }
        }
        /**
         * TODO : 파라미터 넘기는거 함수를 통해서 넘길지, 아니면 navigation의 argument로 넘기는게 나을지
         * 고민해 볼 것.
         */
        composable("userInfoScreen") {
            UserInfoScreen(
                viewModel = viewModel,
                goBack = { navController.navigateUp() },
                goToMakeScheduleActivity = { member -> goToCalendarActivity(member) }
            )
        }
    }

}

@Composable
fun DrawMainTopAppBar(showMemberRequestScreen: () -> Unit) {
    DefaultTopAppbar(
        title = {
            Text(
                text = stringResource(R.string.member_screen_topappbar_title),
                style = MaterialTheme.typography.h1,
                textAlign = TextAlign.Start,
                modifier = Modifier.fillMaxWidth()
            )
        },
        navComponent = {},
        optionComponent = {
            Row() {
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
            }
        })
}