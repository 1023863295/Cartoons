package cn.pear.cartoon.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import cn.pear.cartoon.R;

/**
 * 作者：liuliang
 * 时间 2017/7/2 21:54
 * 邮箱：liang.liu@zmind.cn
 */
public class UrlAdapter extends BaseAdapter {
    private Context mContext;
    private List<Object> list;

    public UrlAdapter(Context mContext, List<Object> list) {
        this.mContext = mContext;
        this.list = list;
    }

    public UrlAdapter() {
    }

    public void setList(List<Object> list) {
        this.list = list;
    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null){
            convertView = View.inflate(mContext, R.layout.url_autocomplete_line,null);
            holder = new ViewHolder();
            holder.autoCompleteTitle = (TextView)convertView.findViewById(R.id.AutocompleteTitle);
            holder.autoCompleteUrl = (TextView)convertView.findViewById(R.id.AutocompleteUrl);
            holder.iconView = (ImageView)convertView.findViewById(R.id.AutocompleteImageView);
        }else{
            convertView = View.inflate(mContext, R.layout.url_autocomplete_line,null);
        }

        return convertView;
    }

    class ViewHolder{
        public TextView autoCompleteUrl;
        public ImageView iconView ;
        public TextView autoCompleteTitle ;
    }
}
