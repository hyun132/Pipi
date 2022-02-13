package com.example.pipi.presentation.main

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.example.pipi.R
import com.example.pipi.global.constants.ui.Colors.QUATERNARY_BRAND
import com.example.pipi.global.constants.ui.Colors.TERTIARY_BRAND
import com.example.pipi.global.constants.ui.Components.DefaultTopAppbar
import com.example.pipi.global.constants.utils.CalendarUtils.getCurrentDateString
import java.util.*

@Composable
fun SelectTrainingTypeScreen(navigate: () -> Unit, goBack: () -> Unit, calendar: Calendar) {

    Scaffold(topBar = { DrawTopAppbar(goBack = goBack, calendar = calendar) }) {
        Column(Modifier.fillMaxSize()) {
            DrawTrainingTypeItem(
                title = stringResource(R.string.training_type_item_title_pt),
                description = stringResource(R.string.training_type_pt_description),
                QUATERNARY_BRAND,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp)
                    .clickable {
                        navigate()
                    }
            )
            DrawTrainingTypeItem(
                title = stringResource(R.string.training_type_item_title_at),
                description = stringResource(R.string.training_type_at_description),
                TERTIARY_BRAND,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp)
                    .clickable {
                        navigate()
                    }
            )
        }
    }
}

@Composable
fun DrawTopAppbar(goBack: () -> Unit, calendar: Calendar) {
    DefaultTopAppbar(navComponent = {
        Icon(
            imageVector = ImageVector.vectorResource(id = R.drawable.ic_back),
            contentDescription = "뒤로가기",
            modifier = Modifier.clickable { goBack() }
        )
    }, title = {
        Row(
            Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = calendar.getCurrentDateString(),
                style = MaterialTheme.typography.h5
            )
        }
    })
}

@Composable
fun DrawTrainingTypeItem(
    title: String,
    description: String,
    titleColor: Color,
    modifier: Modifier
) {
    Card(modifier = modifier) {
        Column(
            Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(text = title, color = titleColor)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = description)
        }
    }
}