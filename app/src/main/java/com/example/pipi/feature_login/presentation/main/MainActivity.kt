package com.example.pipi.feature_login.presentation.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.unit.sp
import com.example.pipi.global.constants.ui.Components.drawTextTitleTopAppbar
import com.google.android.material.tabs.TabItem

//https://levelup.gitconnected.com/implement-android-tablayout-in-jetpack-compose-e61c113add79
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainScreen()

        }
    }
}

@Composable
fun MainScreen() {
    val tabs = listOf<TabItems>(
        TabItems.Members,
        TabItems.MemberRequest
    )
    //appbar도 통일 할 수 있도록 기존 컴포넌트 수정 필요.
    Scaffold(topBar = { drawTextTitleTopAppbar("sam트레이너", {}) }) {
        Tabs(tabs = tabs)
    }
}


@Composable
fun Tabs(tabs: List<TabItems>) {
    var currentTabId by remember { mutableStateOf(0) }
    Column() {
        TabRow(selectedTabIndex = currentTabId) {
            tabs.forEachIndexed() { index, tab ->
                Tab(
                    selected = currentTabId == index,
                    text = {
                        Text(
                            text = tab.title,
                            style = MaterialTheme.typography.subtitle2,
                            fontSize = 14.sp
                        )
                    },
                    onClick = { currentTabId = index })
            }
        }
        tabs[currentTabId].screen
    }
}