package com.asiainfo.crazyguessmusic.utils;

import android.util.Log;

/**
 * Log日志封装工具类
 */

public class LogUtil {

    public static final boolean DEBUG = true;

    public static void d(String tag, String msg) {

        if (DEBUG) {

            Log.d(tag, msg);

        }

    }

    public static void e(String tag, String msg) {

        if (DEBUG) {

            Log.e(tag, msg);

        }

    }

    public static void v(String tag, String msg) {

        if (DEBUG) {

            Log.v(tag, msg);

        }

    }

    public static void w(String tag, String msg) {

        if (DEBUG) {

            Log.w(tag, msg);

        }

    }

    public static void i(String tag, String msg) {

        if (DEBUG) {

            Log.i(tag, msg);

        }

    }
}
