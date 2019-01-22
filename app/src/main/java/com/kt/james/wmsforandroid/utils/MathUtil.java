package com.kt.james.wmsforandroid.utils;

public class MathUtil {

    public static double tryFormatDouble(String value, double defaultValue) {
        double result;
        try {
            result = Double.valueOf(value);
        } catch (Exception e) {
            result = defaultValue;
        }
        return result;
    }

}
