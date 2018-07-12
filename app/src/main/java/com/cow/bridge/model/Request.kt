package com.cow.bridge.model

import java.io.Serializable
import java.util.*

/**
 * Created by jihaeseong on 2018. 7. 9..
 */
class Request : Serializable {
    constructor()

    var iboardIdx : Int = 0
    var iboardTitle : String = ""
    var iboardUrl : String = ""
    var iboardContent : String = ""
    var iboardDate : Date = Date()
    var userIdx : Int = 0
    var userName : String = ""

}
