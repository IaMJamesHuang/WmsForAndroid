package com.kt.james.wmsforandroid.utils;

import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import com.kt.james.wmsforandroid.app.App;

import java.util.Random;

/**
 * Created by jingbin on 2016/11/22.
 * 获取原生资源
 */
public class ResourceUtil {

    /**
     * 随机颜色
     */
    public static int randomColor() {
        Random random = new Random();
        int red = random.nextInt(150) + 50;//50-199
        int green = random.nextInt(150) + 50;//50-199
        int blue = random.nextInt(150) + 50;//50-199
        return Color.rgb(red, green, blue);
    }

    public static Drawable getDrawable(int resid) {
        return ContextCompat.getDrawable(App.getInstance(), resid);
    }

    public static int getColor(int resid) {
        return getResoure().getColor(resid);
    }

    public static Resources getResoure() {
        return App.getInstance().getResources();
    }

    public static String[] getStringArray(int resid) {
        return getResoure().getStringArray(resid);
    }

    public static String getString(int resid) {
        return getResoure().getString(resid);
    }

    public static float getDimens(int resId) {
        return getResoure().getDimension(resId);
    }

    public static void removeSelfFromParent(View child) {

        if (child != null) {

            ViewParent parent = child.getParent();

            if (parent instanceof ViewGroup) {

                ViewGroup group = (ViewGroup) parent;

                group.removeView(child);
            }
        }
    }

}
