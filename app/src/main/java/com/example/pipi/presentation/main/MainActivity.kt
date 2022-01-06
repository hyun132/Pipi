package com.example.pipi.presentation.main

import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
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
    val coroutineScope = rememberCoroutineScope()

    Column(Modifier.fillMaxSize()) {
        NavHost(navController = navController, startDestination = "membersScreen") {
            composable("membersScreen") {
                MembersScreen(
                    viewModel = viewModel,
                    goToCalendarActivity = goToCalendarActivity,
                    showMemberRequestScreen = {
                        navController.navigate("memberRequestScreen") {
                            popUpTo(
                                "memebersScreen"
                            )
                        }
                    }
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

@Composable
fun DrawMainTopAppBar(showMemberRequestScreen: () -> Unit) {
    DefaultTopAppbar(
        title = {
            Text(
                text = "회원관리",
                style = MaterialTheme.typography.h1,
                fontSize = 24.sp,
                textAlign = TextAlign.Start,
                modifier = Modifier.fillMaxWidth()
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