package cn.pear.cartoon.components;

import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.EditText;

/**
 * Created by liuliang on 2017/6/28.
 */

public class DetailWeChromeClient extends WebChromeClient {
    private EditText textTitle;

    public DetailWeChromeClient(EditText textView){
        this.textTitle = textView;

    }


    @Override
    public void onReceivedTitle(WebView view, String title) {
        textTitle.setText(title);
        super.onReceivedTitle(view, title);
    }
}
