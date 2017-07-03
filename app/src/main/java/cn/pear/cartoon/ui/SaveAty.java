package cn.pear.cartoon.ui;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import cn.pear.cartoon.R;
import cn.pear.cartoon.base.BaseAty;
import cn.pear.cartoon.fragment.HistoryListFragment;
import cn.pear.cartoon.fragment.SaveListFragment;

/**
 * Created by liuliang on 2017/6/22.
 */

public class SaveAty extends BaseAty implements View.OnClickListener{
    private ImageView imgBack;
    private TextView textTitle;
    private TextView textRight;

    private LinearLayout linearRG;
    private TextView textSave;
    private TextView textHistory;

    private HistoryListFragment historyListFragment;
    private SaveListFragment saveListFragment;
    FragmentManager fm;
    FragmentTransaction ft;

    @Override
    protected void initData() {
        saveListFragment = new SaveListFragment();

    }

    @Override
    protected int getLayoutView() {
        return R.layout.aty_save_layout;
    }

    @Override
    protected void initView() {
        imgBack = (ImageView)findViewById(R.id.head_top_img_back);
        imgBack.setOnClickListener(this);
        textTitle = (TextView)findViewById(R.id.head_top_text_title);
        textTitle.setText(R.string.bottom_tools_save);
        textRight = (TextView)findViewById(R.id.head_top_text_more);
        textRight.setVisibility(View.VISIBLE);
        textRight.setText("编辑");
        textRight.setOnClickListener(this);

        linearRG = (LinearLayout)findViewById(R.id.save_radioGroup);
        textSave = (TextView)findViewById(R.id.save_text_Collection);
        textSave.setOnClickListener(this);
        textHistory = (TextView)findViewById(R.id.save_text_History);
        textHistory.setOnClickListener(this);
    }

    @Override
    protected void afterViewInit() {
        fm=getSupportFragmentManager();
        ft=fm.beginTransaction();
        ft.replace(R.id.save_history_body,saveListFragment);
        ft.commit();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.head_top_img_back:
                finish();
                break;
            case R.id.save_text_Collection:
                fm=getSupportFragmentManager();
                ft=fm.beginTransaction();
                if(saveListFragment==null){
                    saveListFragment = new SaveListFragment();
                }
                ft.replace(R.id.save_history_body,saveListFragment);
                ft.commit();

                linearRG.setBackgroundResource(R.drawable.download_ing_1);
                textHistory.setTextColor(getResources().getColor(R.color.colorRadio));
                textSave.setTextColor(getResources().getColor(R.color.white));
                textTitle.setText("收藏");

                textRight.setVisibility(View.VISIBLE);
                break;
            case R.id.save_text_History:
                fm=getSupportFragmentManager();
                ft=fm.beginTransaction();
                if(historyListFragment==null){
                    historyListFragment = new HistoryListFragment();
                }
                ft.replace(R.id.save_history_body,historyListFragment);
                ft.commit();

                linearRG.setBackgroundResource(R.drawable.download_ing_2);
                textSave.setTextColor(getResources().getColor(R.color.colorRadio));
                textHistory.setTextColor(getResources().getColor(R.color.white));
                textTitle.setText("历史");
                textRight.setVisibility(View.GONE);
                break;
            case R.id.head_top_text_more:
                saveListFragment.setMod();
                break;
        }
    }
}
