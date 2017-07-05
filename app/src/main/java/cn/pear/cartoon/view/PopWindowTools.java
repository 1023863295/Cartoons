package cn.pear.cartoon.view;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import cn.pear.cartoon.R;
import cn.pear.cartoon.test.TestAty;
import cn.pear.cartoon.ui.SaveAty;
import cn.pear.cartoon.ui.SettingAty;

/**
 * Created by liuliang on 2017/6/21.
 */
//工具弹出框
public class PopWindowTools implements View.OnClickListener{
    private TestAty mContext;
    private View parent; //相对页面控件位置

    private PopupWindow popupWindow;
    private View view;
    private LinearLayout toolLinear;
    private boolean isShow  = false;

    private TextView textSave;
    private TextView textShare;
    private TextView textSet;
    private TextView textLook;
    private RelativeLayout rlClose;

    private PopWindowShare shareWindows;

    public PopWindowTools(TestAty context,View view){
        this.mContext = context;
        this.parent = view;
        initView();
    }


    public void show(){
        isShow = true;
        popupWindow.showAtLocation(parent, Gravity.BOTTOM, 0, 0);// 距离底部的位置
        Animation a = AnimationUtils.loadAnimation(mContext,R.anim.slide_bottom_in);
        toolLinear.setVisibility(View.VISIBLE);
        toolLinear.startAnimation(a);
    }

    public void dismiss(){
        isShow = false;
        popupWindow.dismiss();
        Animation out = AnimationUtils.loadAnimation(mContext,R.anim.slide_bottom_out);
        toolLinear.clearAnimation();
        toolLinear.startAnimation(out);
        toolLinear.setVisibility(View.GONE);
    }

    public boolean isShow(){
        return  isShow;
    }



    private void initView(){
        view = View.inflate(mContext,R.layout.pop_windows_tools, null);
        view.setFocusableInTouchMode(true);
        view.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // TODO Auto-generated method stub
                if ((keyCode == KeyEvent.KEYCODE_MENU) && (popupWindow.isShowing())) {
                    popupWindow.dismiss();// 这里写明模拟menu的PopupWindow退出就行
                    return true;
                }
                return false;
            }
        });

        RelativeLayout relativeLayout = (RelativeLayout)view.findViewById(R.id.rl_menu_other);
        relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
        toolLinear = (LinearLayout)view.findViewById(R.id.pop_widowns_tools_linear);

        popupWindow = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.setAnimationStyle(R.style.popwindow_menu_alpha_anim_style);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setTouchable(true);

        textSave = (TextView)view.findViewById(R.id.pop_windowns_text_save);
        textSave.setOnClickListener(this);
        textShare = (TextView)view.findViewById(R.id.pop_windowns_text_share);
        textShare.setOnClickListener(this);
        textSet = (TextView)view.findViewById(R.id.pop_windowns_text_set);
        textSet.setOnClickListener(this);
        textLook = (TextView)view.findViewById(R.id.pop_windowns_text_look);
        textLook.setOnClickListener(this);
        rlClose = (RelativeLayout)view.findViewById(R.id.pop_widowns_tools_rl_close);
        rlClose.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.pop_widowns_tools_rl_close:
               dismiss();
                break;
            case R.id.pop_windowns_text_save:
                Intent intentSave = new Intent(mContext, SaveAty.class);
                mContext.startActivity(intentSave);
                break;
            case R.id.pop_windowns_text_share:
                share(mContext.getMwebview().getTitle(),mContext.getMwebview().getUrl());
                break;
            case R.id.pop_windowns_text_set:
                Intent intentSet = new Intent(mContext, SettingAty.class);
                mContext.startActivity(intentSet);
                break;
            case R.id.pop_windowns_text_look:

                break;
        }
        dismiss();
    }

    private void share(String message,String url){
        Intent textIntent = new Intent(Intent.ACTION_SEND);
        textIntent.setType("text/plain");
        textIntent.putExtra(Intent.EXTRA_TEXT, message);
        mContext.startActivity(Intent.createChooser(textIntent, url));
    }
}
