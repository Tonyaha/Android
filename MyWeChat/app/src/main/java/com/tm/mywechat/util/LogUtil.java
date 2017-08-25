package com.tm.mywechat.util;

import android.util.Log;

import java.util.HashMap;

/**
 * 日志打印工具类
 *
 * Created by WangJie on 2017-03-15.
 */

public class LogUtil {

    public static boolean showRunningLog;

    public static boolean showHttpDataLog;

    private static String TAG = "WangJ";

    /**
     * 日志输出单个字符串
     *
     * @param value 日志内容
     */
    public static void log(String value){
        if(showRunningLog){
            Log.i(TAG, value);
        }
    }

    /**
     * 日志输出单个字符串
     *
     * @param key 待输出字符串含义的标注
     * @param value 日志内容
     */
    public static void log(String key, String value){
        if(showRunningLog){
            Log.i(TAG, key + ": " + value);
        }
    }

    /**
     * 日志输出字符串数组
     *
     * @param key 待输出字符串含义的标注
     * @param strArray 要查看的数组
     */
    public static void log(String key, String[] strArray){
        if(showRunningLog){
            Log.i(TAG, key + ":");
            for (int i = 0; i < strArray.length; i++) {
                Log.i(TAG, "item[" + i + "] : " + strArray[i]);
            }
        }
    }

    /**
     * 日志输出HashMap
     *
     * @param key 待输出字符串含义的标注
     * @param map 要查看的map
     */
    public static void log(String key, HashMap<String, String> map){
        if(showRunningLog){
            Log.i(TAG, key + ":");
            for (HashMap.Entry<String, String> entry : map.entrySet()) {
                Log.i(TAG, entry.getKey() + " : " + entry.getValue());
            }
        }
    }

    /**
     * 打印请求参数
     * @param requestStr 请求内容
     */
    public static void logRequest(String requestStr){
        if(showHttpDataLog){
            Log.i(TAG, "The RequestStr：\n" + requestStr);
        }
    }

    /**
     * 打印服务器返回结果字符串
     * @param responseStr 报文返回字符串
     */
    public static void logResponse(String responseStr){
        if(showHttpDataLog){
            Log.i(TAG, "The ResponseStr：\n" + responseStr);
        }
    }

    /**
     * Error 错误记录
     * @param errMsg 错误信息
     */
    public static void logErr(String errMsg){
        Log.e(TAG, errMsg);
    }
}
