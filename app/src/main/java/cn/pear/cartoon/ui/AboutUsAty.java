package cn.pear.cartoon.ui;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import cn.pear.cartoon.R;
import cn.pear.cartoon.base.BaseAty;
import cn.pear.cartoon.tools.ApplicationUtils;

/**
 * Created by liuliang on 2017/7/4.
 */

public class AboutUsAty extends BaseAty implements View.OnClickListener{
    private ImageView imgBack;
    private TextView textTitle;

    private TextView textVersionName;

    @Override
    protected void initData() {

    }

    @Override
    protected int getLayoutView() {
        return R.layout.aty_about_layout;
    }

    @Override
    protected void initView() {
        imgBack = (ImageView)findViewById(R.id.head_top_img_back);
        imgBack.setOnClickListener(this);
        textTitle = (TextView)findViewById(R.id.head_top_text_title);
        textTitle.setText(R.string.about_us);

        textVersionName = (TextView)findViewById(R.id.about_us_version_name_text);
        textVersionName.setText(getResources().getString(R.string.app_name)+
                ApplicationUtils.getVerName(this));
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
        }

    }
}
