package cn.pear.cartoon.test;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.regex.Pattern;

import cn.pear.barcodescanner.CaptureActivity;
import cn.pear.cartoon.R;
import cn.pear.cartoon.adapter.UrlAdapter;
import cn.pear.cartoon.global.Constants;
import cn.pear.cartoon.tools.ApplicationUtils;
import cn.pear.cartoon.tools.PermissionUtil;
import cn.pear.cartoon.ui.HomeActivity;
import cn.pear.cartoon.view.EditTextPreIme;

import static cn.pear.cartoon.ui.HomeActivity.REFRESH_URLEDITTEXT;

/**
 * Created by liuliang on 2017/6/30.
 */

public class BrowserSearchView extends FrameLayout implements View.OnClickListener{
    private RelativeLayout searchRlBody;
    private ImageView searchUrlImgICon;

    private RelativeLayout rlDeleteAndRefresh;
    private RelativeLayout rlDelete;
    private RelativeLayout rlRefresh;
    public EditTextPreIme editTextUrl;
    private LinearLayout linearScan;
    private ImageButton imgBtnScan;
    private Button btnCancel;

    private String stringWord = "";
    private String stringUrl = "";

    private TestAty activity;
    InputMethodManager imm;

    UrlAdapter adapter;
    Handler handler;

    public void setActivity(TestAty activity) {
        this.activity = activity;
    }

    public BrowserSearchView(Context context) {
        this(context, null);
    }

