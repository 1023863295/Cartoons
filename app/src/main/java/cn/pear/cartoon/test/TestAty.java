package cn.pear.cartoon.test;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewTreeObserver;
import android.webkit.WebView;
import android.widget.ImageButton;
import android.widget.Toast;

import cn.pear.barcodescanner.CaptureActivity;
import cn.pear.cartoon.R;
import cn.pear.cartoon.base.BaseAty;
import cn.pear.cartoon.global.Constants;
import cn.pear.cartoon.jsInterface.JsCallNative;
import cn.pear.cartoon.tools.WebConfig;
import cn.pear.cartoon.ui.DetailAty;
import cn.pear.cartoon.view.PopWindowTools;

import static cn.pear.cartoon.test.PopTextHelper.TEXT_BTN_CN;
import static cn.pear.cartoon.test.PopTextHelper.TEXT_BTN_COM;
import static cn.pear.cartoon.test.PopTextHelper.TEXT_BTN_DIAN;
import static cn.pear.cartoon.test.PopTextHelper.TEXT_BTN_NET;
import static cn.pear.cartoon.test.PopTextHelper.TEXT_BTN_XIEXIAN;
/**
 * Created by liuliang on 2017/6/30.
 */

public class TestAty extends BaseAty implements View.OnClickListener{
    public static final int PERMISSION_QR_CODE=100;
    public static final int START_QR_CODE_ACTIVITY=200;


    private WebView mwebview;
    public BrowserSearchView browserSearchView;
    public PopTextHelper popTextHelper;
    public InputAssistView inputAssistView;


    public View linearBottomBar;
    private ImageButton imgBtnBack;
    private ImageButton imgBtnNext;
    private ImageButton imgBtnTool;
    private ImageButton imgBtnHome;


    private Boolean SoftInputState = false; //软键盘状态

    private TestWebViewClient webViewClient;
    private TestWeChromeClient webChromeClient;
    private JsCallNative jsCallNative;

    private PopWindowTools popWindowTools;

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
        webViewClient = new TestWebViewClient(this);
        mwebview.setWebViewClient(webViewClient);

        webChromeClient = new TestWeChromeClient(this);
        mwebview.setWebChromeClient(webChromeClient);

        jsCallNative = new JsCallNative(this);
        mwebview.addJavascriptInterface(jsCallNative,"android");




        browserSearchView = (BrowserSearchView)findViewById(R.id.test_search_view);
        browserSearchView.setActivity(this);

        inputAssistView = (InputAssistView)findViewById(R.id.test_input_assit);
        inputAssistView.setActivity(this);

        linearBottomBar = findViewById(R.id.test_bottom_bar_view);
        imgBtnBack = (ImageButton)linearBottomBar.findViewById(R.id.bottom_btn_back);
        imgBtnBack.setOnClickListener(this);
        imgBtnBack.setEnabled(false);
        imgBtnNext = (ImageButton)linearBottomBar.findViewById(R.id.bottom_btn_next);
        imgBtnNext.setOnClickListener(this);
        imgBtnNext.setEnabled(false);
        imgBtnTool = (ImageButton)linearBottomBar.findViewById(R.id.bottom_btn_tools);
        imgBtnTool.setOnClickListener(this);
        imgBtnHome = (ImageButton)linearBottomBar.findViewById(R.id.bottom_btn_home);
        imgBtnHome.setOnClickListener(this);

        setImmListener();
        popTextHelper = new PopTextHelper(this, mHandler);

        popWindowTools = new PopWindowTools(this,imgBtnBack);

    }

    @Override
    protected void afterViewInit() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bottom_btn_back:
                if (mwebview.canGoBack()){
                    mwebview.goBack();
                }
                updateBtnNext(true);
                break;
            case R.id.bottom_btn_next:
                if (mwebview.canGoForward()){
                    mwebview.goForward();
                }
                updateBtnBack(true);
                break;
            case R.id.bottom_btn_tools:
                popWindowTools.show();
                break;
            case R.id.bottom_btn_home:
                goHome();
                break;
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == START_QR_CODE_ACTIVITY && resultCode == RESULT_OK){
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

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == PERMISSION_QR_CODE){
            //摄像头权限
            int p=0;
            for(int i=0;i<grantResults.length;i++){
                if(grantResults[i]!= PackageManager.PERMISSION_GRANTED){
                    p++;
                }
            }
            if(p>0){
                Toast.makeText(this, R.string.need_permission, Toast.LENGTH_LONG).show();
            }else {
                Toast.makeText(this,"扫描二维码",Toast.LENGTH_SHORT).show();
                Intent intent =new Intent(this, CaptureActivity.class);
                startActivityForResult(intent,START_QR_CODE_ACTIVITY);
            }

        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
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

    private long lastBackPress;
    @Override
    public void onBackPressed() {
        if (mwebview.canGoBack()){
            mwebview.goBack();
            updateBtnNext(true);
            return;
        }else{

            if (System.currentTimeMillis() - lastBackPress < 2000) {
                super.onBackPressed();
            } else {
                lastBackPress = System.currentTimeMillis();
                Toast.makeText(this, "再按一次退出", Toast.LENGTH_SHORT).show();
            }
        }
    }

    //主页
    private void goHome(){
        mwebview.clearHistory();
        mwebview.loadUrl(Constants.URL_HOST);
        browserSearchView.editTextUrl.setText("");
        browserSearchView.editTextUrl.clearFocus();
        browserSearchView.setEditState(false); //上面方法有时候监听不到
        updateBtnBack(false);
        updateBtnNext(false);
    }

    public void updateBtnBack(boolean canGoback){
        if(canGoback){
            imgBtnBack.setEnabled(true);
            imgBtnBack.setImageResource(R.drawable.home_1);
        }else {
            imgBtnBack.setEnabled(false);
            imgBtnBack.setImageResource(R.drawable.home_1_back);
        }
    }

    public void updateBtnNext(boolean canGoForward){
        if(canGoForward){
            imgBtnNext.setEnabled(true);
            imgBtnNext.setImageResource(R.drawable.home_2_b);
        }else {
            imgBtnNext.setEnabled(false);
            imgBtnNext.setImageResource(R.drawable.home_2_next);
        }
    }

    public synchronized void refreshBackForwardViews() {
        boolean wvShow = mwebview.getVisibility() == View.VISIBLE;
        boolean forward = mwebview.canGoForward();

        if (wvShow){
            updateBtnBack(true);
        }else{
            updateBtnBack(false);
        }

        if (wvShow){
            updateBtnNext(forward);
        }else if(mwebview.getUrl() != null && !"".equals(mwebview.getUrl())){
            updateBtnNext(true);
        }else{
            updateBtnNext(false);
        }
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
