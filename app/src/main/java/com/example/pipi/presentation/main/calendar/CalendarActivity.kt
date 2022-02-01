package com.example.pipi.presentation.main.calendar

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.pipi.presentation.main.SelectTrainingTypeScreen
import com.example.pipi.presentation.main.calendar.makeschedule.MakeNewExerciseScreen
import com.example.pipi.presentation.main.calendar.makeschedule.ScheduleViewModel
import com.example.pipi.presentation.main.ui.theme.PipiTheme
import org.koin.androidx.viewmodel.ext.android.viewModel

@ExperimentalMaterialApi
class CalendarActivity : ComponentActivity() {

    val viewModel: CalendarViewModel by viewModel()

    @RequiresApi(Build.VERSION_CODES.O)
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


    /**
     * 1/23 TODO : calendar를 넘기기 위해 Json으로 변환하는 로직 필요. 안됨.. 일단 text로 넘기는것으로.
     * https://stackoverflow.com/questions/67121433/how-to-pass-object-in-navigation-in-jetpack-compose
     */
    @RequiresApi(Build.VERSION_CODES.O)
    @ExperimentalFoundationApi
    @Composable
    private fun calendarNavHost(navController: NavHostController) {
        val state = rememberModalBottomSheetState(
            initialValue = ModalBottomSheetValue.Hidden
        )
        NavHost(
            navController = navController,
            startDestination = ScheduleRoute.CALENDAR
        ) {
            composable(route = ScheduleRoute.CALENDAR) {
                CalendarScreen(
                    viewModel,
                    state,
                    onCalendarItemClicked = {
                        onCalendarItemClicked(navController)
                    }
                )
            }
            composable(ScheduleRoute.SCHEDULE) {
                ScheduleDetailScreen(
                    viewModel.calendar,
                    navigate = { navController.navigate(ScheduleRoute.MAKE_NEW_SCHEDULE) },
                    goBack = { navController.goBack() }
                )
            }
            composable(ScheduleRoute.SELECT_TRAINING_TYPE) {
                SelectTrainingTypeScreen(
                    goBack = { navController.navigateUp() },
                    navigate = { navController.navigate(ScheduleRoute.MAKE_NEW_SCHEDULE) },
                    calendar = viewModel.calendar
                )
            }
            composable(ScheduleRoute.MAKE_NEW_SCHEDULE) {
                MakeNewExerciseScreen(
                    goBack = { navController.navigateUp() },
                    calendar = viewModel.calendar,
                    modalBottomSheetState=state
                )
            }
        }
    }

    fun NavController.goBack() {
        this.navigateUp()
    }

    /**
     * 클릭 이벤트는 이후 디자인 나오면 적용할것.
     */
    private fun onCalendarItemClicked(navController: NavController) {
        navController.navigate(ScheduleRoute.SCHEDULE)
    }
}