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
    @GET("/contents/nextcontents/{lastcontentsIdx}/{contentsIdx}")
    fun recommandVideoContentsList (@Path("lastcontentsIdx") lastcontentsIdx : Int, @Path("contentsIdx") userIdx : Int): Call<Network>

    @GET("/contents/{contentsIdx}/{lastcontentsIdx}")
    fun videoCommentList (@Path("contentsIdx") pageIdx : Int, @Path("lastcontentsIdx") userIdx : Int): Call<Network>

    @GET("/contents/getcontents/{userIdx}/{contentsIdx}/{contentsType}")
    fun getVideoContents(@Path("userIdx")userIdx : Int, @Path("contentsIdx")contentsIdx : Int, @Path("contentsType")contentsType : Int): Call<Network>
}
