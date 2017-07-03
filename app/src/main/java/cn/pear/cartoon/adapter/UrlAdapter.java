package cn.pear.cartoon.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import cn.pear.cartoon.R;
import cn.pear.cartoon.bean.SearchKeyWords;
import cn.pear.cartoon.test.TestAty;

/**
 * 作者：liuliang
 * 时间 2017/7/2 21:54
 * 邮箱：liang.liu@zmind.cn
 */
public class UrlAdapter extends BaseAdapter {
    private TestAty mContext;
    private List<SearchKeyWords> list;

    public UrlAdapter(TestAty mContext, List<SearchKeyWords> list) {
        this.mContext = mContext;
        this.list = list;
    }

    public UrlAdapter() {
    }

    public void setList(List<SearchKeyWords> list) {
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null){
            convertView = View.inflate(mContext, R.layout.url_autocomplete_line,null);
            holder = new ViewHolder();
            holder.autoCompleteTitle = (TextView)convertView.findViewById(R.id.AutocompleteTitle);
            holder.autoCompleteUrl = (TextView)convertView.findViewById(R.id.AutocompleteUrl);
            holder.iconView = (ImageView)convertView.findViewById(R.id.AutocompleteImageView);
            holder.rlGo = (RelativeLayout)convertView.findViewById(R.id.rl_urlsuggestion_go);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }

        holder.autoCompleteTitle.setText(list.get(position).getKeywords());

        holder.rlGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContext.browserSearchView.editTextUrl.setText(mContext.browserSearchView.urlList.get(position).getKeywords());
                mContext.browserSearchView.editTextUrl.setSelection(mContext.browserSearchView.editTextUrl.getText().length());
            }
        });

        return convertView;
    }

    class ViewHolder{
        public TextView autoCompleteUrl;
        public ImageView iconView ;
        public TextView autoCompleteTitle ;
        public RelativeLayout rlGo;
    }
}
