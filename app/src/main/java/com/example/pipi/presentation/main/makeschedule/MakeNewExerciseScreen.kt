package com.example.pipi.presentation.main.makeschedule

import android.annotation.SuppressLint
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.pipi.R
import com.example.pipi.global.constants.ui.Colors.BRAND_SECOND
import com.example.pipi.global.constants.ui.Components.DefaultTopAppbar
import com.example.pipi.global.constants.utils.CalendarUtils
import java.util.*

@SuppressLint("UnrememberedMutableState")
@Composable
fun MakeNewExerciseScreen(navController: NavController, calendar: Calendar) {
    val selectedParts = mutableStateOf(FilterData("1", "가슴"))
    val currentDay =

    Column(Modifier.fillMaxWidth()) {
        DefaultTopAppbar(navComponent = {
            Icon(
                imageVector = ImageVector.vectorResource(id = R.drawable.ic_back),
                contentDescription = "뒤로가기"
            )
        }, title = {
            Row(
                Modifier
                    .fillMaxWidth()
                    .align(CenterHorizontally),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.ic_back),
                    contentDescription = "어제"
                )
                Text(
                    text = "년  일",
                    style = MaterialTheme.typography.subtitle2
                )
                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.ic_back),
                    contentDescription = "내일"
                )
            }
        })


    }

}

enum class ButtonClickState {
    CLICKED, NOT_CLICKED
}

@Composable
//버튼을 클릭하면 외부의 선택된 아이템을 이 아이템으로 변경
//선택된 아이템과 같은 아이템인 경우 색상을 변경하도록 함.
fun DrawFilteringButton(filterData: FilterData, onClick: () -> Unit) {
    OutlinedButton(onClick = {
        onClick()
        /**
         * 클릭하면 observe하고 있던 상태를 변경해야함 (선택된 아이템)
         */
    }, Modifier.border(1.dp, color = BRAND_SECOND)) {
        Text(text = filterData.name)
    }
}

@ExperimentalFoundationApi
@Composable
fun DrawFilterLayout(filter: Filter) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(text = filter.title, style = MaterialTheme.typography.body2)
        LazyVerticalGrid(cells = GridCells.Fixed(4)) {
            items(filter.dataList) { filteritem ->
                DrawFilteringButton(filterData = filteritem, onClick = {})
            }
        }
    }
}


/**
 * TODO :
 * api 나오면 entity, dto, model클래스 새로 정의할 것.
 */


data class Filter(val title: String = "부위", val dataList: List<FilterData>)


data class FilterData(val id: String, val name: String)

val partsData = listOf<FilterData>(
    FilterData("1", "가슴"),
    FilterData("2", "등"),
    FilterData("3", "어깨"),
    FilterData("4", "하체"),
    FilterData("5", "이두박근"),
    FilterData("6", "삼두박근"),
    FilterData("7", "복근"),
    FilterData("8", "유산소")
)

val parts = Filter("부위", partsData)