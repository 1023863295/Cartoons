package cn.pear.cartoon.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;

import java.util.List;

import cn.pear.cartoon.R;

/**
 * Created by liuliang on 2017/7/10.
 */

public class HistoryExpandableListAdapter  extends BaseExpandableListAdapter {
    private Context mContext;
    private LayoutInflater mInflater = null;

    private List<Object> groupData;
    private List<Object> childData;

    public HistoryExpandableListAdapter(Context context){
        mContext = context;
        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public int getGroupCount() {
        return groupData.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return groupData.get(groupPosition).;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return groupData.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return ;
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        if (convertView == null){
            convertView = mInflater.inflate(R.layout.history_group, null, false);
        }
        convertView.setTag(R.layout.history_group,groupPosition);
        convertView.setTag(R.layout.history_row,-1);
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        if (convertView == null){
            convertView =  mInflater.inflate(R.layout.history_row, null, false);
        }
        convertView.setTag(R.layout.history_group, groupPosition);
        convertView.setTag(R.layout.history_row, childPosition);
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }
}
