package com.example.obir.intercom3

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.KeyEvent
import android.webkit.WebResourceResponse
import android.webkit.WebView
import android.webkit.WebViewClient
import com.google.firebase.messaging.FirebaseMessaging


class Intercom : AppCompatActivity() {
    var myWebView: WebView? = null
    var URL = "https://www.google.com/"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intercom)

        myWebView = findViewById<WebView>(R.id.webview)

        myWebView!!.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                return false
            }

            override fun shouldInterceptRequest(view: WebView?, url: String?): WebResourceResponse? {
                return null
            }
        }

        myWebView!!.settings.javaScriptEnabled = true
        myWebView!!.clearCache(true)

        val prefs = getSharedPreferences("com.example.obir.intercom3.PREFERENCE_FILE_KEY", Context.MODE_PRIVATE) ?: return
        val url = prefs.getString("url", "https://google.com")

        myWebView!!.loadUrl(url)

        FirebaseMessaging.getInstance().subscribeToTopic("all")
    }
    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK && myWebView!!.canGoBack()) {
            myWebView!!.goBack()
            return true
        }
        return super.onKeyDown(keyCode, event)
    }
}
