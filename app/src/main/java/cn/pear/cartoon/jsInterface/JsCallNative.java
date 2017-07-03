package cn.pear.cartoon.jsInterface;

import android.content.Context;
import android.webkit.JavascriptInterface;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;

import cn.pear.cartoon.bean.Cartoon;
import cn.pear.cartoon.bean.CartoonDao;
import cn.pear.cartoon.db.GreenDaoUtils;
import cn.pear.cartoon.tools.StringUtil;
import cn.pear.cartoon.tools.ToastUtil;

/**
 * Created by liuliang on 2017/6/22.
 */

public class JsCallNative {
    private Context mCOntext;

    public JsCallNative(Context context){
        this.mCOntext= context;
    }


    //JavascriptInterface
    @JavascriptInterface
    public void add(String title,String url){
        if (StringUtil.isEmpty(title) && StringUtil.isEmpty(url)){
            return;
        }else{
            QueryBuilder qb= GreenDaoUtils.getSingleTon().getmDaoSession().getCartoonDao().queryBuilder();
            List<Cartoon> cartoonList = qb.where(CartoonDao.Properties.Url.eq(url)).build().list();
            if (cartoonList == null || cartoonList.size()==0){
                //数据库数据不存在
                Cartoon cartoon = new Cartoon(null, title,url);
                long d = GreenDaoUtils.getSingleTon().getmDaoSession().getCartoonDao().insert(cartoon);
                ToastUtil.showLongToast(mCOntext,"收藏成功"+d);
            }else{
                ToastUtil.showLongToast(mCOntext,"已经收藏");
            }
        }
    }
}
