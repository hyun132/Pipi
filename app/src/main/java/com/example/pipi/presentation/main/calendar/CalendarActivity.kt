package com.example.pipi.presentation.main.calendar

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.pipi.presentation.main.ui.theme.PipiTheme
import org.koin.androidx.viewmodel.ext.android.viewModel

class CalendarActivity : ComponentActivity() {

    val viewModel: CalendarViewModel by viewModel()

    @ExperimentalFoundationApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PipiTheme {
                val navController = rememberNavController()
                Surface(color = MaterialTheme.colors.background) {
                    calendarNavHost(navController)
                }
            }
        }

    }

    @ExperimentalFoundationApi
    @Composable
    private fun calendarNavHost(navController: NavHostController) {
        NavHost(
            navController = navController,
            startDestination = CalendarScreenType.Calendar.name
        ) {
            composable(route = CalendarScreenType.Calendar.name) {
                CalendarScreen(
                    viewModel,
                    onCalendarItemClicked = { date ->
                        onCalendarItemClicked(
                            navController,
                            date
                        )
                    })
            }
            composable(
                route = "${CalendarScreenType.Detail.name}/{date}",
                arguments = listOf(navArgument("date") { type = NavType.StringType })
            ) { entry ->
                /**
                 * 캘린더에서 클릭한 날짜로 데이터 요청? 해당날짜 데이터 그냥 전달?
                 */
                val dateString = entry.arguments?.getString("date") ?: "null"
                ScheduleDetailScreen(dateString, navController, viewModel)
            }
        }
    }

    /**
     * 클릭 이벤트는 이후 디자인 나오면 적용할것.
     */
    private fun onCalendarItemClicked(navController: NavController, date: String) {
        navController.navigate("${CalendarScreenType.Detail.name}/${date}")
    }
}