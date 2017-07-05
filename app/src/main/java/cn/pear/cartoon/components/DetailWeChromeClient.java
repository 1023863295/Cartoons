package cn.pear.cartoon.components;

import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.ProgressBar;
import android.widget.TextView;

import cn.pear.cartoon.R;

/**
 * Created by liuliang on 2017/6/28.
 */

public class DetailWeChromeClient extends WebChromeClient {
    private TextView textTitle;
    private ProgressBar progressBar;
    private View parentView;

    public DetailWeChromeClient(TextView textView){
        this.textTitle = textView;

    }

    @Override
    public void onProgressChanged(WebView view, int newProgress) {
        super.onProgressChanged(view, newProgress);
        parentView = (View) view.getParent();
        progressBar = (ProgressBar)parentView.findViewById(R.id.detail_web_progress_bar);
        progressBar.setProgress(newProgress);
    }

    @Override
    public void onReceivedTitle(WebView view, String title) {
        textTitle.setText(title);
        super.onReceivedTitle(view, title);
    }
}
