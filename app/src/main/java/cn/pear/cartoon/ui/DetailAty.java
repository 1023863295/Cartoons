package cn.pear.cartoon.ui;

import android.app.PendingIntent;
import android.content.Intent;
import android.view.View;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import cn.pear.cartoon.R;
import cn.pear.cartoon.base.BaseAty;
import cn.pear.cartoon.components.DetailWeChromeClient;
import cn.pear.cartoon.components.DetailWebViewClient;
import cn.pear.cartoon.jsInterface.JsCallNative;
import cn.pear.cartoon.tools.WebConfig;
import cn.shpear.ad.sdk.JavaScriptAdSupport;

/**
 * Created by liuliang on 2017/6/22.
 */

public class DetailAty extends BaseAty implements View.OnClickListener{
    private WebView mWebview;
    private JsCallNative jsCallNative;
    private DetailWeChromeClient detailWeChromeClient;
    private DetailWebViewClient detailWebViewClient;
    private JavaScriptAdSupport javaScriptAdSupport;


    private String stringUrl = "";
    private EditText editTextTitle;
    private ImageView imgRefresh;
    private TextView textTitle;

    @Override
    protected void initData() {
        stringUrl = getIntent().getStringExtra("to_url");
        if (!(stringUrl.contains("http://") || stringUrl.contains("https://"))) {
            stringUrl = "http://" + stringUrl+"/";
        }
    }

    @Override
    protected int getLayoutView() {
        return R.layout.aty_detail_layout;
    }

    @Override
    protected void initView() {
        editTextTitle =(EditText)findViewById(R.id.detail_edit_title);
        editTextTitle.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus){
                    editTextTitle.setText(mWebview.getUrl());
                }else{
                    editTextTitle.setText(mWebview.getTitle());
                }
            }
        });
        textTitle = (TextView)findViewById(R.id.detail_text_title);
        textTitle.setText(stringUrl);

        imgRefresh = (ImageView)findViewById(R.id.detail_img_refresh);
        imgRefresh.setOnClickListener(this);

        mWebview = (WebView)findViewById(R.id.detail_webview);
        jsCallNative = new JsCallNative(this);
        mWebview.addJavascriptInterface(jsCallNative,"android");
        WebConfig.setDefaultConfig(mWebview,this);

        detailWeChromeClient = new DetailWeChromeClient(textTitle);
        mWebview.setWebChromeClient(detailWeChromeClient);

        detailWebViewClient = new DetailWebViewClient();
        mWebview.setWebViewClient(detailWebViewClient);

        Intent intent = new Intent(this, DetailAty.class);
        PendingIntent pi = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        javaScriptAdSupport = new JavaScriptAdSupport(mWebview, pi);


        mWebview.loadUrl(stringUrl);

    }

    @Override
    protected void afterViewInit() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.detail_img_refresh:
//                stringUrl = editTextTitle.getText().toString();
//                if (!(stringUrl.contains("http://") || stringUrl.contains("https://"))) {
//                    stringUrl = "http://" + stringUrl+"/";
//                }
                mWebview.reload();
                break;
        }

    }

    @Override
    public void onBackPressed() {
        if (mWebview.canGoBack()){
            mWebview.goBack();
        }
        super.onBackPressed();
    }
}
