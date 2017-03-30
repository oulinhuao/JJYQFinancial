package com.proj.JJYQFinancial.utils;

/**
 * Android 系统版本
 * @author Tony
 */
public class BuildVersionUtil {

    /**
     * SDK 8及以上
     * @return
     * @author Tony
     * @date 2014年11月6日 上午10:32:06
     */
    public static boolean isFroyoOrLater()
    {
        return android.os.Build.VERSION.SDK_INT >= 8;
    }

    /**
     * SDK 9及以上
     * @return
     * @author Tony
     * @date 2014年11月6日 上午10:32:16
     */
    public static boolean isGingerbreadOrLater()
    {
        return android.os.Build.VERSION.SDK_INT >= 9;
    }

    /**
     * 是否是api 11（Android 3.0） 以后
     * @return
     * @author Tony
     * @date 2014-4-5 下午9:38:14
     */
    public static boolean isHoneycombOrLater()
    {
        return android.os.Build.VERSION.SDK_INT >= 11;
    }

    /**
     * SDK 14及以上
     * @return
     * @author Tony
     * @date 2014年11月6日 上午10:30:40
     */
    public static boolean isICSOrLater()
    {
        return android.os.Build.VERSION.SDK_INT >= 14;
    }
    /**
     * SDK 16及以上
     * @return
     * @author Tony
     * @date 2014年11月6日 上午10:31:09
     */
    public static boolean isJellyBeanOrLater()
    {
        return android.os.Build.VERSION.SDK_INT >= 16;
    }

    /**
     * SDK 17及以上
     * @return
     * @author Tony
     * @date 2014年11月6日 上午10:31:17
     */
    public static boolean isJellyBeanMR1OrLater()
    {
        return android.os.Build.VERSION.SDK_INT >= 17;
    }

    /**
     * SDK 18及以上
     * @return
     * @author Tony
     * @date 2014年11月6日 上午10:31:29
     */
    public static boolean isJellyBeanMR2OrLater()
    {
        return android.os.Build.VERSION.SDK_INT >= 18;
    }
    
    /**
     * SDK 19及以上
     * @return
     * @author Tony
     * @date 2014年11月6日 上午10:31:38
     */
    public static boolean isKitKatOrLater()
    {
    	return android.os.Build.VERSION.SDK_INT >= 19;
    }
}
