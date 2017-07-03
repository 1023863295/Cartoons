package cn.pear.cartoon.test;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.ViewTreeObserver;
import android.webkit.WebView;

import cn.pear.cartoon.R;
import cn.pear.cartoon.base.BaseAty;
import cn.pear.cartoon.components.DetailWeChromeClient2;
import cn.pear.cartoon.components.DetailWebViewClient;
import cn.pear.cartoon.global.Constants;
import cn.pear.cartoon.tools.WebConfig;
import cn.pear.cartoon.ui.DetailAty;
import cn.pear.cartoon.ui.HomeActivity;

import static cn.pear.cartoon.test.PopTextHelper.TEXT_BTN_CN;
import static cn.pear.cartoon.test.PopTextHelper.TEXT_BTN_COM;
import static cn.pear.cartoon.test.PopTextHelper.TEXT_BTN_DIAN;
import static cn.pear.cartoon.test.PopTextHelper.TEXT_BTN_NET;
import static cn.pear.cartoon.test.PopTextHelper.TEXT_BTN_XIEXIAN;

/**
 * Created by liuliang on 2017/6/30.
 */

public class TestAty extends BaseAty implements View.OnClickListener{
    private WebView mwebview;
    public BrowserSearchView browserSearchView;
    public PopTextHelper popTextHelper;
    public InputAssistView inputAssistView;


    private Boolean SoftInputState = false; //软键盘状态

    private DetailWebViewClient webViewClient;
    private DetailWeChromeClient2 webChromeClient;

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case TEXT_BTN_XIEXIAN:
                    browserSearchView.editTextUrl.append(popTextHelper.btn_xiexian.getText().toString());
                    break;
                case TEXT_BTN_DIAN:
                    browserSearchView.editTextUrl.append(popTextHelper.btn_dian.getText().toString());
                    break;
                case TEXT_BTN_COM:
                    browserSearchView.editTextUrl.append(popTextHelper.btn_com.getText().toString());
                    break;
                case TEXT_BTN_CN:
                    browserSearchView.editTextUrl.append(popTextHelper.btn_cn.getText().toString());
                    break;
                case TEXT_BTN_NET:
                    browserSearchView.editTextUrl.append(popTextHelper.btn_net.getText().toString());
                    break;

            }
        }
    };

    @Override
    protected void initData() {

    }

    @Override
    protected int getLayoutView() {
        return R.layout.aty_test;
    }

    @Override
    protected void initView() {
        mwebview = (WebView)findViewById(R.id.test_webview);
        WebConfig.setDefaultConfig(mwebview,this);
        mwebview.loadUrl(Constants.URL_HOST);
        webViewClient = new DetailWebViewClient();
        mwebview.setWebViewClient(webViewClient);

        webChromeClient = new DetailWeChromeClient2();
        mwebview.setWebChromeClient(webChromeClient);




        browserSearchView = (BrowserSearchView)findViewById(R.id.test_search_view);
        browserSearchView.setActivity(this);

        inputAssistView = (InputAssistView)findViewById(R.id.test_input_assit);
        inputAssistView.setActivity(this);

        setImmListener();
        popTextHelper = new PopTextHelper(this, mHandler);

    }

    @Override
    protected void afterViewInit() {

    }

    @Override
    public void onClick(View v) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == HomeActivity.START_QR_CODE_ACTIVITY && resultCode == RESULT_OK){
            Bundle b2 = data.getExtras();
            if(b2!=null){
                String result =b2.getString("result");
                if(result.startsWith("http")){
                    if(result.endsWith(".apk")){
                        //--检测url中有apk则下载，如果手机又此apk，自动打开
                    }else {
                        Intent intentDetail = new Intent(TestAty.this,DetailAty.class);
                        intentDetail.putExtra("url",result);
                        startActivity(intentDetail);
                        return;
                    }
                }
            }
        }
    }



    /**
     * 添加软键盘弹出或者隐藏的监听
     */
    public void setImmListener(){
        getWindow().getDecorView().getViewTreeObserver().
                addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        Rect r = new Rect();
                        getWindow().getDecorView().getWindowVisibleDisplayFrame(r);
                        int height = getWindow().getDecorView().getHeight();
                        int keyboardheight = height - r.bottom ;
                        // 保存通知栏高度
                        if (300 > keyboardheight) {//隐藏键盘状态
                            SoftInputState = false;
                            popTextHelper.dismiss();
                        }
                        // 保存键盘高度
                        else {//弹出键盘状态
                            SoftInputState = true;
                            popTextHelper.show(mwebview,keyboardheight);
                        }
                    }
                });
    }

    @Override
    public void onBackPressed() {
        if (mwebview.canGoBack()){
            mwebview.goBack();
            return;
        }
        super.onBackPressed();
    }

    public WebView getMwebview() {
        return mwebview;
    }

    public Handler getmHandler() {
        return mHandler;
    }

    public Boolean getSoftInputState() {
        return SoftInputState;
    }
}
