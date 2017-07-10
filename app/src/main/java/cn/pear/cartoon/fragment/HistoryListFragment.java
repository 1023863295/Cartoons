package cn.pear.cartoon.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.LayoutAnimationController;
import android.view.animation.TranslateAnimation;
import android.widget.ExpandableListView;
import android.widget.RelativeLayout;

import java.util.List;

import cn.pear.cartoon.R;
import cn.pear.cartoon.adapter.HistoryExpandableListAdapter;

/**
 * Created by liuliang on 2017/6/22.
 * 历史
 */

public class HistoryListFragment extends Fragment {
    private View view;
    private ExpandableListView expandableListView;
    private RelativeLayout rlNoHitorys;

    private List<String> group_dateTime;
    private List<String> child_url;
    private HistoryExpandableListAdapter historyExpandableListAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.expanellistview,container,false);
        initView();
        return view;
    }

    private void initView(){
        expandableListView = (ExpandableListView)view.findViewById(R.id.List);
        rlNoHitorys = (RelativeLayout)view.findViewById(R.id.rlNoHitorys);
        registerForContextMenu(expandableListView);
        expandableListView.setGroupIndicator(null);
        setAnimation();
    }



    private void setAnimation() {
        AnimationSet set = new AnimationSet(true);

        Animation animation = new AlphaAnimation(0.0f, 1.0f);
        animation.setDuration(100);
        set.addAnimation(animation);

        animation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, -1.0f, Animation.RELATIVE_TO_SELF, 0.0f);
        animation.setDuration(100);
        set.addAnimation(animation);
        LayoutAnimationController controller = new LayoutAnimationController(set, 0.5f);

        expandableListView.setLayoutAnimation(controller);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        return super.onContextItemSelected(item);
    }

    private void fillData() {

    }
}
