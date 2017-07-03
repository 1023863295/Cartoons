package cn.pear.cartoon.test;

import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupWindow;

import cn.pear.cartoon.R;


/**
 * Created by mochy-acer1 on 2017/2/6.
 */

public class PopTextHelper {
    public static final int TEXT_BTN_XIEXIAN = 200;
    public static final int TEXT_BTN_DIAN = 201;
    public static final int TEXT_BTN_COM = 202;
    public static final int TEXT_BTN_CN = 203;
    public static final int TEXT_BTN_NET = 204;
    private View view;
    public Button btn_xiexian;
    public Button btn_dian;
    public Button btn_com;
    public Button btn_cn;
    public Button btn_net;
    private PopupWindow popupWindow;
    private View.OnClickListener onClickListener;

    Context context;
    Handler myHandler;
    public PopTextHelper(Context context, Handler myHandler) {
        this.context = context;
        this.myHandler = myHandler;
        view = LayoutInflater.from(context).inflate(R.layout.popmenu_texthelper,null);
        init();
        intViews();
    }

    private void init() {
        onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()){
                    case R.id.btn_xiexian:
                        myHandler.sendEmptyMessage(TEXT_BTN_XIEXIAN);
                        break;
                    case R.id.btn_dian:
                        myHandler.sendEmptyMessage(TEXT_BTN_DIAN);
                        break;
                    case R.id.btn_com:
                        myHandler.sendEmptyMessage(TEXT_BTN_COM);
                        break;
                    case R.id.btn_cn:
                        myHandler.sendEmptyMessage(TEXT_BTN_CN);
                        break;
                    case R.id.btn_net:
                        myHandler.sendEmptyMessage(TEXT_BTN_NET);
                        break;
                }
            }
        };
    }

    private void intViews() {
        btn_xiexian = (Button) view.findViewById(R.id.btn_xiexian);
        btn_dian = (Button) view.findViewById(R.id.btn_dian);
        btn_com = (Button) view.findViewById(R.id.btn_com);
        btn_cn = (Button) view.findViewById(R.id.btn_cn);
        btn_net = (Button) view.findViewById(R.id.btn_net);

        popupWindow = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT,
                context.getResources().getDimensionPixelSize(R.dimen.pop_text_helper_h));
        popupWindow.setBackgroundDrawable(null);
        popupWindow.setAnimationStyle(R.style.pop_texthelper_anim_style);
        btn_xiexian.setOnClickListener(onClickListener);
        btn_dian.setOnClickListener(onClickListener);
        btn_com.setOnClickListener(onClickListener);
        btn_cn.setOnClickListener(onClickListener);
        btn_net.setOnClickListener(onClickListener);

    }

    public void show(View parent, int bottom) {
        popupWindow.showAtLocation(parent, Gravity.BOTTOM, 0, bottom);
        Log.i("TAG","POPWINDOW===SHOW");
//        popupWindow.showAsDropDown(parent,0,-110);
        popupWindow.update();

    }

    public void dismiss() {
        popupWindow.dismiss();
    }

    public boolean isShow(){
        return popupWindow != null && popupWindow.isShowing();
    }

}
