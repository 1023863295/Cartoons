package cn.pear.cartoon.global;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import cn.pear.cartoon.tools.ApplicationUtils;
import cn.pear.cartoon.tools.OkhttpUtil;
import cn.pear.cartoon.tools.SharedPreferencesHelper;
import cn.shpear.ad.sdk.SdkConfig;
import cn.shpear.ad.sdk.SdkContext;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

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

    public static MyApplication getInstance() {
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

    private void getConfigInfo() {
        OkHttpClient okHttpClient = OkhttpUtil.getInstance().getOkHttpClient();
        RequestBody formBody = new FormBody.Builder()
                .add("ver", ApplicationUtils.getVerName(this))
//                .add("userid", UserPreference.getIdentity(this))
//                .add("phonedid", phoneDid)
                .build();
        Request request = new Request.Builder()
                .url(Constants.URL_CONFIG_INFO)
                .post(formBody)
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    JSONObject jsonObject = new JSONObject(response.body().string());
                    JSONArray jsonArray = jsonObject.getJSONArray("Data");
                    if (jsonArray != null && jsonArray.length() > 0) {
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject obj = jsonArray.getJSONObject(i);
                            String key = obj.getString("configkey");
                            String value = obj.getString("configvalue");
                            if ("baidupid".equals(key)){
                                SharedPreferencesHelper.saveData(MyApplication.this,"baidupid",value);
                            }
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
