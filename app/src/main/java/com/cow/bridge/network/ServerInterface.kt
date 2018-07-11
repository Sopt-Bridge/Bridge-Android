package com.cow.bridge.network

import com.cow.bridge.model.Hash
import com.cow.bridge.model.Request
import retrofit2.Call
import retrofit2.http.*
import java.util.ArrayList

/**
 * Created by jihaeseong on 2017. 2. 13..
 */
interface ServerInterface {

    //Video contents
    @GET("/contents/nextcontents/{lastcontentsIdx}/{contentsIdx}")
    fun recommandVideoContentsList (@Path("lastcontentsIdx") lastcontentsIdx : Int, @Path("contentsIdx") userIdx : Int): Call<Network>

    @GET("/contents/{contentsIdx}/{lastcontentsIdx}")
    fun videoCommentList (@Path("contentsIdx") pageIdx : Int, @Path("lastcontentsIdx") userIdx : Int): Call<Network>

    @POST("/contents/getcontents/{userIdx}/{contentsIdx}/{contentsType}")
    fun getVideoContents(@Body userIdx : Int, @Body contentsIdx : Int, @Body contentsType : Int): Call<Network>

    @POST("/contents/clike/{contentsIdx}/{userIdx}")
    fun changeVideoContentsLike(@Body contentsIdx : Int, @Body userIdx : Int): Call<Network>


    //home
    @GET("/home/recent/{contentsCategory}/{lastcontentsIdx}")
    fun recentContentsList(@Path("contentsCategory") category : Int, @Path("lastcontentsIdx") lastcontentsIdx : Int): Call<Network>

    @GET("/home/hitsort/{contentsCategory}/{pageIdx}")
    fun hitsortContentsList(@Path("contentsCategory") category : Int, @Path("pageIdx") pageIdx : Int): Call<Network>

    @GET("/home/likesort/{contentsCategory}/{pageIdx}")
    fun likesortContentsList(@Path("contentsCategory") category : Int, @Path("pageIdx") pageIdx : Int): Call<Network>

    @GET("/home/nowtrend/{contentsCategory}")
    fun nowTrendContentsList(@Path("contentsCategory") category : Int): Call<Network>

    @GET("/home/recommended")
    fun recommendedContentsList(): Call<Network>


    //subscribe

    @GET("/subscribe/recommendedhashlist/{pageIdx}/{userIdx}")
    fun recommendedHashList(@Path("pageIdx") pageIdx : Int, @Path("userIdx") userIdx : Int): Call<Network>

    @GET("/subscribe/getsubhashlist/{pageIdx}/{userIdx}")
    fun getMySubscribeHashList(@Path("pageIdx") pageIdx : Int, @Path("userIdx") userIdx : Int): Call<Network>

    @POST("/subscribe/hashcontentlist")
    fun getHashContentList(@Body hash : Hash): Call<Network>

    @POST("/subscribe/subscribemodify")
    fun subscribeModify(@Body hash : Hash): Call<Network>


    //request

    @GET("/trequest/trequest_listview/{lastcontentsIdx}")
    fun requestContentsList(@Path("lastcontentsIdx") lastcontentsIdx : Int): Call<Network>

    @GET("/trequest/trequest_search/{searchname}")
    fun requestSearchContentsList(@Path("searchname") searchname : String): Call<Network>
}
