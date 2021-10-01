package com.example.pipi.start

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.pipi.R
import com.example.pipi.global.constants.Fonts.appFontFamily
import com.example.pipi.global.constants.ui.Components.drawDefaultButton
import com.example.pipi.feature_login.presentation.login.LoginActivity

class StartActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_Pipi)
        super.onCreate(savedInstanceState)
        setContent {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
            ) {
                drawOnBoardingText("모두의 건강을 보다 ", "쉽게")
                Spacer(modifier = Modifier.height(66.dp))
                drawDefaultButton(
                    colorResource(R.color.main_purple),
                    text = "트레이너로 시작하기",
                    onClick = { moveMainActivity() },
                    isEnabled = true
                )
                Spacer(modifier = Modifier.height(15.dp))
                drawDefaultButton(
                    Color.Black,
                    "일반회원으로 시작하기",
                    isEnabled = true,
                    onClick = { moveMainActivity() })
            }
        }
    }

    private fun moveMainActivity() {
        //do something
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
        Toast.makeText(this, "버튼 클릭", Toast.LENGTH_SHORT).show()
    }
}

@Composable
fun drawOnBoardingImage(modifier: Modifier) {
    Image(
        painterResource(R.drawable.onboarding), contentDescription = "onbarding Image",
        modifier
    )
}

@Composable
fun drawOnBoardingText(plainText: String, boldText: String) {
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
        Text(text = plainText, style = MaterialTheme.typography.subtitle1)
        Text(
            text = boldText,
            style = MaterialTheme.typography.subtitle2,
            fontWeight = FontWeight(700)
        )
    }
}

@Preview
@Composable
fun DefaultPreview() {
    Spacer(modifier = Modifier.height(16.dp))
    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally
    ) {
        drawOnBoardingImage(
            Modifier
                .height(168.dp)
                .width(168.dp)
                .fillMaxWidth()
        )
        drawOnBoardingText("모두의 건강을 보다 ", "철저하게")
        drawOnBoardingText("모두의 건강을 보다 ", "섬세하게")
        drawOnBoardingText("모두의 건강을 보다 ", "쉽게")
    }
    Spacer(modifier = Modifier.height(20.dp))
    drawDefaultButton(
        colorResource(R.color.main_purple),
        text = "트레이너로 시작하기",
        onClick = { },
        isEnabled = true
    )
    Spacer(modifier = Modifier.height(15.dp))
    drawDefaultButton(
        Color.Black,
        "일반회원으로 시작하기",
        isEnabled = true,
        onClick = {  })
}