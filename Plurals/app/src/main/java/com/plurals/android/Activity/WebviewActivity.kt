package com.plurals.android.Activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.plurals.android.R
import android.webkit.WebViewClient
import android.webkit.WebSettings
import android.webkit.WebView


class WebviewActivity : AppCompatActivity() {
    var wb: WebView? = null

    private inner class HelloWebViewClient : WebViewClient() {
        override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
            return false
        }
    }

    /** Called when the activity is first created. */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_webview)
        wb = findViewById(R.id.webView1)

        if (intent.hasExtra("WEBVIEW_URL")){

        wb?.getSettings()?.javaScriptEnabled = true
        wb?.getSettings()?.loadWithOverviewMode = true
        wb?.getSettings()?.useWideViewPort = true
        wb?.getSettings()?.builtInZoomControls = true
        wb?.getSettings()?.pluginState = WebSettings.PluginState.ON
//        wb?.getSettings()?.setPluginsEnabled(true)
        wb?.setWebViewClient(HelloWebViewClient())
        wb?.loadUrl(intent.getStringExtra("WEBVIEW_URL"))
        }
    }
}
