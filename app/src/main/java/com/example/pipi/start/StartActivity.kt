package com.example.pipi.start

import android.os.Bundle
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pipi.R

class StartActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Column(modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()) {
                Spacer(modifier = Modifier.height(16.dp))
                drawOnBoardingImage()
                drawOnBoardingText("모두의 건강을 보다 ", "철저하게")
                drawOnBoardingText("모두의 건강을 보다 ", "섬세하게")
                drawOnBoardingText("모두의 건강을 보다 ", "쉽게")
                Spacer(modifier = Modifier.height(20.dp))
                drawDefaultButton("트레이너로 시작하기") { moveMainActivity() }
                drawDefaultButton("일반회원으로 시작하기") { moveMainActivity() }
            }
        }
    }

    private fun moveMainActivity() {
        //do something
        Toast.makeText(this, "버튼 클릭", Toast.LENGTH_SHORT).show()
    }
}

@Composable
fun drawOnBoardingImage() {
    Image(painterResource(R.drawable.onboarding), contentDescription = "onbarding Image",
        Modifier
            .width(360.dp)
            .height(360.dp))
}

@Composable
fun drawOnBoardingText(plainText: String, boldText: String) {
    Row (modifier = Modifier.fillMaxWidth(),horizontalArrangement = Arrangement.Center){
        Text(text = plainText)
        Text(text = boldText, fontWeight = FontWeight.Bold)
    }
}

@Composable
fun drawDefaultButton(text: String, onClick: () -> Unit) {
    Button(
        modifier = Modifier
            .clip(RoundedCornerShape(4.dp))
            .fillMaxWidth(),
        onClick = onClick,
        colors = ButtonDefaults.textButtonColors(backgroundColor = Color(R.color.main_purple))
    ) {
        Text(fontSize = 16.sp, text = text)
    }
}

@Preview
@Composable
fun DefaultPreview(){
    Spacer(modifier = Modifier.height(16.dp))
    Column(modifier = Modifier
        .padding(16.dp)
        .fillMaxWidth()
    ,horizontalAlignment = Alignment.CenterHorizontally) {
        drawOnBoardingImage()
        drawOnBoardingText("모두의 건강을 보다 ", "철저하게")
        drawOnBoardingText("모두의 건강을 보다 ", "섬세하게")
        drawOnBoardingText("모두의 건강을 보다 ", "쉽게")
    }
    Spacer(modifier = Modifier.height(20.dp))
    drawDefaultButton("트레이너로 시작하기") {  }
    drawDefaultButton("일반회원으로 시작하기") {  }
}