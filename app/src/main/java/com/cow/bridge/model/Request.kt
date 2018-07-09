package com.cow.bridge.model

import java.io.Serializable
import java.util.*

/**
 * Created by jihaeseong on 2018. 7. 9..
 */
class Request : Serializable {
    var iboardTitle : String = ""
    var iboardDate : Date = Date()
    var userIdx : Int = 0

}
