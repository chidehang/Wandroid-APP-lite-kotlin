package com.cdh.wandroid.util;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.PowerManager;
import android.os.SystemClock;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.webkit.WebSettings;
import android.webkit.WebView;


import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Created by chidehang on 2019/2/26
 */
public class DeviceUtils {

    private DeviceUtils() {
    }

    /**
     * 获取屏幕宽度
     */
    public static int getScreenWidth(Context context) {
        DisplayMetrics dm = context.getApplicationContext().getResources().getDisplayMetrics();
        if (dm.widthPixels > dm.heightPixels) {
            return dm.heightPixels;
        } else {
            return dm.widthPixels;
        }
    }

    /**
     * 获取屏幕高度
     */
    public static int getScreenHeight(Context context) {
        DisplayMetrics dm = context.getApplicationContext().getResources().getDisplayMetrics();
        if (dm.widthPixels > dm.heightPixels) {
            return dm.widthPixels;
        } else {
            return dm.heightPixels;
        }
    }

    /**
     * 密度转换像素
     * @param dipValue dp值
     * @return 像素
     */
    public static int dip2px(Context context, float dipValue) {
        DisplayMetrics dm = context.getApplicationContext().getResources().getDisplayMetrics();
        return (int) (dipValue * dm.density + 0.5f);
    }

}
