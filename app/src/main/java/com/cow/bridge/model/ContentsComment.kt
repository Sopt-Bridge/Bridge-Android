package com.cow.bridge.model

import java.io.Serializable
import java.util.*

class ContentsComment : Serializable {
    var CcmtIdx: Int = 0  //컨텐츠
    var recommentCnt: Int = 0
    var CcmtContent: String = ""
    var userName: String = ""
    var CcmtDate : Date = Date()



}