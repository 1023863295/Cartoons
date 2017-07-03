package cn.pear.cartoon.fragment;

import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import cn.pear.cartoon.R;
import cn.pear.cartoon.global.Constants;
import cn.pear.cartoon.jsInterface.JsCallNative;
import cn.pear.cartoon.jsInterface.JsGetPhoneInfoInterface;
import cn.pear.cartoon.tools.WebConfig;
import cn.pear.cartoon.ui.DetailAty;
import cn.shpear.ad.sdk.JavaScriptAdSupport;

/**
 * Created by liuliang on 2017/6/20.
 */

public class MainFragment extends Fragment implements FragmentBackHandler{
    private View view;
    private WebView mWebview;
    private JavaScriptAdSupport javaScriptAdSupport;
    private JsGetPhoneInfoInterface jsGetPhoneInfoInterface;
    private JsCallNative jsCallNative;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = View.inflate(getActivity(), R.layout.fragmneg_main_layout,null);
        if (getArguments() != null) {
        }
        initView();
        return view;
    }

    private void initView(){
        mWebview = (WebView)view.findViewById(R.id.main_webview);
       mWebview.setWebViewClient(new WebViewClient(){
           @Override
           public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
               return super.shouldOverrideUrlLoading(view, request);
           }
       });
        mWebview.loadUrl(Constants.URL_HOST);
        WebConfig.setDefaultConfig(mWebview,getActivity());

//        jsGetPhoneInfoInterface = new JsGetPhoneInfoInterface(getActivity());
//        mWebview.addJavascriptInterface(jsGetPhoneInfoInterface,"android");

        jsCallNative = new JsCallNative(getActivity());
        mWebview.addJavascriptInterface(jsCallNative,"android");
        Intent intent = new Intent(getActivity(), DetailAty.class);
        PendingIntent pi = PendingIntent.getActivity(getActivity(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        javaScriptAdSupport = new JavaScriptAdSupport(mWebview, pi);
    }

    @Override
    public boolean onBackPressed() {
        if (mWebview.canGoBack()){
            mWebview.goBack();
            return true;
        }else{
            return BackHandlerHelper.handleBackPress(this);
        }
    }
}
