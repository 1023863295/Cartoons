package cn.pear.cartoon.tools;
import okhttp3.OkHttpClient;

/**
 * Created by liuliang on 2017/7/3.
 */

public class OkhttpUtil {
    public static OkhttpUtil okhttpUtil;
    private OkHttpClient okHttpClient;

    private OkhttpUtil(){}

    public static OkhttpUtil getInstance(){

        if (okhttpUtil == null) {
            synchronized (OkhttpUtil.class) {
                if (okhttpUtil == null) {
                    okhttpUtil = new OkhttpUtil();
                }
            }
        }
        return okhttpUtil;
    }

    public OkHttpClient getOkHttpClient(){
        if(okHttpClient==null){
            synchronized (OkHttpClient.class) {
                if (okHttpClient == null) {
                    okHttpClient = new OkHttpClient();
                }
            }
        }
        return okHttpClient;
    }
}
