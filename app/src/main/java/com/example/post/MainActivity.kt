package com.example.post

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.awaitResponse
import retrofit2.converter.gson.GsonConverterFactory
import com.example.post.UserItem as UserItem

class MainActivity : AppCompatActivity() {



    lateinit var goToAddUser : Button
    lateinit var goTodelUp : Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        goToAddUser = findViewById(R.id.goToAdduser)
        goTodelUp = findViewById(R.id.delUpd)

        showUsersInfo()

        goToAddUser.setOnClickListener {
            val intent = Intent(this, addUser::class.java)
            startActivity(intent)



        }

        goTodelUp.setOnClickListener {
            val intent = Intent(this, UpdateDelete::class.java)
            startActivity(intent)

        }





    }


    private fun showUsersInfo() {



        val api = Retrofit.Builder()
            .baseUrl("https://dojo-recipes.herokuapp.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(apiInterface::class.java)


        CoroutineScope(Dispatchers.IO).launch {
            val response: Response<List<UserItem>?> = api.getUsersinfo()!!.awaitResponse()
            if (response.isSuccessful) {
                val dataBody = response.body()!!
                Log.d("dataShow" , "Success :  " )




                withContext(Dispatchers.Main) {
                    val myRV = findViewById<RecyclerView>(R.id.rvMain)
                    myRV.adapter = RecyclerViewAdapter(dataBody)
                    myRV.layoutManager = LinearLayoutManager(this@MainActivity)
                    myRV.adapter!!.notifyDataSetChanged()



                }

            }


            else {
                Log.d("dataShow" , "failed !")

            }


        }
    }





}