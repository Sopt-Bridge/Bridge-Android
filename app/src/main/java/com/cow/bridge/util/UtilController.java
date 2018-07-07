package com.cow.bridge.util;

import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;

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
}
