package com.vhuthu.work

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.vhuthu.work.models.FarmerResponse
import com.vhuthu.work.ui.theme.WorkTheme
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WorkTheme {
                // A surface container using the 'background' color from the theme
                displayListView()
            }
        }
    }
}

fun getJSONData(courseList: MutableList<String>, ctx: Context) {
    // on below line we are creating a retrofit
    // builder and passing our base url
    val retrofit = Retrofit.Builder()
        .baseUrl("http://10.100.2.38:8080/api/v2/")
        // on below line we are calling add
        // Converter factory as Gson converter factory.
        .addConverterFactory(GsonConverterFactory.create())
        // at last we are building our retrofit builder.
        .build()

    // below line is to create an instance for our retrofit api class.
    val retrofitAPI = retrofit.create(RetrofitAPI::class.java)

    val token = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJqcUBnbWFpbC5jb20iLCJyb2xlcyI6IlJPTEVfRkFSTUVSIiwiaWF0IjoxNjk0MTc2NDUzLCJleHAiOjE2OTc3NzY0NTN9.y_G9M72pvdAUCo_fjz4IRA1mSHMn61DbrGn2JrguPWk"

    // on below line we are calling a method to get all the courses from API.
    val call: Call<FarmerResponse> = retrofitAPI.getLanguages(token = "Bearer $token")

    // on below line we are calling method to enqueue and calling
    // all the data from array list.
    call!!.enqueue(object : Callback<FarmerResponse> {
        override fun onResponse(
            call: Call<FarmerResponse>?,
            response: Response<FarmerResponse>?
        ) {
            // on below line we are checking if response is successful.
            if (response != null) {
                if (response.isSuccessful) {

                    Log.d("Tag", response.body()?.data.toString())

                    var lst: ArrayList<FarmerResponse> = ArrayList()

                    val farmerResponse = response.body()

                    if (farmerResponse != null) {
                        lst.add(farmerResponse)
                    }

                    for (i in 0 until lst.size) {
                        // on below line we are adding data to course list.
                        courseList.add(lst.get(i).data.farms[0].incomeStatements[0].incomeStatementItems.toString())
                    }

                }
            }
        }

        override fun onFailure(call: Call<FarmerResponse>?, t: Throwable) {

            Log.e("Error", t.toString())
            // displaying an error message in toast
            Toast.makeText(ctx, "Fail to get the data..", Toast.LENGTH_SHORT)
                .show()

        }
    })
}

@Composable
fun displayListView() {
    val context = LocalContext.current
    // on below line we are creating and
    // initializing our array list
    val courseList = remember { mutableStateListOf<String>() }
    getJSONData(courseList, context)

    // on the below line we are creating a
    // lazy column for displaying a list view.
    // on below line we are calling lazy column
    // for displaying listview.
    LazyColumn {
        // on below line we are populating
        // items for listview.
        items(courseList) { language ->
            // on below line we are specifying ui for each item of list view.
            // we are specifying a simple text for each item of our list view.
            Text(language, modifier = Modifier.padding(15.dp))

            //Text(language., modifier = Modifier.padding(15.dp))
            // on below line we are specifying
            // divider for each list item
            Divider()
        }
    }
}