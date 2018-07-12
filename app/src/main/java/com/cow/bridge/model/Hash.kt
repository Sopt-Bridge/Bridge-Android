package com.cow.bridge.model

import java.io.Serializable
import java.util.*

/**
 * Created by jihaeseong on 2018. 7. 8..
 */
class Hash : Serializable {
    constructor(hashName : String){
        this.hashName = hashName
    }

    constructor(hashName : String, userIdx : Int){
        this.hashName = hashName
        this.userIdx = userIdx
    }

    constructor(hashName : String, userIdx : Int, sortType : Int){
        this.hashName = hashName
        this.userIdx = userIdx
        this.sortType = sortType
    }

    var hashName : String = ""
    var hashImg : String = ""
    var hashCnt : Int = 0
    var subflagresult : Int = 0
    var userIdx : Int = 0
    var pageIdx : Int = 0
        set(value) {
            field = value
        }
    var sortType : Int = 0

}
