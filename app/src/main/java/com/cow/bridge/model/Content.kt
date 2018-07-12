package com.cow.bridge.model

import java.io.Serializable
import java.util.*

/**
 * Created by jihaeseong on 2018. 7. 4..
 */

class Content : Serializable{
    constructor(){}

    constructor(contentsIdx : Int, userIdx : Int){
        this.contentsIdx = contentsIdx
        this.userIdx = userIdx
    }
    var contentsIdx : Int = 0
    var contentsTitle : String = ""
    var contentsInfo : String = ""
    var contentsHit : Int = 0
    var contentsCategory : Int = 0
    var contentsDate : Date = Date()
    var contentsLike : Int = 0
    var hashName1 : String = ""
    var hashName2 : String = ""
    var hashName3 : String = ""
    var contentsType : Int = 0
    var imgCnt : Int = 0
    var contentsRuntime : String = "00:00"
    var contentsUrl : String = ""
    var contentsoriginUrl : String = ""
    var commentCnt : Int = 0
    var likeFlag : Int = 0
    var subFlag : Int = 1
    var userIdx : Int = 0
}
