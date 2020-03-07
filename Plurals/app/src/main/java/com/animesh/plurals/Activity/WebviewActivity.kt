package com.animesh.plurals.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.animesh.plurals.R
import android.webkit.WebViewClient
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.webkit.WebSettings
import androidx.core.app.ComponentActivity
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
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
