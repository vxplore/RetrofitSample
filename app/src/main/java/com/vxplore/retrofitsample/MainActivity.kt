package com.vxplore.retrofitsample

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import com.vxplore.retrofitsample.ui.theme.RetrofitSampleTheme
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RetrofitSampleTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Column(){
                        Button(
                            onClick = {
                                val retrofit = Retrofit.Builder()
                                    .baseUrl("https://jsonplaceholder.typicode.com/")
                                    .addConverterFactory(GsonConverterFactory.create())
                                    .build()

                                val service: JsonPlaceholder =
                                    retrofit.create(JsonPlaceholder::class.java)
                                val userCall: Call<User> = service.getUser("1")
                                userCall.enqueue(object: Callback<User>{
                                    override fun onResponse(
                                        call: Call<User>,
                                        response: Response<User>
                                    ) {
                                        Log.d("fkldjfldfd",response.body()?.title.toString())
                                    }

                                    override fun onFailure(call: Call<User>, t: Throwable) {
                                        Log.d("fkldjfldfd","failed")
                                    }
                                })
                            }
                        ) {
                            Text("Call")
                        }
                    }
                }
            }
        }
    }
}

interface JsonPlaceholder {
    @GET("todos/{user}")
    fun getUser(@Path("user") id: String): Call<User>
}

data class User (
    val userID: Long,
    val id: Long,
    val title: String,
    val completed: Boolean
)
