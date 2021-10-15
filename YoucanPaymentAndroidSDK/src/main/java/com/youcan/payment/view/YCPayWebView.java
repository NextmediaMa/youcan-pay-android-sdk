package com.youcan.payment.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.youcan.payment.interfaces.YCPayWebViewCallBackImpl;
import com.youcan.payment.models.YCPayResult;

import java.util.HashMap;
import java.util.regex.Pattern;

public class YCPayWebView extends WebView {

    private String listenerUrl;

    YCPayWebViewCallBackImpl webViewListener;

    public void setWebViewListener(YCPayWebViewCallBackImpl webViewListener) {
        this.webViewListener = webViewListener;
    }

    public YCPayWebView(@NonNull Context context) {
        super(context);
    }

    public YCPayWebView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.getSettings().setJavaScriptEnabled(true);
        this.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        this.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);

                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                Log.e("build_test_1", url);

                try {
                    if (url.contains("is_success=0")) {
                        HashMap<String, String> urlData = getListenUrlResult(url);

                        if (webViewListener != null) {
                            webViewListener.onPayFailure(urlData.get("message"));
                        }

                        return;
                    }

                    if (url.contains("is_success=1")) {
                        if (webViewListener != null) {
                            webViewListener.onPaySuccess();
                        }

                        return;
                    }

                    return;
                } catch (Exception exception) {
                    exception.printStackTrace();
                    if (webViewListener != null) {
                        webViewListener.onPayFailure("3Ds: error has occurred");
                    }
                }
            }
        });
    }

    public YCPayWebView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void loadResult(YCPayResult result) {
        this.listenerUrl = result.listenUrl;
        this.loadDataWithBaseURL("", result.threeDsPage, "text/html", "utf-8", null);
    }

    public void loadResultUrl(YCPayResult result) {
        this.listenerUrl = result.listenUrl;
        this.loadUrl(result.redirectUrl);
    }

    private HashMap<String, String> getListenUrlResult(String url) {
        String[] urlSplit = url.split(Pattern.quote("?"));
        if (urlSplit.length == 1) {
            return new HashMap<>();
        }
        String[] data = urlSplit[1].split("&");
        HashMap<String, String> hash = new HashMap<>();

        for (int i = 0; i < data.length; i++) {
            hash.put(data[i].split("=")[0], data[i].split("=")[1].replace("+"," "));
        }

        return hash;
    }
}