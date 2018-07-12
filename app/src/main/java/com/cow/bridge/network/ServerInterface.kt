package com.cow.bridge.network

import com.cow.bridge.model.Content
import com.cow.bridge.model.Feedback
import com.cow.bridge.model.Hash
import com.cow.bridge.model.Request
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.*
import java.util.ArrayList

/**
 * Created by jihaeseong on 2017. 2. 13.. //Good....
 */
interface ServerInterface {

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


    //contents

//    // 이미지 컨텐츠 보기
//    @GET ("/contents/getcontents{userIdx}/{contentsIdx}/{contentsType}")
//    fun imageContents(@Path(""))

    @POST("/contents/clike")
    fun clikeContents(@Body content : Content)  : Call<Network>

    // feedback

    @POST("/feedback/feedback_write")
    fun writeFeedback(@Body feedback : Feedback ) : Call<Network>


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

    @POST("/trequest/trequest_write")
    fun requestWriteContents(@Body request : Request): Call<Network>


    //search

    @GET("/search/search/{pageIdx}/{searchname}/{searchType}/{sortType}")
    fun searchContents(@Path("pageIdx") pageIdx : Int, @Path("searchname") searchname : String, @Path("searchType") searchType : Int, @Path("sortType") sortType : Int): Call<Network>


}
