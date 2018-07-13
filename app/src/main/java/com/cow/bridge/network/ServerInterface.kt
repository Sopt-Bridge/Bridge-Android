package com.cow.bridge.network

import com.cow.bridge.model.*
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

    //Video contents
    @GET("/contents/nextcontents/{lastcontentsIdx}/{contentsIdx}")
    fun recommandVideoContentsList (@Path("lastcontentsIdx") lastcontentsIdx : Int, @Path("contentsIdx") contentsIdx: Int): Call<Network>

    @POST("/contents/getcontents/{userIdx}/{contentsIdx}/{contentsType}")
    fun getVideoContents(@Body userIdx : Int, @Body contentsIdx : Int, @Body contentsType : Int): Call<Network>

    //contents

    @POST("/contents/clike")
    fun clikeContents(@Body content : Content)  : Call<Network>

    @POST("/contents/ccomment_write")
    fun contentsCommentWrite(@Body contentsComment: ContentsComment): Call<Network>

    @POST("/contents/ccomment_delete")
    fun contentsCommentDelete(@Body contentsComment: ContentsComment): Call<Network>

    @GET("/contents/ccomment_view/{contentsIdx}/{lastcontentsIdx}")
    fun getContentCommentList(@Path("contentsIdx") contentsIdx : Int, @Path("lastcontentsIdx") lastcontentsIdx : Int): Call<Network>


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

    @GET("/trequest/trequestcomment_view/{iboardIdx}/{lastcontentsIdx}")
    fun getrequestComment(@Path("iboardIdx") iboardIdx: Int,@Path("lastcontentsIdx") lastcontentsIdx: Int): Call<Network>

    @POST("/trequest/trequestcomment_write")
    fun requestCommentWrite(@Body request : Request): Call<Network>

    @POST("/trequest/trequestcomment_delete")
    fun requestCommentDelete(@Body request : Request): Call<Network>


    //search

    @GET("/search/search/{pageIdx}/{searchname}/{searchType}/{sortType}")
    fun searchContents(@Path("pageIdx") pageIdx : Int, @Path("searchname") searchname : String, @Path("searchType") searchType : Int, @Path("sortType") sortType : Int): Call<Network>

    @GET("/search/getonehash/{userIdx}/{hashName}")
    fun getSearchHashInfo(@Path("userIdx") userIdx: Int, @Path("hashName") hashName: String): Call<Network>


    //login

    @GET("/user/getmytext/{userIdx}")
    fun getMyTextList(@Path("userIdx") userIdx : Int): Call<Network>

    @POST("/user/login")
    fun login(@Body user : User): Call<Network>

    @POST("/user/quit")
    fun withdrawal(@Body user : User): Call<Network>


    //library

    @POST("/library/addgroupcontents")
    fun addGroupContents(@Body group : Group): Call<Network>

    @POST("/library/contentsdelete")
    fun deleteGroupGontents(@Body group : Group): Call<Network>

    @GET("/library/recentvideo/{userIdx}")
    fun getRecentVideoList(@Path("userIdx") userIdx : Int): Call<Network>

    @GET("/library/getgroupcontents/{lastcontentsIdx}/{userIdx}/{groupIdx}")
    fun getGroupContentsList(@Path("lastcontentsIdx") lastcontentsIdx : Int, @Path("userIdx") userIdx : Int, @Path("groupIdx") groupIdx : Int): Call<Network>

    @GET("/library/grouplist/{userIdx}")
    fun getGroupList(@Path("userIdx") userIdx : Int): Call<Network>

    @POST("/library/addgroup")
    fun addGroup(@Body group : Group): Call<Network>

    @POST("/library/groupmodify")
    fun modifyGroup(@Body group : Group): Call<Network>

    @POST("/library/groupdelete")
    fun deleteGroup(@Body group : Group): Call<Network>


}
