package com.z_martin.mylibrary.widgets;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.webkit.JsResult;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.z_martin.mylibrary.R;


/**
 * @ describe:
 * @ author: Martin
 * @ createTime: 2018/11/14 18:35
 * @ version: 1.0
 */
public class CustomWebView extends WebView {

    private ProgressBar mProgressBar;
    private OnWebViewLoadListener listener;
    
    private loadListener mLoadListener;

    private boolean isLoadError;

    public CustomWebView(Context context) {
        this(context, null);
    }

    public CustomWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
        addProgressBar(context);
        initDefaultWebSettings();
        setWebChromeClient(new WebChromeClient());
        setWebViewClient(new GIWebViewClient());
        requestFocus();
    }


    @SuppressLint("SetJavaScriptEnabled")
    public WebSettings initDefaultWebSettings() {
        WebSettings webSettings = null;
        if (!isInEditMode()) {
            webSettings = getSettings();
            webSettings.setSavePassword(false);
            webSettings.setSaveFormData(false);
            webSettings.setJavaScriptEnabled(true);
            webSettings.setDomStorageEnabled(true);
            webSettings.setLoadWithOverviewMode(true);
            webSettings.setUseWideViewPort(true);
            webSettings.setBuiltInZoomControls(true);
            webSettings.setDisplayZoomControls(false);
            webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
            webSettings.setAppCacheEnabled(false); // 设置缓存不开启
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                webSettings.setAllowFileAccessFromFileURLs(false);
                webSettings.setAllowUniversalAccessFromFileURLs(true);
            }
        }
        return webSettings;
    }

    //初始化进度条并且添加，因为WebView是一个ViewGroup
    private void addProgressBar(Context context) {
        mProgressBar = new ProgressBar(context, null, android.R.attr.progressBarStyleHorizontal);
        int height = 8;
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, height);
        mProgressBar.setLayoutParams(layoutParams);
        Drawable drawable = context.getResources().getDrawable(R.drawable.web_progress_bar_states);
        mProgressBar.setProgressDrawable(drawable);
        addView(mProgressBar);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        invalidate();
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    //监听加载结果
    class GIWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            if (listener != null && !isLoadError) {
                listener.onLoadSuccess(url);
            }
        }

        @Override
        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            super.onReceivedError(view, errorCode, description, failingUrl);
            //6.0以下执行
            isLoadError = true;
            if (listener != null)
                listener.onLoadError();
        }

        //处理网页加载失败时
        @Override
        public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
            super.onReceivedError(view, request, error);
            //6.0以上执行
            isLoadError = true;
            if (listener != null)
                listener.onLoadError();
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            if (listener != null)
                listener.onLoadStart(view);
        }
    }

    //监听加载过程
    class WebChromeClient extends android.webkit.WebChromeClient {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            if (newProgress == 100) {
                mProgressBar.setVisibility(GONE);
                if (mLoadListener != null) {
                    mLoadListener.loadOver();
                }
            } else {
                if (mProgressBar.getVisibility() == GONE) {
                    mProgressBar.setVisibility(VISIBLE);
                }
                mProgressBar.setProgress(newProgress);
            }
            super.onProgressChanged(view, newProgress);
        }

        @Override
        public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);
            if (title.contains("404")) {
                if (listener != null)
                    listener.onLoadStart(view);
            } else {
                if (listener != null)
                    listener.titleChang(title + "");
            }
        }

        /**
         * 显示提示信息
         *
         * @param view
         * @param url
         * @param message
         * @param result
         * @return
         */
        public boolean onJsAlert(WebView view, String url, String message, final JsResult result) {
            result.confirm();
            return true;
        }

    }


    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        LayoutParams lp = (LayoutParams) mProgressBar.getLayoutParams();
        lp.x = l;
        lp.y = t;
        mProgressBar.setLayoutParams(lp);
        super.onScrollChanged(l, t, oldl, oldt);
    }


    public interface OnWebViewLoadListener {
        /**
         * 加载路径成功
         */
        void onLoadSuccess(String url);

        /**
         * 加载路径失败
         */
        void onLoadError();

        /**
         * 开始加载
         */
        void onLoadStart(WebView view);
        
        /** title 改变 */
        void titleChang(String title);
    }
    
    public interface loadListener{
        
        /** 加载结束 */
        void loadOver();
    }
    
    

    public void setOnWebViewLoadListener(OnWebViewLoadListener listener) {
        this.listener = listener;
    }

    public void setLoadListener(loadListener loadListener) {
        mLoadListener = loadListener;
    }

    /**
     * 加载路径的方法，直接调用就可以了
     *
     * @param path
     */
    public void load(String path) {
        isLoadError = false;
        loadUrl(path);
    }
}