    public BrowserSearchView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BrowserSearchView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        return false;
    }

    public void init(final Context context, AttributeSet attrs, int defStyleAttr) {
        View view = LayoutInflater.from(context).inflate(R.layout.browser_search_view_content, null);
        addView(view);
        searchRlBody = (RelativeLayout)view.findViewById(R.id.head_search_body);
        searchUrlImgICon = (ImageView)view.findViewById(R.id.search_url_img_icon);

        rlDeleteAndRefresh = (RelativeLayout)view.findViewById(R.id.delete_refresh_relative);
        rlDelete = (RelativeLayout)view.findViewById(R.id.search_rl_delete);
        rlDelete.setOnClickListener(this);
        rlRefresh = (RelativeLayout)view.findViewById(R.id.search_rl_refresh);
        rlRefresh.setOnClickListener(this);
        linearScan = (LinearLayout) view.findViewById(R.id.search_linearLayout_scan);
        linearScan.setOnClickListener(this);
        imgBtnScan = (ImageButton)view.findViewById(R.id.search_img_btn_scanBtn);
        imgBtnScan.setOnClickListener(this);
        btnCancel = (Button)view.findViewById(R.id.search_btn_go);
        btnCancel.setOnClickListener(this);
        editTextUrl = (EditTextPreIme)view.findViewById(R.id.search_edit_url);

        editTextUrl.setOnKeyBackListener(new EditTextPreIme.OnKeyBackListener() {
            @Override
            public void onKeyBack() {
                if( activity.getSoftInputState()){
                    imm = (InputMethodManager) editTextUrl.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(editTextUrl.getWindowToken(),0);
                }else {
                    editTextUrl.clearFocus();
                }
            }
        });

        editTextUrl.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(editTextUrl.hasFocus()){
                    aferTextChange();
                }
            }
        });

        editTextUrl.setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                synchronized (BrowserSearchView.this) {
                    String url = activity.getMwebview().getUrl();

                    if (hasFocus) {
                        setEditState(true);
                        aferTextChange();
                    } else {
                        setEditState(false);
                        imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                        activity.popTextHelper.dismiss();//因为监控小键盘弹出的监听有BUG 会执行多次 所以在这里隐藏texthelper

                        linearScan.setVisibility(View.VISIBLE);
                        rlDeleteAndRefresh.setVisibility(View.GONE);
                    }

                    if (InputAssistView.isSuggestion) {
                        InputAssistView.isSuggestion = false;
                    } else {
//                        args = new int[]{mod, visitMod};
                    }
//                    BrowserSearchView.this.lHandler.obtainMessage(MSG_MOD_CHANGE, args).sendToTarget();
                }
            }
        });

        editTextUrl.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH){
                    //搜索
                    goSearch(editTextUrl.getText().toString());
                    setSoftInputHide();
                    editTextUrl.clearFocus();

                    return true;
                }
                return false;
            }
        });

        handler= new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what){
                    case REFRESH_URLEDITTEXT:
                        activity.inputAssistView.setVisibility(View.VISIBLE);
                        activity.inputAssistView.deleteSearch.setVisibility(VISIBLE);
//                        if(TextUtils.isEmpty(tabView.getUrl())&&adapter.getCount()>0){
//                            tabView.getDeleteSearch().setVisibility(View.VISIBLE);
//                        }else {
//                            tabView.getDeleteSearch().setVisibility(View.GONE);
//                        }
                        break;
                }
            }
        };

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.search_rl_delete:
                delete();
                break;
            case R.id.search_rl_refresh:
                break;
            case R.id.search_linearLayout_scan:
            case R.id.search_img_btn_scanBtn:
                openScan();
                break;
            case R.id.search_btn_go:
                goOrCancel();
                break;
        }
    }

    private synchronized void aferTextChange(){
        final String keyWord= editTextUrl.getText().toString();
        if (TextUtils.isEmpty(keyWord.replace(" ",""))) {
            btnCancel.setText("取消");
            linearScan.setVisibility(View.VISIBLE);
            rlDelete.setVisibility(View.GONE);
            rlDelete.setEnabled(false);
            rlRefresh.setVisibility(View.GONE);
            rlRefresh.setEnabled(false);
            rlDeleteAndRefresh.setVisibility(View.GONE);
        }else{
            btnCancel.setText("前往");
            linearScan.setVisibility(View.GONE);
            rlDelete.setEnabled(true);
            rlDelete.setVisibility(View.VISIBLE);
            rlRefresh.setEnabled(true);
            rlRefresh.setVisibility(View.VISIBLE);
            rlDeleteAndRefresh.setVisibility(View.VISIBLE);
        }
        if(!(keyWord.equals(activity.getMwebview().getTitle())||keyWord.equals(activity.getMwebview().getUrl()))){

            Message msg = Message.obtain();
            msg.obj = keyWord;
            msg.what = REFRESH_URLEDITTEXT;
            handler.sendMessage(msg);

//            urlSuggestionTask=new AsyncTask() {
//                @Override
//                protected Object doInBackground(Object[] params) {
//                    c = BookmarksProviderWrapper.getUrlSuggestions(sparrowActivity.getApplicationContext(), keyWord,
//                            PreferenceManager.getDefaultSharedPreferences(sparrowActivity).getBoolean(Constants.PREFERENCE_USE_WEAVE, false));
//                    Message msg = Message.obtain();
//                    msg.obj = keyWord;
//                    msg.what = REFRESH_URLEDITTEXT;
//                    handler.sendMessage(msg);
//                    return null;
//
//                }
//            };
////                urlSuggestionTask =  SuggestionAsyncTask.getInatance(c,lHandler,sparrowActivity,keyWord,REFRESH_URLEDITTEXT);
//            urlSuggestionTask.execute();
//            tabView.holder.urlSuggestionList.setVisibility(View.VISIBLE);

        }else{
            btnCancel.setText("取消");
//            tabView.holder.urlSuggestionList.setVisibility(View.GONE);
//            tabView.getDeleteSearch().setVisibility(View.GONE);
        }
    }

    //打开扫码
    private void openScan(){
        //--先检测权限
        String[] strings= PermissionUtil.getInstance().checkCameraPermission(activity);
        if(strings!=null&&strings.length>0){
            PermissionUtil.getInstance().requestNeedPermission(activity,strings, HomeActivity.PERMISSION_QR_CODE);
        }else {
            Toast.makeText(activity,"扫描二维码",Toast.LENGTH_SHORT).show();
            Intent intent =new Intent(activity, CaptureActivity.class);
            activity.startActivityForResult(intent,9);
        }
    }

    private void delete(){
       editTextUrl.setText("");
    }

    public void setUrl(String url){
        if(url == null ){
            url = "";
        }else if(url.contains("baidu.com")&&url.contains("?word=")){
            try {
                String result=url.split("word=")[1].split("&")[0];
                url= URLDecoder.decode(result,"utf-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        editTextUrl.setText(url);
    }

    private void setRefresh(){

    }

    private void goOrCancel(){
        String url = editTextUrl.getText().toString().replace(" ", "");
        if (!TextUtils.isEmpty(url)&&!(editTextUrl.getText().toString().equals(activity.getMwebview().getTitle())||
                editTextUrl.getText().toString().equals(activity.getMwebview().getUrl()))&&
                btnCancel.getText().equals("前往")) {
            goSearch(url);
            editTextUrl.clearFocus();
        } else {
            editTextUrl.clearFocus();
        }
    }


    public void setSoftInputHide() {
        imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(editTextUrl.getWindowToken(), 0);
    }

    public void clearHistory() {
        ApplicationUtils.showYesNoDialog(activity, android.R.drawable.ic_dialog_alert, R.string.Commons_ClearHistory, R.string.Commons_NoUndoMessage,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
//                        doClearHistory();
                    }
                });
    }

    //--true则edit处于编辑状态设置为白底黑字，false则edit处于非编辑状态，设置为黑底白字
    public synchronized void setEditState(boolean editEnable){
        if(editEnable){
            searchRlBody.setBackgroundResource(R.drawable.search_corner_white);
            searchUrlImgICon.setImageResource(R.drawable.home_14_gray);
            imgBtnScan.setBackgroundResource(R.drawable.home_9);
            editTextUrl.setTextColor(Color.parseColor("#000000"));
            editTextUrl.setHintTextColor(Color.parseColor("#b4b4b4"));

            btnCancel.setVisibility(View.VISIBLE);
        }else {
            searchRlBody.setBackgroundResource(R.drawable.search_corner_white_gray);
            searchUrlImgICon.setImageResource(R.drawable.home_14_white);
            imgBtnScan.setBackgroundResource(R.drawable.home_9_white);
            editTextUrl.setTextColor(Color.parseColor("#ffffff"));
            editTextUrl.setHintTextColor(Color.parseColor("#ffffff"));
            btnCancel.setVisibility(View.GONE);
        }
    }

    private void goSearch(String stringWord){
        String url1 = stringWord;//现将stringWord赋给url1判断是都已HTTP://或者HTTPS开头 如果没有则给URL1赋头
        if (!(url1.contains("http://") || url1.contains("https://"))) {
            url1 = "http://" + url1+"/";
        }
        Pattern patternUrl = Pattern.compile("((http|ftp|https)://)(([a-zA-Z0-9\\._-]+\\.[a-zA-Z]{2,6})|([0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}))(:[0-9]{1,4})*(/[a-zA-Z0-9\\&%_\\./-~-+]*)?");
        String targetUrl = null;

        String currentUrl = activity.getMwebview().getUrl();
        if (stringWord.startsWith("http://")||stringWord.startsWith("https://")) {//如果URL以HTTP或者HTTPS开头 则目标URL为原始URL
            targetUrl = stringWord;
//            saveSearch(updateSearch, targetUrl , null , currentUrl);//保存历史浏览和关键字
        }else{
            if (patternUrl.matcher(url1).matches()) {//加了HTTP头后若匹配正则 则加载url1 并保存
                targetUrl = url1;
//                saveSearch(updateSearch, targetUrl , null , currentUrl);
            }else {

                try {
                    //part为从服务器取到并保存在sharedpreferences中的字符串
                    String part = Constants.URL_DEFAULT_FROM;
                    //拼接百度搜索字符串
                    targetUrl = Constants.URL_SEARCH_FROM+ part+ "/s?word="+
                            new String(stringWord.getBytes("UTF-8"), "UTF-8").replace("&", "%26").replace("#","%23").replace("%","%25");
//                saveSearch(updateSearch, targetUrl,url, currentUrl);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }

        }

        activity.getMwebview().loadUrl(targetUrl);
    }
}
