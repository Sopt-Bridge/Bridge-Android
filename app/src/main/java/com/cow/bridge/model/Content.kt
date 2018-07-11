package com.cow.bridge.model

import java.io.Serializable
import java.util.*

/**
 * Created by jihaeseong on 2018. 7. 4..
 */

class Content : Serializable{
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
<<<<<<< HEAD
    var imgCnt : Int = 0
    var contentsRuntime : String = "00:00"
    var likeFlag : Int = 0
=======
    var contentsUrl : String = ""
    var contentsoriginUrl : String = ""
    var contentsRuntime : String = "00:00"
    var imgCnt : Int = 0
    var commentCnt : Int = 0
>>>>>>> 14ae2e36045b5d6ef8be841ebd4fba45ac79e485

}
