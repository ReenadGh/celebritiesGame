package com.example.headsup

import retrofit2.Call
import retrofit2.http.*
import retrofit2.http.Body

import okhttp3.ResponseBody

import retrofit2.http.PUT




interface apiInterface {
   // @POST("/test/")
  //  fun addUser(@Body user :UserItem ) : Call<UserItem?>


    @GET("/celebrities/")
    fun getSInfo() :Call <List<SItem>?>

   // @FormUrlEncoded
  //  @PUT("/test/{id}")
  //  fun updateUserName(@Path("id")id : Int , @Field("name") name : String): Call<UserItem?>

   //   @PUT("/test/{id}")
   //  fun updateUserName(@Path("id")id : Int ,  @Body user :UserItem): Call<UserItem?>
  //  @DELETE("/test/{id}")
  //  fun deleteUser(@Path("id")id : Int ): Call<UserItem?>

}