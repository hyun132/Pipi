package com.example.pipi.presentation.main

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.example.pipi.R
import com.example.pipi.global.constants.ui.Colors
import com.example.pipi.global.constants.ui.Colors.PRIMARY_BLACK

@ExperimentalMaterialApi
@Composable
fun MemeberRequestScreen(viewModel: MainViewModel) {
    val requests = viewModel.memberRequest
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
                .padding(start = 16.dp, end = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = "친구 요청",
                style = MaterialTheme.typography.subtitle2,
                color = Colors.FONT_GRAY
            )
            Text(text = "13 명", style = MaterialTheme.typography.subtitle2, color = PRIMARY_BLACK)
            Spacer(modifier = Modifier.weight(1F))
            Row(modifier = Modifier.clickable(onClick = { viewModel.setBottomSheetState(true) })) {
                Text(text = "사용중")
                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.ic_delete),
                    contentDescription = "사용중"
                )
            }
        }
        //lazycolum
        LazyColumn(contentPadding = PaddingValues(start = 16.dp, end = 16.dp)) {
            requests.value?.let {
                itemsIndexed(it) { index, item ->
                    Box(Modifier.fillMaxWidth(), contentAlignment = Alignment.CenterEnd) {
                        MemberItem(member = item,onClick = {/*TODO*/}) {
                            Row(horizontalArrangement = Arrangement.End) {
                                Button(
                                    onClick = { /*수락*/ },
                                    modifier = Modifier.height(40.dp).width(62.dp).clip(
                                        RoundedCornerShape(8.dp))
                                ) {
                                    Text(text = "수락", color = Color.White)
                                }
                                Spacer(Modifier.width(8.dp))
                                Button(
                                    onClick = { /*거절*/ },
                                    modifier = Modifier.height(40.dp).width(62.dp).clip(
                                        RoundedCornerShape(8.dp))
                                ) {
                                    Text(text = "거절")
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}