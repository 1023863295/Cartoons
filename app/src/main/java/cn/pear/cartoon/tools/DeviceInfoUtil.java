package cn.pear.cartoon.tools;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.provider.Settings;
import android.util.DisplayMetrics;

import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.URLEncoder;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;

import cn.pear.cartoon.global.MyApplication;

import static cn.shpear.ad.sdk.util.Utils.intToIp;

/**
 * Created by liuliang on 2017/6/22.
 */

public class DeviceInfoUtil {
    private static String mIpAddress = "";
    private static String mMacAddress = "00:00:00:00:00:00";
    private final static String MAK_KEK = "mac_key";
    private final static String MAK_VALUE = "mac_value";
    private static String mDeviceModel = "";

    /**
     * 获取本机IPv4地址
     *
     * @param context
     * @return 本机IPv4地址；null：无网络连接
     */
    public static String getIpV4Address(Context context) {
        // 获取WiFi服务
        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        // 判断WiFi是否开启
        if (wifiManager.isWifiEnabled()) {
            // 已经开启了WiFi
            WifiInfo wifiInfo = wifiManager.getConnectionInfo();
            int ipAddress = wifiInfo.getIpAddress();
            String ip = intToIp(ipAddress);
            return ip;
        } else {
            // 未开启WiFi
            return getIpAddress();
        }
    }

    public static String getIpAddress() {
        if(StringUtil.isEmpty(mIpAddress)){
            try {
                for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements();) {
                    NetworkInterface intf = en.nextElement();
                    for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements();) {
                        InetAddress inetAddress = enumIpAddr.nextElement();
                        if (!inetAddress.isLoopbackAddress()) {
                            mIpAddress = inetAddress.getHostAddress().toString();
                        }
                    }
                }
            } catch (SocketException ex) {
                ex.printStackTrace();
            }
        }
        return mIpAddress;
    }

    //--获取手机IMEI号 3
    public static String getIMEI(Context context){
        android.telephony.TelephonyManager tm = (android.telephony.TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        String device_id = "canNotGet";
//			if (checkPermission(context, Manifest.permission.READ_PHONE_STATE)) {
//				device_id = tm.getDeviceId();
//			}
        try {
            device_id=tm.getDeviceId();
            if(device_id==null||device_id.equals("")){
                device_id="canNotGet";
            }
        }catch (Exception e){
            device_id="canNotGet";
        }
        return device_id;
    }

    public static String getAndroidId(Context context){
        return Settings.System.getString(context.getContentResolver(),Settings.Secure.ANDROID_ID);
    }

    public static String getDeviceMacAddress(Context context)
    {
        String addr = getMacAddress(context);
        if(addr.equals("02:00:00:00:00:00"))
        {
            addr = getMacAddr();
        }
        return addr;
    }

    //--获取手机mac地址
    public static String getMacAddress(Context context) {

        if (context == null) {
            context = MyApplication.instance;
        }
        if ((!mMacAddress.equals(mMacAddress)) || (context == null)) {
            return mMacAddress;
        }
        try {
            WifiManager wifi = (WifiManager) context
                    .getSystemService(Context.WIFI_SERVICE);
            WifiInfo info = wifi.getConnectionInfo();
            if (info != null && !StringUtil.isEmpty(info.getMacAddress())) {
                setCacheMacAddress(context, info.getMacAddress());
                return (mMacAddress = info.getMacAddress());
            } else {
                String mac = getCacheMacAddress(context);
                if (!StringUtil.isEmpty(mac)) {
                    return (mMacAddress = mac);
                }else{
                    // "AF"标识来自android客户端的mac地址
                    String randomMacAddress = StringUtil.substringAndAddPrefix(StringUtil.getMD5Str(String.valueOf(System.currentTimeMillis())), 10, "AF");
                    if(!StringUtil.isEmpty(randomMacAddress)){
                        setCacheMacAddress(context, randomMacAddress);
                        return randomMacAddress;
                    }else{
                        return mMacAddress;
                    }
                }
            }

        } catch (Exception e) {

            e.printStackTrace();
        }
        return mMacAddress;
    }

    public static String getMacAddr() {
        try {
            List<NetworkInterface> all = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface nif : all) {
                if (!nif.getName().equalsIgnoreCase("wlan0")) continue;

                byte[] macBytes = nif.getHardwareAddress();
                if (macBytes == null) {
                    return "";
                }

                StringBuilder res1 = new StringBuilder();
                for (byte b : macBytes) {
                    res1.append(String.format("%02X:",b));
                }

                if (res1.length() > 0) {
                    res1.deleteCharAt(res1.length() - 1);
                }
                return res1.toString();
            }
        } catch (Exception ex) {
        }
        return "02:00:00:00:00:00";
    }

    private static void setCacheMacAddress(Context context, String macAddress) {
        if (context != null&&!StringUtil.isEmpty(macAddress)) {
            SharedPreferences preference = context.getSharedPreferences(MAK_KEK, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = preference.edit();
            editor.putString(MAK_VALUE, macAddress);
            editor.commit();
        }
    }

    private static String getCacheMacAddress(Context context) {
        if (context == null) {
            return null;
        }
        SharedPreferences sharedPreferences = context.getSharedPreferences(MAK_KEK, Context.MODE_PRIVATE);
        return sharedPreferences.getString(MAK_VALUE, null);
    }

    //获取手机型号 1
    public static String getDeviceModel() {
        if(StringUtil.isEmpty(mDeviceModel)){
            try {
                mDeviceModel = URLEncoder.encode(android.os.Build.MODEL,"UTF-8");
            } catch (UnsupportedEncodingException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return mDeviceModel;
    }

    /**
     * According to phone resolution height
     */
    public static int getHeightPixels(Context context) {
        DisplayMetrics dm = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(dm);
        return dm.heightPixels;
    }

    /**
     * According to phone resolution Width
     */
    public static int getWidthPixels(Context context) {
        DisplayMetrics dm = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(dm);
        return dm.widthPixels;
    }

    /**
     * 获得设备的横向dpi
     */
    public static float getDeviceScreenDpi(Context context) {
        DisplayMetrics dm = new DisplayMetrics();
        dm = context.getApplicationContext().getResources().getDisplayMetrics();
        return dm.densityDpi;
    }

}
