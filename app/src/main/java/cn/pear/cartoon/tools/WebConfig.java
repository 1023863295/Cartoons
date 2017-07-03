package cn.pear.cartoon.tools;

import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.WebSettings;
import android.webkit.WebView;

import cn.pear.cartoon.global.Constants;

/**
 * Created by liuliang on 2017/5/12.
 */

public class WebConfig {
    private static final String APP_CACHE_DIRNAME = "/webcache"; // web缓存目录

    public static void setDefaultConfig(WebView webView, final Context context){

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
            webView.setWebContentsDebuggingEnabled(true);
        }

        WebSettings settings = webView.getSettings();
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        settings.setLoadWithOverviewMode(true);

        //将图片调整到适合webview的大小
        settings.setUseWideViewPort(true);
        settings.setJavaScriptEnabled(true);
        //设置可以访问文件
        settings.setAllowFileAccess(true);
        settings.setAllowContentAccess(true);

        if (NetworkUtil.isNetworkAvailable(context)){
            settings.setCacheMode(WebSettings.LOAD_DEFAULT);
        }else{
            settings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        }
        settings.setDatabaseEnabled(true);
        String dbPath = context.getFilesDir().getAbsolutePath()+ APP_CACHE_DIRNAME;
        settings.setDatabasePath(dbPath);

//        设置WebView保存地理位置信息数据路径，指定的路径Application具备写入权限
        settings.setGeolocationDatabasePath(context.getApplicationContext().getDir("database", Context.MODE_PRIVATE).getPath());
        settings.setGeolocationEnabled(false);

        settings.setDomStorageEnabled(true);
//        String appCaceDir = context.getDir( "cache" , Context.MODE_PRIVATE).getPath();
        settings.setAppCachePath(dbPath);

        Log.i("test","dbPath:"+dbPath);

//		settings.setSupportMultipleWindows(true);


        settings.setLoadsImagesAutomatically(true);
        settings.setUserAgentString(settings.getUserAgentString()+Constants.WEB_SETTING_UA);
        settings.setAppCacheEnabled(true);
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        boolean acceptCookie = true;
//        boolean acceptCookie = Controller.getInstance().getPreferences().getBoolean(Constants.PREFERENCES_BROWSER_ENABLE_COOKIES, true);
        CookieManager.getInstance().setAcceptCookie(acceptCookie);
        settings.setSupportZoom(true);
//        boolean sysProxy = Controller.getInstance().getPreferences().getBoolean(Constants.PREFERENCES_BROWSER_ENABLE_PROXY_SETTINGS, false);
        boolean sysProxy = true;
        if (sysProxy) {
            ProxySettings.setSystemProxy(context);
        } else {
            ProxySettings.resetSystemProxy(context);
        }
        webView.requestFocus();
        webView.setLongClickable(true);
        webView.setScrollbarFadingEnabled(true);
        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        webView.setDrawingCacheEnabled(true);
        webView.setHorizontalScrollBarEnabled(false);
    }

}
