package cn.pear.cartoon.base;

import android.annotation.TargetApi;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;

import cn.pear.cartoon.R;
import cn.pear.cartoon.tools.SystemBarTintManager;

/**
 * Created by liuliang on 2017/6/14.
 */

public abstract class BaseAty extends AppCompatActivity {
    private int stateBarColor = 0; // 0 代表默认背景色，1 代表不显示，其它表示自定义

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        modifyStatusColor();
        initData();
        setContentView(getLayoutView());

        initView();
        afterViewInit();
    }


    private void modifyStatusColor() {
        //修改状态栏以及虚拟按键颜色
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setTranslucentStatus(true);
            SystemBarTintManager tintManager = new SystemBarTintManager(this);
            tintManager.setStatusBarTintEnabled(true);
//        tintManager.setNavigationBarTintEnabled(true);
            if (stateBarColor == 0){
                tintManager.setStatusBarTintResource(R.color.top_title_bg);
                tintManager.setNavigationBarTintColor(Color.BLACK);
//                tintManager.setStatusBarAlpha(0.5f);
            }else if(stateBarColor == 1) {

            }else {
                tintManager.setStatusBarTintColor(stateBarColor);
            }
        }
    }

    @TargetApi(19)
    private void setTranslucentStatus(boolean on) {
        Window win = getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }


    public void  setStatusBarTintResource(int colorId){
        this.stateBarColor = colorId;
//        getSupportActionBar().hide();// 隐藏ActionBar
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                WindowManager.LayoutParams.FLAG_FULLSCREEN);//remove notification bar  即全屏
    }

    /**
     * 初始化数据，包括从bundle中获取数据保存到当前activity中
     */
    protected abstract void initData();


    /**
     * 设置viewlayout
     */
    protected abstract int getLayoutView();

    /**
     * 初始化界面，如获取界面中View的名称并保存，定义Title的文字，以及定义各个控件的处理事件
     */
    protected abstract void initView();

    /**
     * 界面初始化之后的后处理，如启动网络读取数据、启动定位等
     */
    protected abstract void afterViewInit();


}
