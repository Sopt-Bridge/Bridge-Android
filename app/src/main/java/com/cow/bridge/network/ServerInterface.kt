package com.cow.bridge.network

import retrofit2.Call
import retrofit2.http.GET

/**
 * Created by jihaeseong on 2017. 2. 13..
 */
interface ServerInterface {

    @get:GET("/home/recommended")
    val recommendedList: Call<Network>

}
