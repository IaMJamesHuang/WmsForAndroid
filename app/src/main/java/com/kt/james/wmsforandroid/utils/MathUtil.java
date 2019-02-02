package com.kt.james.wmsforandroid.utils;

public class MathUtil {

    public static float tryFormatFloat(String value, float defaultValue) {
        float result;
        try {
            result = Float.valueOf(value);
        } catch (Exception e) {
            result = defaultValue;
        }
        return result;
    }

    public static boolean isFloatRight(String value) {
        try {
            float result = Float.parseFloat(value);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

}
