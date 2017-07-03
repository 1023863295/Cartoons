package cn.pear.cartoon.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import cn.pear.cartoon.R;

/**
 * Created by liuliang on 2017/6/20.
 */

public class RightFragment extends Fragment implements View.OnClickListener{
    private View view;
    private Button btnAdd;
    private Button btnDelete;
    private Button btnUpdate;
    private Button btnSelect;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = View.inflate(getActivity(), R.layout.fragmneg_right_layout,null);
        if (getArguments() != null) {
        }
        initView();
        return view;
    }

    private void initView(){
        btnAdd = (Button)view.findViewById(R.id.right_fragment_btn_add);
        btnAdd.setOnClickListener(this);
        btnDelete = (Button)view.findViewById(R.id.right_fragment_btn_delete);
        btnDelete.setOnClickListener(this);
        btnSelect =(Button)view.findViewById(R.id.right_fragment_btn_select);
        btnSelect.setOnClickListener(this);
        btnUpdate = (Button)view.findViewById(R.id.right_fragment_btn_update);
        btnUpdate.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.right_fragment_btn_add:
//                User insertData = new User(null,"liu","liang");
//                dao.insert(insertData);
//                JsCallNative js = new JsCallNative(getActivity());
//                js.add("新闻","baidu");
                break;
            case R.id.right_fragment_btn_delete:
//                dao.deleteByKey(l);
                break;
            case R.id.right_fragment_btn_select:
                break;
            case R.id.right_fragment_btn_update:
                break;
        }

    }
}
