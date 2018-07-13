package com.cow.bridge.model

import java.io.Serializable
import java.util.*

class ContentsComment : Serializable {

    constructor()

    constructor(userIdx : Int,ccmtIdx : Int ){
        this.userIdx = userIdx
        this.ccmtIdx = ccmtIdx
    }

    var ccmtIdx: Int = 0
   // var recommentCnt: Int = 0
    var userName: String = ""
    var ccmtDate : Date = Date()
    var contentsIdx: Int = 0
    var ccmtContent: String = ""
    var userIdx: Int = 0

}