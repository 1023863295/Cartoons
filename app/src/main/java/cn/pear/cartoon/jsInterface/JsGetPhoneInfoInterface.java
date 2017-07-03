package cn.pear.cartoon.jsInterface;

import android.app.Activity;
import android.os.Build;
import android.webkit.JavascriptInterface;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.lang.ref.WeakReference;
import java.net.URLEncoder;

import cn.pear.cartoon.global.Constants;
import cn.pear.cartoon.tools.DeviceInfoUtil;
import cn.pear.cartoon.tools.NetworkUtil;

/**
 * Created by liuliang on 2017/6/22.
 */

public class JsGetPhoneInfoInterface {
    private WeakReference<Activity> reference;
    private Activity mActivity;

    public JsGetPhoneInfoInterface(Activity activity){
        reference=new WeakReference<Activity>(activity);
        this.mActivity = reference.get();
    }

    //获取一些硬件信息,广告需要,from 为0 表示列表页，详情页为1
    @JavascriptInterface
    public String getQQAdNeedInfo(int type){
        JSONObject jsonObject = new JSONObject();
        if (type == 0){
            try {
                jsonObject.put("ip", DeviceInfoUtil.getIpV4Address(mActivity));
                jsonObject.put("devicetype",4);
                jsonObject.put("os","Android");
                jsonObject.put("osv",  Build.VERSION.RELEASE );
                jsonObject.put("did", DeviceInfoUtil.getIMEI(mActivity));
                jsonObject.put("dpid", DeviceInfoUtil.getAndroidId(mActivity));
                jsonObject.put("mac", DeviceInfoUtil.getDeviceMacAddress(mActivity));
//            jsonObject.put("ua", System.getProperty("http.agent"));
                jsonObject.put("ua", URLEncoder.encode(Constants.WEB_SETTING_UA,"utf-8"));
                jsonObject.put("make", android.os.Build.MANUFACTURER);
                jsonObject.put("model", DeviceInfoUtil.getDeviceModel());
                jsonObject.put("h", DeviceInfoUtil.getHeightPixels(mActivity));
                jsonObject.put("w", DeviceInfoUtil.getWidthPixels(mActivity));
                jsonObject.put("ppi", DeviceInfoUtil.getDeviceScreenDpi(mActivity));
                jsonObject.put("carrier", NetworkUtil.getCarrier(mActivity));
                jsonObject.put("connectiontype", NetworkUtil.getNetWorkClass(mActivity));
                jsonObject.put("screen_orientation", "1");
            }
            catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return jsonObject.toString();
    }
}
