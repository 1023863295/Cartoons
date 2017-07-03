package cn.pear.cartoon.global;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;

import cn.shpear.ad.sdk.SdkConfig;
import cn.shpear.ad.sdk.SdkContext;

/**
 * Created by liuliang on 2017/6/15.
 */

public class MyApplication extends Application {
    public static MyApplication instance;
    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

        //初始化 Fresco
        Fresco.initialize(this);
    }

    public static MyApplication getInstance(){
        return instance;
    }

    //--初始化pear广告的sdk
    public void initAdMax() {
        SdkConfig.Builder builder = new SdkConfig.Builder();
        SdkConfig c = builder.setAppId("410801")
                .setAppSecret("6b7d6792f70043edbd334753854289f0")
                .setApiHostUrl("http://test.api.idsie.com/api.htm").build();
        SdkContext.init(this, c);
    }
}
