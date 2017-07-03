package cn.pear.cartoon.ui;

import android.view.View;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.ImageView;

import cn.pear.cartoon.R;
import cn.pear.cartoon.base.BaseAty;
import cn.pear.cartoon.components.DetailWeChromeClient;
import cn.pear.cartoon.components.DetailWebViewClient;
import cn.pear.cartoon.jsInterface.JsCallNative;
import cn.pear.cartoon.tools.WebConfig;

/**
 * Created by liuliang on 2017/6/22.
 */

public class DetailAty extends BaseAty implements View.OnClickListener{
    private WebView mWebview;
    private JsCallNative jsCallNative;
    private DetailWeChromeClient detailWeChromeClient;
    private DetailWebViewClient detailWebViewClient;

    private String stringUrl = "";
    private EditText editTextTitle;
    private ImageView imgRefresh;

    @Override
    protected void initData() {
        stringUrl = getIntent().getStringExtra("url");
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

        imgRefresh = (ImageView)findViewById(R.id.detail_img_refresh);
        imgRefresh.setOnClickListener(this);

        mWebview = (WebView)findViewById(R.id.detail_webview);
        jsCallNative = new JsCallNative(this);
        mWebview.addJavascriptInterface(jsCallNative,"android");
        WebConfig.setDefaultConfig(mWebview,this);

        detailWeChromeClient = new DetailWeChromeClient(editTextTitle);
        mWebview.setWebChromeClient(detailWeChromeClient);

        detailWebViewClient = new DetailWebViewClient();
        mWebview.setWebViewClient(detailWebViewClient);

        mWebview.loadUrl(stringUrl);

    }

    @Override
    protected void afterViewInit() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.detail_img_refresh:
                stringUrl = editTextTitle.getText().toString();
                if (!(stringUrl.contains("http://") || stringUrl.contains("https://"))) {
                    stringUrl = "http://" + stringUrl+"/";
                }
                mWebview.loadUrl(stringUrl);
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
