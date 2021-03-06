package com.cow.bridge.model

import java.io.Serializable

/**
 * Created by jihaeseong on 2018. 7. 12..
 */

class Group : Serializable{
    constructor()

    constructor(groupIdx : Int){
        this.groupIdx = groupIdx
    }

    constructor(groupIdx : Int, contentsIdx : Int){
        this.groupIdx = groupIdx
        this.contentsIdx = contentsIdx
    }

    constructor(userIdx : Int, groupTitle : String, groupColor : String){
        this.userIdx = userIdx
        this.groupTitle = groupTitle
        this.groupColor = groupColor
    }

    constructor(userIdx : Int, groupIdx: Int, groupTitle : String, groupColor : String){
        this.userIdx = userIdx
        this.groupIdx = groupIdx
        this.groupTitle = groupTitle
        this.groupColor = groupColor
    }

    var groupTitle : String = ""
    var groupBgimage : String = ""
    var groupIdx : Int = 0
    var groupColor : String = ""
    var userIdx : Int = 0
    var contentsIdx : Int = 0
}