package com.cow.bridge.model

import java.io.Serializable
import java.util.*


class Request : Serializable {
    constructor()

    constructor(userIdx : Int, icmtContent : String, iboardIdx : Int){
        this.userIdx = userIdx
        this.icmtContent = icmtContent
        this.iboardIdx = iboardIdx

    }

    constructor(userIdx : Int, icmtIdx : Int){
        this.userIdx = userIdx
        this.icmtIdx = icmtIdx
    }

    var iboardIdx : Int = 0
    var iboardTitle : String = ""
    var iboardUrl : String = ""
    var iboardContent : String = ""
    var iboardDate : Date = Date()
    var userIdx : Int = 0
    var userName : String = ""
    var icmtDate: Date = Date()
    var icmtContent : String = ""
    var icmtIdx : Int = 0


}
