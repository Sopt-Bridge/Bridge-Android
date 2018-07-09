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
    var contentsRuntime : String = "00:00"

}
