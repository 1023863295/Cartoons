package cn.pear.cartoon.test;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import cn.pear.cartoon.R;
import cn.pear.cartoon.tools.ToastUtil;

/**
 * Created by liuliang on 2017/7/4.
 */

public class BottomBarView extends LinearLayout implements View.OnClickListener{
    private ImageButton imgBtnBack;
    private ImageButton imgBtnNext;
    private ImageButton imgBtnTool;
    private ImageButton imgBtnHome;

    private View view;
    private TestAty testAty;

    public BottomBarView(Context context) {
        super(context);
    }

    public BottomBarView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public BottomBarView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    public void init(final Context context, AttributeSet attrs, int defStyleAttr){
        view = View.inflate(context, R.layout.bottom_bar_layout,null);
        addView(view);

        imgBtnBack = (ImageButton)view.findViewById(R.id.bottom_btn_back);
        imgBtnBack.setOnClickListener(this);
        imgBtnNext = (ImageButton)view.findViewById(R.id.bottom_btn_next);
        imgBtnNext.setOnClickListener(this);
        imgBtnTool = (ImageButton)view.findViewById(R.id.bottom_btn_tools);
        imgBtnTool.setOnClickListener(this);
        imgBtnHome = (ImageButton)view.findViewById(R.id.bottom_btn_home);
        imgBtnHome.setOnClickListener(this);
    }

    public void setActivity(TestAty testAty) {
        this.testAty = testAty;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bottom_btn_back:
                ToastUtil.showShortToast(testAty,"onClic");
                break;
            case R.id.bottom_btn_next:
                break;
            case R.id.bottom_btn_tools:
                break;
            case R.id.bottom_btn_home:
                break;
        }

    }
}
