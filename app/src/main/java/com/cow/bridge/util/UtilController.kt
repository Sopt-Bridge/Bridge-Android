package com.cow.bridge.util

import android.content.Context
import android.content.res.Resources
import android.util.DisplayMetrics

import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date

/**
 * Created by jihaeseong on 2018. 7. 7..
 */

object UtilController {


    private var uniqueInstance: UtilController? = null

    // 클래스의 인스턴스를 만들어서 리턴해 준다.
    val instance: UtilController
        get() {
            if (uniqueInstance == null) {
                uniqueInstance = UtilController
            }
            return this!!.uniqueInstance!!
        }


    fun convertDpToPixel(dp: Float, context: Context): Float {
        val resources = context.resources
        val metrics = resources.displayMetrics
        return dp * (metrics.densityDpi / 160f)
    }

    fun convertPixelsToDp(px: Float, context: Context): Float {
        val resources = context.resources
        val metrics = resources.displayMetrics
        return px / (metrics.densityDpi / 160f)
    }


    fun timeformat(time: Date): String {
        var time_str = ""
        val cal: Calendar
        val cur_time: Long
        val get_time: Long
        val new_time: Long
        val seconds: Long
        val minutes: Long
        val hours: Long
        val days: Long
        val now = Date()
        cur_time = now.time
        get_time = time.time//+32400000;

        cal = Calendar.getInstance()
        cal.timeInMillis = cur_time - get_time
        new_time = cal.timeInMillis / 1000
        days = new_time / 3600 / 24
        hours = new_time / 3600 % 24
        minutes = new_time / 60 % 60
        seconds = new_time % 60

        if (days > 7) {
            val sdFormat = SimpleDateFormat("yyyy-MM-dd")
            time_str = sdFormat.format(get_time)
        } else if (days >= 1) {
            time_str = days.toString() + "days ago"
        } else if (hours >= 1) {
            time_str = hours.toString() + "hours ago"
        } else if (minutes >= 1) {
            time_str = minutes.toString() + "mins ago"
        } else if (seconds < 60) {
            time_str = "now"
        } else {
            val sdFormat = SimpleDateFormat("yyyy-MM-dd")
            time_str = sdFormat.format(get_time)
        }

        return time_str
    }


}
