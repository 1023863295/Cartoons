package cn.pear.cartoon.test;

import android.graphics.Bitmap;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import cn.pear.cartoon.R;
import cn.pear.cartoon.global.Constants;

/**
 * Created by liuliang on 2017/6/28.
 */

public class TestWebViewClient extends WebViewClient {
    private ProgressBar progressBar;
    private TestAty testAty;

    public TestWebViewClient(TestAty testAty){
        this.testAty = testAty;
    }

    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {
        super.onPageStarted(view, url, favicon);
        View parentView = (View)view.getParent();
        progressBar = (ProgressBar)parentView.findViewById(R.id.test_web_progress_bar);
        progressBar.setVisibility(View.VISIBLE);

    }

    @Override
    public void onPageFinished(WebView view, String url) {
        super.onPageFinished(view, url);
        progressBar.setVisibility(View.GONE);
        if (url.endsWith(Constants.URL_HOST)){
            testAty.updateBtnBack(false);
        }else{
            testAty.refreshBackForwardViews();
            testAty.browserSearchView.setMode(2);
        }
    }

    @Override
    public WebResourceResponse shouldInterceptRequest(WebView view, String url) {
        return super.shouldInterceptRequest(view, url);
    }

    @Override
    public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
        return super.shouldInterceptRequest(view, request);
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
        view.loadUrl(request.toString());
        return true;
    }
}
