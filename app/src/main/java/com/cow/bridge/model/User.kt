package com.cow.bridge.model

/**
 * Created by jihaeseong on 2018. 7. 12..
 */

class User{
    constructor(userUuid: String){
        this.userUuid = userUuid
    }

    constructor(userUuid : String, userName : String, userType : Int) {
        this.userUuid = userUuid
        this.userName = userName
        this.userType = userType
    }

    var userUuid : String = ""
    var userName : String = ""
    var userType : Int = 0
}