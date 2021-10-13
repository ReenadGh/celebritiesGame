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

class UpdateDelete : AppCompatActivity() {
    lateinit var pk3: EditText
    lateinit var name3: EditText
    lateinit var loc3: EditText
    lateinit var update3: Button
    lateinit var delete3: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_delete)



        pk3 = findViewById(R.id.pk2)
        name3 = findViewById(R.id.name2)
        loc3 = findViewById(R.id.loc2)
        update3 = findViewById(R.id.upBu)
        delete3 = findViewById(R.id.delBu)




        update3.setOnClickListener {


            updateUser(pk3.text.toString().toInt() , name3.text.toString() , loc3.text.toString() )



        }
        delete3.setOnClickListener {
            deleteUser(pk3.text.toString().toInt())


        }


    }


    private fun updateUser(pk :Int , newName: String , newLoc : String) {


        val api = Retrofit.Builder()
            .baseUrl("https://dojo-recipes.herokuapp.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(apiInterface::class.java)


        CoroutineScope(Dispatchers.IO).launch {
            val response: Response<UserItem?> = api.updateUserName(pk, UserItem(newLoc,newName,0))!!.awaitResponse()
            if (response.isSuccessful) {
                val data = response.body()!!
                Log.d("dataUpdate", "Success : " + data.toString())
                withContext(Dispatchers.Main) {
                    Toast.makeText(this@UpdateDelete, " user Updated ! ", Toast.LENGTH_SHORT).show()

                }

            } else {
                Log.d("dataUpdate", "failed !")

            }


        }
    }



    private fun deleteUser(pk : Int) {


        val api = Retrofit.Builder()
            .baseUrl("https://dojo-recipes.herokuapp.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(apiInterface::class.java)


        CoroutineScope(Dispatchers.IO).launch {
            val response: Response<UserItem?> = api.deleteUser(pk)!!.awaitResponse()
            if (response.isSuccessful) {
                val data = response.body()!!
                Log.d("dataDeleted", "Success : " + data.toString())
                withContext(Dispatchers.Main) {
                    Toast.makeText(this@UpdateDelete, "User deleted !", Toast.LENGTH_SHORT).show()


                }

            } else {
                Log.d("dataDeleted", "failed !")

            }


        }
    }
}


