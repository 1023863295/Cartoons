//package cn.pear.cartoon.ui;
//
//import android.annotation.TargetApi;
//import android.app.PendingIntent;
//import android.content.Intent;
//import android.content.pm.PackageManager;
//import android.graphics.Rect;
//import android.os.Bundle;
//import android.os.Handler;
//import android.os.Message;
//import android.support.annotation.NonNull;
//import android.support.annotation.Nullable;
//import android.support.v4.app.Fragment;
//import android.support.v4.content.ContextCompat;
//import android.text.Editable;
//import android.text.TextUtils;
//import android.text.TextWatcher;
//import android.view.KeyEvent;
//import android.view.View;
//import android.view.ViewTreeObserver;
//import android.view.inputmethod.EditorInfo;
//import android.webkit.WebResourceRequest;
//import android.webkit.WebView;
//import android.webkit.WebViewClient;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.ImageButton;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
//import android.widget.RelativeLayout;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import java.io.UnsupportedEncodingException;
//
//import cn.pear.barcodescanner.CaptureActivity;
//import cn.pear.cartoon.R;
//import cn.pear.cartoon.base.BaseAty;
//import cn.pear.cartoon.global.Constants;
//import cn.pear.cartoon.jsInterface.JsCallNative;
//import cn.pear.cartoon.test.TestAty;
//import cn.pear.cartoon.tools.PermissionUtil;
//import cn.pear.cartoon.tools.StringUtil;
//import cn.pear.cartoon.tools.SystemTool;
//import cn.pear.cartoon.tools.ToastUtil;
//import cn.pear.cartoon.tools.WebConfig;
//import cn.pear.cartoon.view.PopWindowTools;
//import cn.shpear.ad.sdk.JavaScriptAdSupport;
//
//public class HomeActivity extends BaseAty implements View.OnClickListener{
//    public static final int PERMISSION_QR_CODE=100;
//
//    public static final int START_QR_CODE_ACTIVITY=200;
//
//
//    public static final int REFRESH_URLEDITTEXT=300;
//
//    private LinearLayout linearBottom;
//    private ImageButton imgBtnback;
//    private ImageButton imgBtnNext;
//    private ImageButton imgBtnTools;
//    private ImageButton imgBtnHome;
//
//
//    private View viewInputAssit; // 输入提示view
//
//
//    private  View viewSearch;
//    private RelativeLayout rlEditBg;
//    private EditText editTextSearch;
//    private RelativeLayout rlSearchDelete; //删除
//    private ImageButton imgBtnScan; //扫描
//    private TextView textCaccel; //取消
//    private ImageView imageSearch;
//
//    private String keyWords = "";
//
//    private WebView mWebview;
//    private JsCallNative jsCallNative;
//    private JavaScriptAdSupport javaScriptAdSupport;
//
//
//    private PopWindowTools popWindowTools;
//
//    @Override
//    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//    }
//
//    @Override
//    protected void initData() {
//    }
//
//    @Override
//    protected int getLayoutView() {
//        return R.layout.activity_main;
//    }
//
//
//    @Override
//    protected void initView() {
//        viewSearch = findViewById(R.id.home_top_search);
//        rlEditBg = (RelativeLayout)viewSearch.findViewById(R.id.search_rl_bg_edit);
//        imageSearch = (ImageView)viewSearch.findViewById(R.id.search_url_img);
//        rlSearchDelete = (RelativeLayout)viewSearch.findViewById(R.id.search_rl_clear);
//        rlSearchDelete.setOnClickListener(this);
//        imgBtnScan = (ImageButton)viewSearch.findViewById(R.id.search_btn_scan);
//        imgBtnScan.setOnClickListener(this);
//        textCaccel = (TextView)viewSearch.findViewById(R.id.search_text_cancel);
//        textCaccel.setOnClickListener(this);
//
//        editTextSearch = (EditText)viewSearch.findViewById(R.id.search_edittext);
//        editTextSearch.setOnKeyListener(new View.OnKeyListener() {
//            @Override
//            public boolean onKey(View v, int keyCode, KeyEvent event) {
//                if (keyCode == KeyEvent.KEYCODE_ENTER) {
//                    //当软键盘弹出时BACK置顶为最小化当前软键盘
//                    String url = editTextSearch.getText().toString();
//                    if (!TextUtils.isEmpty(url) && !url.replace(" ", "").equals("")) {
//                        editTextSearch.clearFocus();
//                    }
//                    return true;
//                }
//                return false;
//            }
//        });
//        editTextSearch.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View v, boolean hasFocus) {
//                if (hasFocus){
//                    linearBottom.setVisibility(View.GONE);
//                    viewInputAssit.setVisibility(View.VISIBLE);
//                    textCaccel.setVisibility(View.VISIBLE);
//                    rlEditBg.setBackgroundResource(R.drawable.search_corner_white);
//                    imageSearch.setImageResource(R.drawable.home_14_gray);
//                    imgBtnScan.setBackgroundResource(R.drawable.home_9);
//                    editTextSearch.setTextColor(ContextCompat.getColor(HomeActivity.this,R.color.text_gray));
//                }else{
//                    linearBottom.setVisibility(View.VISIBLE);
//                    viewInputAssit.setVisibility(View.GONE);
//                    rlEditBg.setBackgroundResource(R.drawable.search_corner_white_gray);
//                    imageSearch.setImageResource(R.drawable.home_14_white);
//                    imgBtnScan.setBackgroundResource(R.drawable.home_9_white);
//                    editTextSearch.setTextColor(ContextCompat.getColor(HomeActivity.this,R.color.white));
//                    editTextSearch.setText("");
//                    imgBtnScan.setVisibility(View.VISIBLE);
//                    rlSearchDelete.setVisibility(View.INVISIBLE);
//                }
//           }
//        });
//        editTextSearch.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//                if(editTextSearch.hasFocus()){
//                    aferTextChange();
//                }
//            }
//        });
//
//
//        editTextSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
//            @Override
//            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
//                if (actionId == EditorInfo.IME_ACTION_SEARCH){
//                    //搜索
//                    goSearch(editTextSearch.getText().toString());
//                    return true;
//                }
//                return false;
//            }
//        });
//
//
//        linearBottom = (LinearLayout)findViewById(R.id.bottom_linear);
//        imgBtnback = (ImageButton)findViewById(R.id.bottom_btn_back);
//        imgBtnback.setOnClickListener(this);
//        imgBtnNext = (ImageButton)findViewById(R.id.bottom_btn_next);
//        imgBtnNext.setOnClickListener(this);
//        imgBtnTools = (ImageButton)findViewById(R.id.bottom_btn_tools);
//        imgBtnTools.setOnClickListener(this);
//        imgBtnHome = (ImageButton)findViewById(R.id.bottom_btn_home);
//        imgBtnHome.setOnClickListener(this);
//
//        mWebview = (WebView)findViewById(R.id.home_webview);
//
//        popWindowTools = new PopWindowTools(this,imgBtnback);
//        initInputAssit();
//
//        //添加软键盘弹出或者隐藏的监听
//        setImmListener();
//
//    }
//
//    @Override
//    protected void afterViewInit() {
//        mWebview.loadUrl(Constants.URL_HOST);
//        WebConfig.setDefaultConfig(mWebview,this);
//
//        jsCallNative = new JsCallNative(this);
//        mWebview.addJavascriptInterface(jsCallNative,"android");
//        Intent intent = new Intent(this, DetailAty.class);
//        PendingIntent pi = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
//        javaScriptAdSupport = new JavaScriptAdSupport(mWebview, pi);
//        mWebview.setWebViewClient(new WebViewClient(){
//
//            // 拦截替换网络请求数据,  从API 21开始引入
//            @TargetApi(21)
//            @Override
//            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
//                Intent intent = new Intent(HomeActivity.this,DetailAty.class);
//                intent.putExtra("url",request.getUrl().toString());
//                startActivity(intent);
//                return true;
//            }
//
//            // 拦截替换网络请求数据,  API 11开始引入，API 21弃用
//            @Override
//            public boolean shouldOverrideUrlLoading(WebView view, String url) {
//                Intent intent = new Intent(HomeActivity.this,DetailAty.class);
//                intent.putExtra("url",url);
//                startActivity(intent);
//                return true;
//            }
//        });
//    }
//
//    @Override
//    public void onClick(View v) {
//        switch (v.getId()){
//            case R.id.bottom_btn_back:
//                break;
//            case R.id.bottom_btn_next:
//                Intent intentTest = new Intent(this, TestAty.class);
//                startActivity(intentTest);
////                changeFragment(rightFragment);
//                break;
//            case R.id.bottom_btn_tools:
//                popWindowTools.show();
//                break;
//            case R.id.bottom_btn_home:
//                goHome();
//                break;
//            case R.id.search_btn_scan:
//                openScan();
//                break;
//            case R.id.search_rl_clear:
//                ToastUtil.showLongToast(this,"clear");
//                editTextSearch.setText("");
//                break;
//            case R.id.search_text_cancel:
//                linearBottom.setVisibility(View.VISIBLE);
//                textCaccel.setVisibility(View.GONE);
//                viewInputAssit.setVisibility(View.GONE);
//                editTextSearch.setFocusable(false);
//                editTextSearch.setFocusableInTouchMode(true);//设置触摸聚焦
//                SystemTool.hideKeyBoard(this,editTextSearch); //隐藏键盘
//                break;
//            case R.id.input_btn_www:
//                keyWords = editTextSearch.getText().toString();
//                editTextSearch.setText(keyWords+btnWWW.getText().toString());
//                editTextSearch.setSelection(editTextSearch.getText().length());
//                break;
//            case R.id.input_btn_dot:
//                keyWords = editTextSearch.getText().toString();
//                editTextSearch.setText(keyWords+btnDot.getText().toString());
//                editTextSearch.setSelection(editTextSearch.getText().length());
//                break;
//            case R.id.input_btn_com:
//                keyWords = editTextSearch.getText().toString();
//                editTextSearch.setText(keyWords+btnCom.getText().toString());
//                editTextSearch.setSelection(editTextSearch.getText().length());
//                break;
//            case R.id.input_btn_cn:
//                keyWords = editTextSearch.getText().toString();
//                editTextSearch.setText(keyWords+btnCn.getText().toString());
//                editTextSearch.setSelection(editTextSearch.getText().length());
//                break;
//            case R.id.input_btn_net:
//                keyWords = editTextSearch.getText().toString();
//                editTextSearch.setText(keyWords+btnNet.getText().toString());
//                editTextSearch.setSelection(editTextSearch.getText().length());
//                break;
//        }
//    }
//
//    private void changeFragment(Fragment fragment){
////        fm = getSupportFragmentManager();
////        ft = fm.beginTransaction();
////        if (fragment instanceof  RightFragment){
////            ft.setCustomAnimations(R.anim.slide_right_in,R.anim.slide_left_out);
////        }else{
////            ft.setCustomAnimations(R.anim.slide_left_in,R.anim.slide_right_out);
////        }
////        ft.replace(R.id.home_linear_container,fragment);
////        ft.commit();
//    }
//
//    //扫描
//    private void openScan(){
//        //--先检测权限
//        String[] strings= PermissionUtil.getInstance().checkCameraPermission(this);
//        if(strings!=null&&strings.length>0){
//            PermissionUtil.getInstance().requestNeedPermission(this,strings,PERMISSION_QR_CODE);
//        }else {
//            Toast.makeText(this,"扫描二维码",Toast.LENGTH_SHORT).show();
//            Intent intent =new Intent(this, CaptureActivity.class);
//            this.startActivityForResult(intent,START_QR_CODE_ACTIVITY);
//        }
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if (requestCode == START_QR_CODE_ACTIVITY){ //二维码扫码
//            Bundle b2 = data.getExtras();
//            if(b2!=null){
//                String result =b2.getString("result");
//                if(result.startsWith("http")){
//                    if(result.endsWith(".apk")){
//                        //--检测url中有apk则下载，如果手机又此apk，自动打开
//
//                    }else {
//                        Intent intentDetail = new Intent(HomeActivity.this,DetailAty.class);
//                       intentDetail.putExtra("url",result);
//                        startActivity(intentDetail);
//                        return;
//                    }
//                }
//            }
//        }
//        super.onActivityResult(requestCode, resultCode, data);
//    }
//
//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//       if (requestCode == PERMISSION_QR_CODE){
//           //摄像头权限
//           int p=0;
//           for(int i=0;i<grantResults.length;i++){
//               if(grantResults[i]!= PackageManager.PERMISSION_GRANTED){
//                   p++;
//               }
//           }
//           if(p>0){
//               Toast.makeText(this, R.string.need_permission, Toast.LENGTH_LONG).show();
//           }else {
//               Toast.makeText(this,"扫描二维码",Toast.LENGTH_SHORT).show();
//               Intent intent =new Intent(this, CaptureActivity.class);
//               startActivityForResult(intent,START_QR_CODE_ACTIVITY);
//           }
//
//       }
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//    }
//
//
//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        if (keyCode == KeyEvent.KEYCODE_BACK){
//            if (popWindowTools.isShow()){
//                popWindowTools.dismiss();
//                return true;
//            }else if(viewInputAssit.isShown()){
//                viewInputAssit.setVisibility(View.GONE);
//                mWebview.setVisibility(View.VISIBLE);
//                editTextSearch.setFocusable(false);
//                editTextSearch.setFocusableInTouchMode(true);//设置触摸聚焦
//                textCaccel.setVisibility(View.GONE);
//            }
//        }
//        return super.onKeyDown(keyCode, event);
//    }
//
//    private long lastBackPress;
//    @Override
//    public void onBackPressed() {
//        if (mWebview.canGoBack()){
//            mWebview.goBack();
//        }else{
//            if (System.currentTimeMillis() - lastBackPress < 2000) {
//                super.onBackPressed();
//            } else {
//                lastBackPress = System.currentTimeMillis();
//                Toast.makeText(this, "再按一次退出", Toast.LENGTH_SHORT).show();
//            }
//        }
//        //用于fragment返回键
////        if (!BackHandlerHelper.handleBackPress(this)) {
////            if (System.currentTimeMillis() - lastBackPress < 2000) {
////                super.onBackPressed();
////            } else {
////                lastBackPress = System.currentTimeMillis();
////                Toast.makeText(this, "再按一次退出", Toast.LENGTH_SHORT).show();
////            }
////        }
//    }
//
//    //主页
//    private void goHome(){
//        mWebview.loadUrl(Constants.URL_HOST);
//        mWebview.clearHistory();
//        editTextSearch.setText("");
//    }
//
//    private void aferTextChange(){
//        final String keyWord = editTextSearch.getText().toString();
//        if (!StringUtil.isEmpty(keyWord)){
//            rlSearchDelete.setVisibility(View.VISIBLE);
//            imgBtnScan.setVisibility(View.GONE);
//        }else{
//            rlSearchDelete.setVisibility(View.INVISIBLE);
//            imgBtnScan.setVisibility(View.VISIBLE);
//        }
//
//        if(!(keyWord.equals(mWebview.getTitle())||keyWord.equals(mWebview.getUrl()))){
//            new Thread(){
//                @Override
//                public void run() {
//                    Message msg = Message.obtain();
//                    msg.obj = keyWord;
//                    msg.what = REFRESH_URLEDITTEXT;
//                    mHandler.sendMessage(msg);
//                }
//            }.start();
//        }
//    }
//
//    private void goSearch(String strKey){
//        String targetUrl = "";
//        String part = Constants.URL_DEFAULT_FROM;
//        //拼接百度搜索字符串
//        try {
//            targetUrl = Constants.URL_SEARCH_FROM+ part+ "/s?word="+
//                    new String(strKey.getBytes("UTF-8"), "UTF-8").replace("&", "%26").replace("#","%23").replace("%","%25");
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
//        mWebview.loadUrl(targetUrl);
//
//
//        linearBottom.setVisibility(View.VISIBLE);
//        textCaccel.setVisibility(View.GONE);
//        viewInputAssit.setVisibility(View.GONE);
//        editTextSearch.setFocusable(false);
//        editTextSearch.setFocusableInTouchMode(true);//设置触摸聚焦
//        SystemTool.hideKeyBoard(this,editTextSearch); //隐藏键盘
//        editTextSearch.setText(strKey);
//
//    }
//
//    private Handler mHandler = new Handler(){
//        @Override
//        public void handleMessage(Message msg) {
//            super.handleMessage(msg);
//            switch (msg.what){
//                case REFRESH_URLEDITTEXT:
////                    adapter.setKeyWord(msg.obj.toString());
////                    tabView.getUrlSuggestionList().setAdapter(adapter);
////                    adapter.changeCursor(c);
//////                        adapter.notifyDataSetChanged();
////                    if(TextUtils.isEmpty(tabView.getUrl())&&adapter.getCount()>0){
////                        tabView.getDeleteSearch().setVisibility(View.VISIBLE);
////                    }else {
////                        tabView.getDeleteSearch().setVisibility(View.GONE);
////                    }
//                    break;
//            }
//        }
//    };
//
//
//    private Button btnWWW;
//    private Button btnDot;
//    private Button btnCom;
//    private Button btnCn;
//    private Button btnNet;
//    private LinearLayout linearHelper;
//
//    private void initInputAssit(){
//        viewInputAssit = findViewById(R.id.main_input_assit);
//
//        linearHelper = (LinearLayout)viewInputAssit.findViewById(R.id.input_linear_helper);
//        btnWWW = (Button)viewInputAssit.findViewById(R.id.input_btn_www);
//        btnDot = (Button)viewInputAssit.findViewById(R.id.input_btn_dot);
//        btnCom = (Button)viewInputAssit.findViewById(R.id.input_btn_com);
//        btnCn = (Button)viewInputAssit.findViewById(R.id.input_btn_cn);
//        btnNet = (Button)viewInputAssit.findViewById(R.id.input_btn_net);
//        btnWWW.setOnClickListener(this);
//        btnDot.setOnClickListener(this);
//        btnCom.setOnClickListener(this);
//        btnCn.setOnClickListener(this);
//        btnNet.setOnClickListener(this);
//    }
//
//    /**
//     * 添加软键盘弹出或者隐藏的监听
//     */
//    public void setImmListener(){
//        getWindow().getDecorView().getViewTreeObserver().
//                addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
//                    @Override
//                    public void onGlobalLayout() {
//                        Rect r = new Rect();
//                        getWindow().getDecorView().getWindowVisibleDisplayFrame(r);
//                        int height = getWindow().getDecorView().getHeight();
//                        int keyboardheight = height - r.bottom ;
//                        // 保存通知栏高度
//                        if (300 > keyboardheight) {//隐藏键盘状态
//                            linearHelper.setVisibility(View.GONE);
//                        }
//                        // 保存键盘高度
//                        else {//弹出键盘状态
//                          linearHelper.setVisibility(View.VISIBLE);
//                        }
//                    }
//                });
//    }
//}
