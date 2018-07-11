package com.cow.bridge.network

import com.cow.bridge.model.Content
import com.cow.bridge.model.ContentsComment
import com.cow.bridge.model.Hash
import com.cow.bridge.model.Request
import java.util.ArrayList

/**
 * Created by jihaeseong on 2018. 7. 4..
 */

class Result{

    var userIdx : Int? = 0
    var token : String? = ""

    var contents_list: ArrayList<Content>? = null
    var hashcontents_list: ArrayList<Hash>? = null
    var recommendedhashcontents_list: ArrayList<Hash>? = null
    var request_list: ArrayList<Request>? = null
    var comments_list : ArrayList<ContentsComment>? = null
}
