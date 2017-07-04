package cn.pear.cartoon.test;

import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.ProgressBar;

import cn.pear.cartoon.R;
import cn.pear.cartoon.global.Constants;

/**
 * Created by liuliang on 2017/6/28.
 */

public class TestWeChromeClient extends WebChromeClient {
    private ProgressBar progressBar;
    private TestAty testAty;

    public TestWeChromeClient(TestAty testAty){
        this.testAty = testAty;

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
        if (!view.getUrl().endsWith(Constants.URL_HOST)){
            testAty.browserSearchView.editTextUrl.setText(title);
        }
    }
}
