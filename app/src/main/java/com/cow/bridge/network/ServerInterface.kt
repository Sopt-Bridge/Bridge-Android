package com.cow.bridge.network

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

/**
 * Created by jihaeseong on 2017. 2. 13..
 */
interface ServerInterface {

    @get:GET("/home/recommended")
    val recommendedList: Call<Network>


    //Video contents
    @POST("/contents/{pageIdx}/{userIdx}")
    fun recommandVideoContentsList (@Path("pageIdx") pageIdx : Int, @Path("UserIdx") userIdx : Int): Call<Network>

    @GET("/contents/{pageIdx}/{userIdx}")
    fun VideoCommentList (@Path("pageIdx") pageIdx : Int, @Path("UserIdx") userIdx : Int): Call<Network>

}
