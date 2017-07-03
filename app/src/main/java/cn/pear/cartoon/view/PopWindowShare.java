package cn.pear.cartoon.view;

import android.content.Context;
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

/**
 * Created by liuliang on 2017/6/26.
 * 分享
 */

public class PopWindowShare implements View.OnClickListener{
    private Context mContext;
    private View parent; //相对页面控件位置
    private View rootView;
    private TextView textWeixinCircle;
    private TextView textWeixin;
    private TextView textQq;
    private TextView textQQzone;

    private RelativeLayout rlOthers;
    private RelativeLayout rlClose;

    private PopupWindow popupWindow;
    private LinearLayout contentLinear;


    public PopWindowShare(Context context,View view){
        this.mContext = context;
        this.parent = view;
        initView();
    }

    private void initView(){
        rootView = View.inflate(mContext, R.layout.pop_widowns_share,null);
        textWeixinCircle = (TextView)rootView.findViewById(R.id.share_text_weixin_circle);
        textWeixinCircle.setOnClickListener(this);
        textWeixin = (TextView)rootView.findViewById(R.id.share_text_weixin);
        textWeixin.setOnClickListener(this);
        textQq = (TextView)rootView.findViewById(R.id.share_text_qq);
        textQq.setOnClickListener(this);
        textQQzone = (TextView)rootView.findViewById(R.id.share_text_qq_zone);
        textQQzone.setOnClickListener(this);

        rlOthers = (RelativeLayout)rootView.findViewById(R.id.share_rl_other);
        rlOthers.setOnClickListener(this);
        rlClose = (RelativeLayout)rootView.findViewById(R.id.share_rl_close);
        rlClose.setOnClickListener(this);
        contentLinear = (LinearLayout)rootView.findViewById(R.id.pop_widowns_share_linear);



        rootView.setOnKeyListener(new View.OnKeyListener() {
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

        popupWindow = new PopupWindow(rootView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.setAnimationStyle(R.style.popwindow_menu_alpha_anim_style);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setTouchable(true);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.share_text_weixin_circle:
                break;
            case  R.id.share_text_weixin:
                shareTo(1,"");
                break;
            case R.id.share_text_qq_zone:
                shareTo(2,"");
                break;
            case R.id.share_text_qq:
                break;
            case R.id.share_rl_other:
                break;
            case R.id.share_rl_close:
                break;
        }
        dismiss();
    }

    public void show(){
        popupWindow.showAtLocation(parent, Gravity.BOTTOM, 0, 0);// 距离底部的位置
        Animation a = AnimationUtils.loadAnimation(mContext,R.anim.slide_bottom_in);
        contentLinear.setVisibility(View.VISIBLE);
        contentLinear.startAnimation(a);
    }

    public void dismiss(){
        popupWindow.dismiss();
        Animation out = AnimationUtils.loadAnimation(mContext,R.anim.slide_bottom_out);
        contentLinear.clearAnimation();
        contentLinear.startAnimation(out);
        contentLinear.setVisibility(View.GONE);
    }


    //分享 0 微信朋友圈，1 微信朋友， 2 qq好友，3 qq空间
    private void shareTo(int type,String shareUrl){
//        sendIntent.setClassName("com.tencent.mm", "com.tencent.mm.ui.tools.ShareImgUI");//微信朋友
//          sendIntent.setClassName("com.tencent.mobileqq", "cooperation.qqfav.widget.QfavJumpActivity");//保存到QQ收藏
//          sendIntent.setClassName("com.tencent.mobileqq", "cooperation.qlink.QlinkShareJumpActivity");//QQ面对面快传
//          sendIntent.setClassName("com.tencent.mobileqq", "com.tencent.mobileqq.activity.qfileJumpActivity");//传给我的电脑
//        sendIntent.setClassName("com.tencent.mobileqq", "com.tencent.mobileqq.activity.JumpActivity");//QQ好友或QQ群
//          sendIntent.setClassName("com.tencent.mm", "com.tencent.mm.ui.tools.ShareToTimeLineUI");//微信朋友圈，仅支持分享图片
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, shareUrl);
        sendIntent.setType("text/plain");
        switch (type){
            case 0:
                sendIntent.setClassName("com.tencent.mm", "com.tencent.mm.ui.tools.ShareImgUI");//微信朋友
                mContext.startActivity(Intent.createChooser(sendIntent, "分享"));
                break;
            case 1:
                break;
            case 2:
                sendIntent.setClassName("com.tencent.mobileqq", "com.tencent.mobileqq.activity.JumpActivity");//QQ好友或QQ群
                break;
            case 3:
                break;
        }
        mContext.startActivity(Intent.createChooser(sendIntent, "分享"));

    }
}
