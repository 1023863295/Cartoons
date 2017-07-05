package cn.pear.cartoon.ui;

import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.Timer;
import java.util.TimerTask;

import cn.pear.cartoon.R;
import cn.pear.cartoon.base.BaseAty;
import cn.pear.cartoon.global.MyApplication;
import cn.pear.cartoon.test.TestAty;
import cn.shpear.ad.sdk.AdItem;
import cn.shpear.ad.sdk.BaseAD;
import cn.shpear.ad.sdk.NotificationAd;
import cn.shpear.ad.sdk.SdkContext;
import cn.shpear.ad.sdk.listener.NotificationAdListener;

/**
 * Created by liuliang on 2017/6/22.
 */

public class WelcomeAty extends BaseAty implements View.OnClickListener{
    private SimpleDraweeView simpleDraweeView;
    private Timer timer;
    private TimerTask timerTask;

    private TextView textJump;
    private Handler handler;
    private int timeLong = 3; //3s 之后跳转

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setStatusBarTintResource(1);
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(SdkContext.permissions_required,1);
        }else{
            requestPermission();
        }
    }

    @Override
    protected void initData() {
        timer = new Timer();
        timerTask = new TimerTask() {
            @Override
            public void run() {
                handler.sendEmptyMessage(timeLong);
                timeLong --;
            }
        };
    }

    @Override
    protected int getLayoutView() {
        return R.layout.aty_welcome;
    }

    @Override
    protected void initView() {
        simpleDraweeView = (SimpleDraweeView)findViewById(R.id.welcome_img_ad);
        textJump = (TextView)findViewById(R.id.jump_ad_welcome);
        textJump.setOnClickListener(this);
        textJump.setVisibility(View.GONE);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.jump_ad_welcome:
//                Intent intent = new Intent(WelcomeAty.this,HomeActivity.class);

                Intent intent = new Intent(WelcomeAty.this,TestAty.class);
                startActivity(intent);
                finish();
                if (timer != null){
                    timer.cancel();
                    timer.purge();
                    timer = null;
                }
                break;
        }
    }

    @Override
    protected void afterViewInit() {
        handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if (msg.what > 0){
                    textJump.setText(msg.what+"s 跳过");
                }else{
                    Intent intent = new Intent(WelcomeAty.this,TestAty.class);
                    startActivity(intent);
                    finish();
                    if (timer != null){
                        timer.cancel();
                        timer.purge();
                        timer = null;
                    }
                }
            }
        };
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(BaseAD.hasPermission(this)){
            onPermissionGranted();
        }
    }

    public void requestPermission(){
        if(!BaseAD.hasPermission(this)){
            ActivityCompat.requestPermissions(this, SdkContext.permissions_required, 1);
        }else{
            onPermissionGranted();
        }
    }

    public void onPermissionGranted(){
        MyApplication.instance.initAdMax();
//        timer.schedule(timerTask,3000);

        timer.schedule(timerTask,0,1000);
//        pushNotice();

        textJump.setVisibility(View.VISIBLE);
    }


    //测试
    NotificationAd  notificationAd;
    public static final String NOTIFICATION_AD_PID="111111";
    public static final String NATIVE_AD_PID="202";
    //--发送广告通知
    private void pushNotice(){
        Log.i("test","pushNotice");
        notificationAd  = new NotificationAd(getApplication(), NOTIFICATION_AD_PID, cn.shpear.sdk.ad.R.drawable.ad_max_notification_small,
                new NotificationAdListener() {
                    @Override
                    public void onADLoaded(AdItem adItem) {
                        notificationAd.push();
                    }

                    @Override
                    public void onADLoadFail(int i) {
                    }

                    @Override
                    public void onNoAD(int i) {
                    }
                });
        Intent intent = new Intent(getApplication(), TestAty.class);
        PendingIntent pi = PendingIntent.getActivity(getApplication(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        notificationAd.setPendingIntent(pi);
        notificationAd.loadAd();
    }

    @Override
    public void setStatusBarTintResource(int colorId) {
        super.setStatusBarTintResource(colorId);
    }
}
