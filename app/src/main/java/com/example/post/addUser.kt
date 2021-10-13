package com.example.post

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.awaitResponse
import retrofit2.converter.gson.GsonConverterFactory

class addUser : AppCompatActivity() {

    lateinit var name: EditText
    lateinit var loc: EditText
    lateinit var save: Button
    lateinit var view: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_user)
        name = findViewById(R.id.name1)
        loc = findViewById(R.id.loc)
        save = findViewById(R.id.save)
        view = findViewById(R.id.view)


        save.setOnClickListener {

            if (name.toString().isNotEmpty() && loc.toString().isNotEmpty()) {

                addUser(name.text.toString(), loc.text.toString())
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            } else {

                Toast.makeText(this, "Fill all fields !! ", Toast.LENGTH_SHORT).show()


            }


        }
        view.setOnClickListener {

            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)

        }


    }


    private fun addUser(name: String, loc: String) {


        val api = Retrofit.Builder()
            .baseUrl("https://dojo-recipes.herokuapp.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(apiInterface::class.java)


        CoroutineScope(Dispatchers.IO).launch {
            val response: Response<UserItem?> = api.addUser(UserItem(loc, name,0))!!.awaitResponse()
            if (response.isSuccessful) {
                val data = response.body()!!
                Log.d("dataPush", "Success : " + data.toString())
                withContext(Dispatchers.Main) {
              //      Toast.makeText(this, "User added !", Toast.LENGTH_SHORT).show()


                }

            } else {
                Log.d("dataPush", "failed !")

            }


        }
    }
}


