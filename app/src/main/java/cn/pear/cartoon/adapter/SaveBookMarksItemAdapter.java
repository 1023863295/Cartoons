package cn.pear.cartoon.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.pear.cartoon.R;
import cn.pear.cartoon.bean.Cartoon;

/**
 * Created by mochy-acer1 on 2016/11/28.
 *
 */
public class SaveBookMarksItemAdapter extends RecyclerView.Adapter<SaveBookMarksItemAdapter.ViewHolder> {
    //数据源
    private List<Cartoon> list;
    //是否显示单选框,默认false
    private boolean isshowBox = false;
    public boolean isSelectAll=false;
    // 存储勾选框状态的map集合
    private Map<Integer, Boolean> map = new HashMap<>();
    //接口实例
    private RecyclerViewOnItemClickListener onItemClickListener;
    private CheckBoxOnCheckListener onCheckListener;

    public SaveBookMarksItemAdapter(List<Cartoon> list) {
        this.list = list;
        initMap();
    }

    //初始化map集合,默认为不选中
    public void initMap() {
        for (int i = 0; i < list.size(); i++) {
            map.put(i, false);
        }
    }

    public boolean isHasSelect(){
        for(int i=0;i<map.size();i++){
            if(map.get(i))
                return true;
        }
        return false;
    }


    //视图管理
    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView title;
        private CheckBox checkBox;
        private View root;
        //        private TextView tv_url;
        private ImageView im_icon;

        public ViewHolder(View root) {
            super(root);
            this.root = root;
            title = (TextView) root.findViewById(R.id.bookmark_title);
            checkBox = (CheckBox) root.findViewById(R.id.bookmark_item_box);
            im_icon= (ImageView) root.findViewById(R.id.bookmark_icon);
//            tv_url= (TextView) root.findViewById(R.id.bookmark_url);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    //绑定视图管理者
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        Cartoon item=list.get(position);
        holder.title.setText(item.getTitle());
//        holder.tv_url.setText(item.getUrl());
        //To-DO
        holder.im_icon.setImageResource(R.drawable.fav_icn_unknown);
        //长按显示/隐藏
        if (isshowBox) {
            holder.checkBox.setVisibility(View.VISIBLE);
        } else {
            holder.checkBox.setVisibility(View.GONE);
        }
        //设置checkBox显示的动画
        if (isshowBox)
            //设置Tag
            holder.root.setTag(position);
        holder.root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    //注意这里使用getTag方法获取数据
                    onItemClickListener.onItemClickListener(v, position);
                }
            }
        });
        //设置checkBox改变监听
        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                //用map集合保存
                map.put(position, isChecked);

                for(int i=0;i<list.size();i++){
                    isSelectAll=true;
                    if(!map.get(i)) {
                        isSelectAll = false;
                        break;
                    }
                }
                if(onCheckListener!=null){
                    onCheckListener.onCheckChange(isSelectAll,isHasSelect());
                }
            }
        });
        // 设置CheckBox的状态
        if (map.get(position) == null) {
            map.put(position, false);
        }
        holder.checkBox.setChecked(map.get(position));
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View root = LayoutInflater.from(parent.getContext()).inflate(R.layout.save_bookmark_row, parent, false);
        ViewHolder vh = new ViewHolder(root);
        //为Item设置点击事件
        return vh;
    }
//    //点击事件
//    @Override
//    public void onClick(View v) {
//        if (onItemClickListener != null) {
//            //注意这里使用getTag方法获取数据
//            Log.i("TAG","onitemclick======================="+(Integer) v.getTag());
//
//            onItemClickListener.onItemClickListener(v, (Integer) v.getTag());
//        }
//    }

//    //长按事件
//    @Override
//    public boolean onLongClick(View v) {
//        //不管显示隐藏，清空状态
//        initMap();
//        return onItemClickListener != null && onItemClickListener.onItemLongClickListener(v, (Integer) v.getTag());
//    }

    //设置点击事件
    public void setRecyclerViewOnItemClickListener(RecyclerViewOnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    //设置点击事件
    public void setCheckBoxListener(CheckBoxOnCheckListener onCheckListener) {
        this.onCheckListener = onCheckListener;
    }


    //设置是否显示CheckBox
    public void setShowBox() {
        //取反
        isshowBox = !isshowBox;
    }

    //点击item选中CheckBox
    public void setSelectItem(int position) {
        //对当前状态取反
        if (map.get(position)) {
            map.put(position, false);
        } else {
            map.put(position, true);
        }
        notifyItemChanged(position);
    }

    public void selectAll(){
        Map<Integer, Boolean> map = getMap();
        for (int i = 0; i < map.size(); i++) {
            map.put(i, true);
            notifyDataSetChanged();
        }
    }

    public void  cancelSelecAll(){
        Map<Integer, Boolean> m = getMap();
        for (int i = 0; i < m.size(); i++) {
            m.put(i, false);
            notifyDataSetChanged();
        }
    }

    public void refreshMap(){
        for(int i =0;i<map.size();i++){
            map.put(i,false);
        }
    }
    //返回集合给MainActivity
    public Map<Integer, Boolean> getMap() {
        return map;
    }

    public interface CheckBoxOnCheckListener{
        void onCheckChange(boolean isSelecAll, boolean isHasSelect);
    }

    //接口回调设置点击事件
    public interface RecyclerViewOnItemClickListener {
        //点击事件
        void onItemClickListener(View view, int position);

        //长按事件
        boolean onItemLongClickListener(View view, int position);
    }
}
