package cn.pear.cartoon.test;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.RelativeLayout;

import cn.pear.cartoon.R;
import cn.pear.cartoon.view.SearchListView;

/**
 * 作者：liuliang
 * 时间 2017/7/2 10:15
 * 邮箱：liang.liu@zmind.cn
 */
public class InputAssistView extends RelativeLayout {
    private TestAty testAty;

    public SearchListView UrlSuggestion;
    public View deleteSearch;

    public static boolean isSuggestion=false;
    public InputAssistView(Context context) {
        this(context, null);
    }

    public InputAssistView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public InputAssistView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init(context, attrs, defStyleAttr);
    }

    public void setActivity(TestAty activity) {
        this.testAty = activity;
    }

    public void init(final Context context, AttributeSet attrs, int defStyleAttr){
        View view = LayoutInflater.from(context).inflate(R.layout.input_assist_view_content, null);
        addView(view);

        UrlSuggestion = (SearchListView)view.findViewById(R.id.Url_Suggestion);
        deleteSearch =  view.findViewById(R.id.delete_Search);
        UrlSuggestion.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                testAty.inputAssistView.setVisibility(View.GONE);
                testAty.browserSearchView.editTextUrl.clearFocus();
                testAty.browserSearchView.setSoftInputHide();
                testAty.browserSearchView.goSearch(testAty.browserSearchView.urlList.get(position).getKeywords());
                testAty.browserSearchView.editTextUrl.setText(testAty.browserSearchView.urlList.get(position).getKeywords());
                testAty.browserSearchView.editTextUrl.setSelection(testAty.browserSearchView.editTextUrl.getText().length());
            }
        });

        UrlSuggestion.setOnScrollChangeListener(new SearchListView.OnScrollChangeListener() {
            @Override
            public void onScroll() {//滑动的时候隐藏软键盘
                testAty.browserSearchView.setSoftInputHide();
            }
        });


        deleteSearch.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                testAty.browserSearchView.clearHistory();
                deleteSearch.setVisibility(View.GONE);
            }
        });

    }
}
