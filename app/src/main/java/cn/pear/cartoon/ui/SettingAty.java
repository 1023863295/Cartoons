package cn.pear.cartoon.ui;

import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import cn.pear.cartoon.R;
import cn.pear.cartoon.base.BaseAty;
import cn.pear.cartoon.tools.ToastUtil;

/**
 * Created by liuliang on 2017/6/22.
 */

public class SettingAty extends BaseAty implements View.OnClickListener{
    private ImageView imgBack;
    private TextView textTitle;

    private RelativeLayout rlFeedback;
    private RelativeLayout rlAbout;

    @Override
    protected void initData() {

    }

    @Override
    protected int getLayoutView() {
        return R.layout.aty_setting;
    }

    @Override
    protected void initView() {
        imgBack = (ImageView)findViewById(R.id.head_top_img_back);
        imgBack.setOnClickListener(this);
        textTitle = (TextView)findViewById(R.id.head_top_text_title);
        textTitle.setText(R.string.bottom_tools_set);

        rlFeedback = (RelativeLayout)findViewById(R.id.set_rl_feedback);
        rlFeedback.setOnClickListener(this);
        rlAbout = (RelativeLayout)findViewById(R.id.set_rl_about);
        rlAbout.setOnClickListener(this);
    }

    @Override
    protected void afterViewInit() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.head_top_img_back:
                finish();
                break;
            case R.id.set_rl_about:
                ToastUtil.showLongToast(this,"正在开发中");
                break;
            case R.id.set_rl_feedback:
                ToastUtil.showLongToast(this,"正在开发中");
                break;
        }

    }
}
