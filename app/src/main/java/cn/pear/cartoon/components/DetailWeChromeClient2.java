package cn.pear.cartoon.components;

import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.ProgressBar;

import cn.pear.cartoon.R;

/**
 * Created by liuliang on 2017/6/28.
 */

public class DetailWeChromeClient2 extends WebChromeClient {
    private ProgressBar progressBar;

    public DetailWeChromeClient2(){

    }

    @Override
    public void onProgressChanged(WebView view, int newProgress) {
        super.onProgressChanged(view, newProgress);
        View parentView = (View)view.getParent();
        progressBar = (ProgressBar)parentView.findViewById(R.id.test_web_progress_bar);
        progressBar.setProgress(newProgress);
    }

    @Override
    public void onReceivedTitle(WebView view, String title) {
        super.onReceivedTitle(view, title);
    }
}
