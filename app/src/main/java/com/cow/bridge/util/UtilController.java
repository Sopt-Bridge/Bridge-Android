package com.cow.bridge.util;

import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by jihaeseong on 2018. 7. 7..
 */

public class UtilController {


    private static UtilController uniqueInstance;

    private UtilController() {
    }

    // 클래스의 인스턴스를 만들어서 리턴해 준다.
    public static UtilController getInstance() {
        if (uniqueInstance == null) {
            uniqueInstance = new UtilController();
        }
        return uniqueInstance;
    }

    public static float convertDpToPixel(float dp, Context context){
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float px = dp * (metrics.densityDpi / 160f);
        return px;
    }

    public static float convertPixelsToDp(float px, Context context){
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float dp = px / (metrics.densityDpi / 160f);
        return dp;
    }


    public static String timeformat(Date time){
        String time_str="";
        Calendar cal;
        long cur_time, get_time, new_time;
        long seconds, minutes, hours, days;
        Date now = new Date();
        cur_time = now.getTime();
        get_time = time.getTime();//+32400000;

        cal = Calendar.getInstance();
        cal.setTimeInMillis(cur_time - get_time);
        new_time = cal.getTimeInMillis()/1000;
        days = new_time/3600/24;
        hours = new_time/3600%24;
        minutes = new_time/60%60;
        seconds = new_time%60;

        if(days>7){
            DateFormat sdFormat = new SimpleDateFormat("yyyy-MM-dd");
            time_str = sdFormat.format(get_time);
        }else if(days >= 1){
            time_str = days+"일 전";
        }else if(hours >= 1){
            time_str = hours+"시간 전";
        }else if(minutes >= 1){
            time_str = minutes+"분 전";
        }else if(seconds < 60){
            time_str = "방금";
        }else{
            DateFormat sdFormat = new SimpleDateFormat("yyyy-MM-dd");
            time_str = sdFormat.format(get_time);
        }

        return time_str;
    }


}
