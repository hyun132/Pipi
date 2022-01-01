package com.example.pipi.data.remote

import com.example.pipi.Pipi
import com.example.pipi.global.constants.Const.BASE_URL
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class PipiService {
    private val retrofit: Retrofit by lazy {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY

        val client = OkHttpClient.Builder()
            .addInterceptor(logging)
            .addInterceptor(HeaderRequestInterceptor())
            .build()

        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val loginApi: PipiApi = retrofit.create(PipiApi::class.java)
}

class HeaderRequestInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request =
            chain.request().newBuilder().addHeader("authorization", "Bearer ${Pipi.prefs.token}")
                .build()
        val response = chain.proceed(request)

        if (response.headers("Set-Cookie").isNotEmpty()) {
//            val regex = ".*(accessToken="
            //refreshToken=AS21\d321ASdsda, accessToken=AS213add\d23eaa; Path=/; HttpOnly
            response.header("Set-Cookie").let { header ->
                if (header != null) {
                    ".*accessToken=([^;]*).*".toRegex()
                        .matchEntire(header)?.groups?.get(1)?.value.let { token ->
                        Pipi.prefs.token = token
                    }
//                    // accessToken이 들어있는 쿠키이면 이 쿠키에서 accessToken을 가져온다.
//                    if ("accessToken" in header) {
//                        Pipi.prefs.token = header.split(' ')[1].split('=')[1].removeSuffix(";")
//                    }
                }
            }
        }

        return response
    }

}