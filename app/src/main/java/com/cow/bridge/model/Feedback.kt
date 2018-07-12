package com.cow.bridge.model

import java.io.Serializable

class Feedback : Serializable {

    constructor(userIdx: Int, contentsIdx: Int, fboardContent : String){
        this.userIdx = userIdx
        this.contentsIdx = contentsIdx
        this.fboardContent = fboardContent
    }

    var userIdx : Int = 0
    var contentsIdx : Int = 0
    var fboardContent : String = ""

}