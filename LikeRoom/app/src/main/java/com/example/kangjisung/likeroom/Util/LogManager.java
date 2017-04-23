package com.example.kangjisung.likeroom.Util;

import android.util.Log;

import com.example.kangjisung.likeroom.DefineManager;

/**
 * Created by stories2 on 2017. 4. 23..
 */
public class LogManager extends DefineManager {
    public static void PrintLog(String className, String methodName, String message, int logLevel) {
        String logMsg = "[" + className + "] {" + methodName + "} (" + message + ")";
        switch (logLevel) {
            case LOG_LEVEL_VERBOSE:
                LogVerbose(logMsg);
                break;
            case LOG_LEVEL_DEBUG:
                LogDebug(logMsg);
                break;
            case LOG_LEVEL_INFO:
                LogInfo(logMsg);
                break;
            case LOG_LEVEL_WARN:
                LogWarn(logMsg);
                break;
            case LOG_LEVEL_ERROR:
                LogError(logMsg);
                break;
            default:
                LogError("[LogManager] {PrintLog} (Wrong Logger Message)");
                break;
        }
    }

    public static void LogVerbose(String logBody) {
        Log.v(APP_NAME, logBody);
    }

    public static void LogDebug(String logBody) {
        Log.d(APP_NAME, logBody);
    }

    public static void LogInfo(String logBody) {
        Log.i(APP_NAME, logBody);
    }

    public static void LogWarn(String logBody) {
        Log.w(APP_NAME, logBody);
    }

    public static void LogError(String logBody) {
        Log.e(APP_NAME, logBody);
    }
}
