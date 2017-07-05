package cn.pear.cartoon.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.LayoutAnimationController;
import android.view.animation.TranslateAnimation;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cn.pear.cartoon.R;
import cn.pear.cartoon.adapter.SaveBookMarksItemAdapter;
import cn.pear.cartoon.bean.Cartoon;
import cn.pear.cartoon.db.GreenDaoUtils;
import cn.pear.cartoon.test.TestAty;
import cn.pear.cartoon.tools.ApplicationUtils;

/**
 * Created by liuliang on 2017/6/22.
 * 收藏
 */

public class SaveListFragment extends Fragment implements View.OnClickListener{
    private View view;

    private RecyclerView mList;
    RelativeLayout rlHistory;
    RelativeLayout rlNoBookmarkS;

    private List<Cartoon> listCartoon;
    private SaveBookMarksItemAdapter adapter;

    public  boolean isChecking=false;
    private RelativeLayout rl_bookmark_bottom;
    static TextView bookmark_selectall;
    static TextView bookmark_history_delete;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_list_save, container, false);
        initView();
        return view;
    }

    private void initView() {
        mList = (RecyclerView)view.findViewById(R.id.save_bookmarks_rv);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mList.setLayoutManager(manager);
        mList.setHasFixedSize(true);

        rlHistory= (RelativeLayout) view.findViewById(R.id.rlHistory);
        rlNoBookmarkS= (RelativeLayout) view.findViewById(R.id.rlNoBookmarkS);

        listCartoon = new ArrayList<>();
        fillData();

        adapter.setRecyclerViewOnItemClickListener(new SaveBookMarksItemAdapter.RecyclerViewOnItemClickListener() {
            @Override
            public void onItemClickListener(View view, int position) {
                if(!isChecking){
                    Intent intent = new Intent(getActivity(), TestAty.class);
                    intent.putExtra("to_url",listCartoon.get(position).getUrl());
                    startActivity(intent);
                }
            }
            @Override
            public boolean onItemLongClickListener(View view, int position) {
                return false;
            }
        });

        rl_bookmark_bottom = (RelativeLayout)view.findViewById(R.id.rl_bookmark_bottom);
        bookmark_selectall = (TextView)view.findViewById(R.id.bookmark_selectall);
        bookmark_selectall.setOnClickListener(this);
        bookmark_history_delete = (TextView)view.findViewById(R.id.bookmark_history_delete);
        bookmark_history_delete.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bookmark_selectall:
                if(adapter.isSelectAll){
                    adapter.cancelSelecAll();
                    adapter.isSelectAll=false;
                    bookmark_selectall.setText("全选");
                }else {
                    adapter.selectAll();
                    adapter.isSelectAll=true;
                    bookmark_selectall.setText("取消全选");
                }
                break;
            case R.id.bookmark_history_delete:
                if(adapter.isHasSelect()){
                    clearHistory();
                }
                break;
        }

    }

    /**
     * Fill the list.
     */
    private void fillData(){
        listCartoon.clear();
        listCartoon.addAll(GreenDaoUtils.getSingleTon().getmDaoSession().getCartoonDao().loadAll());
        adapter=new SaveBookMarksItemAdapter(listCartoon);
        if(!(adapter.getItemCount()> 0)){
            rlNoBookmarkS.setVisibility(View.VISIBLE);
            mList.setVisibility(View.GONE);
        }else {
            rlNoBookmarkS.setVisibility(View.GONE);
            mList.setVisibility(View.VISIBLE);
        }
        mList.setAdapter(adapter);
        setAnimation();
    }

    /**
     * Set the list loading animation.
     */
    private void setAnimation() {
        AnimationSet set = new AnimationSet(true);

        Animation animation = new AlphaAnimation(0.0f, 1.0f);
        animation.setDuration(100);
        set.addAnimation(animation);

        animation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, -1.0f, Animation.RELATIVE_TO_SELF, 0.0f);
        animation.setDuration(100);
        set.addAnimation(animation);
        LayoutAnimationController controller = new LayoutAnimationController(set, 0.5f);

        mList.setLayoutAnimation(controller);
    }

    public void setMod(){
        if(adapter.getItemCount()>0){
            isChecking=!isChecking;
            showEditLayout();
            adapter.setShowBox();
            //设置选中的项
            adapter.notifyDataSetChanged();
        }
    }

    //是否为编辑状态 是就显示底部栏否则隐藏
    public void showEditLayout() {
        if(isChecking){
            rl_bookmark_bottom.setVisibility(View.VISIBLE);
        }else {
            rl_bookmark_bottom.setVisibility(View.GONE);
        }
    }

    //--弹出对话框，确认是否删除所有书签
    public void clearHistory() {
        ApplicationUtils.showYesNoDialog(getActivity(), android.R.drawable.ic_dialog_alert, R.string.Commons_ClearHistory, R.string.Commons_NoUndoMessage,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(adapter.isSelectAll){
                            //全部删除
                            GreenDaoUtils.getSingleTon().getmDaoSession().getCartoonDao().deleteAll();
                        }else {
                            Map<Integer, Boolean> map = adapter.getMap();
                            for (int i = 0; i < map.size(); i++) {
                                if (map.get(i)) {
                                    Cartoon cartoon = listCartoon.get(i);
                                    GreenDaoUtils.getSingleTon().getmDaoSession().getCartoonDao().delete(cartoon);
//                                    BookmarksProviderWrapper.deleteStockBookmark(getActivity().getContentResolver(), list.get(i).getmUrl());
                                }
                            }
                            adapter.refreshMap();
                        }
                        fillData();
                        adapter.notifyDataSetChanged();
                    }
                });
    }
}
