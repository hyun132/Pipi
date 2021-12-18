package com.example.pipi.presentation.main.makeschedule

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.pipi.global.constants.utils.BaseCalendar
import com.example.pipi.presentation.main.SelectTrainingTypeScreen
import com.example.pipi.presentation.main.ui.theme.PipiTheme

class ScheduleActivity(val calendar: BaseCalendar) : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            PipiTheme {
                Surface(color = MaterialTheme.colors.background) {
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController, startDestination = "selectTrainingType"
                    ) {
                        composable("selectTrainingType") {
                            SelectTrainingTypeScreen(
                                navController = navController,
                                calendar = calendar
                            )
                        }
                        composable("makeNewExerciseScreen") {
                            MakeNewExerciseScreen(
                                navController = navController,
                                calendar = calendar
                            )
                        }
                    }
                }
            }
        }
    }
}