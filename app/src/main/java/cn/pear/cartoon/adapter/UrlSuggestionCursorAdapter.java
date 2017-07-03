package cn.pear.cartoon.adapter;

import android.content.Context;
import android.database.Cursor;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SimpleCursorAdapter;

/**
 * Created by liuliang on 2017/6/29.
 */

public class UrlSuggestionCursorAdapter extends SimpleCursorAdapter {

    public static final String URL_SUGGESTION_ID = "_id";
    public static final String URL_SUGGESTION_TITLE = "URL_SUGGESTION_TITLE";
    public static final String URL_SUGGESTION_URL = "URL_SUGGESTION_URL";
    public static final String URL_SUGGESTION_TYPE = "URL_SUGGESTION_TYPE";
    private Handler mHandler;
    private String keyWord;
    /**
     * Constructor.
     * @param context The context.
     * @param handler
     * @param layout The layout.
     * @param c The Cursor.
     * @param from Input array.
     * @param to Output array.
     */
    public UrlSuggestionCursorAdapter(Context context, Handler handler, int layout, Cursor c, String[] from, int[] to, String keyWord) {
        super(context, layout, c, from, to);
        this.mHandler=handler;
        this.keyWord=keyWord;
    }

    public void setKeyWord(String keyWord) {
        this.keyWord = keyWord;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
//
        View superView = super.getView(position, convertView, parent);
//        RelativeLayout rl_urlsuggestion_go= (RelativeLayout) superView.findViewById(R.id.rl_urlsuggestion_go);
//        final TextView autoCompleteUrl= (TextView) superView.findViewById(R.id.AutocompleteUrl);
//        ImageView iconView = (ImageView) superView.findViewById(R.id.AutocompleteImageView);
//        final TextView autoCompleteTitle= (TextView) superView.findViewById(R.id.AutocompleteTitle);
//        int resultType;
//        try {
//            resultType = Integer.parseInt(getCursor().getString(getCursor().getColumnIndex(URL_SUGGESTION_TYPE)));
//        } catch (Exception e) {
//            resultType = 0;
//        }
//        autoCompleteTitle.setText(StringUtil.matcherSearchText(Color.parseColor("#3095ef"),autoCompleteTitle.getText().toString(),keyWord));
//        autoCompleteUrl.setText(StringUtil.matcherSearchText(Color.parseColor("#3095ef"),autoCompleteUrl.getText().toString(),keyWord));
//        switch (resultType) {
//            case 1: iconView.setImageResource(R.drawable.ico_web);
//                autoCompleteUrl.setVisibility(View.VISIBLE);
//                RelativeLayout.LayoutParams layoutParams=new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//                layoutParams.setMargins(20,0,20,0);
//                rl_urlsuggestion_go.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        Message msg=Message.obtain();
//                        msg.obj=autoCompleteUrl.getText();
//                        msg.what= InputAssistView.SET_URLEDITTEXT;
//                        mHandler.sendMessage(msg);
//                    }
//                });
//                autoCompleteTitle.setLayoutParams(layoutParams);
//                break;
//            case 3: iconView.setImageResource(R.drawable.ico_search);
//                autoCompleteUrl.setVisibility(View.GONE);
//                rl_urlsuggestion_go.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        Message msg=Message.obtain();
//                        msg.obj=autoCompleteTitle.getText();
//                        msg.what=InputAssistView.SET_URLEDITTEXT;
//                        mHandler.sendMessage(msg);
//                    }
//                });
//                break;
//            default: break;
//        }
//
        return superView;
    }
}
