package com.cow.bridge.model

import java.io.Serializable
import java.util.*

/**
 * Created by jihaeseong on 2018. 7. 8..
 */
class Hash : Serializable {
    constructor(hashName : String, userIdx : Int){
        this.hashName = hashName
        this.userIdx = userIdx
    }

    var hashName : String = ""
    var hashImg : String = ""
    var hashCnt : Int = 0
    var subflagresult : Int = 0
    var userIdx : Int = 0

}
