package com.example.obir.intercom3

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.KeyEvent
import android.webkit.WebResourceResponse
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Button
import android.widget.TextView
import com.google.firebase.messaging.FirebaseMessaging

class Intercom : AppCompatActivity() {
    var myWebView: WebView? = null
    var mySoundView: WebView? = null
    var btn: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intercom)

        myWebView = findViewById<WebView>(R.id.webview)
        mySoundView = findViewById<WebView>(R.id.soundview)

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
        val urlVideo = prefs.getString("video_stream", "file:///android_asset/startpage.html")
        val urlAudio = prefs.getString("audio_stream", "file:///android_asset/startpage.html")

        myWebView!!.loadUrl(urlVideo)
        mySoundView!!.loadUrl(urlAudio)

        tvresult = findViewById(R.id.tvresult) as TextView

        btn = findViewById(R.id.btn) as Button

        btn!!.setOnClickListener {
            val intent = Intent(this@Intercom, ScanActivity::class.java)
            startActivity(intent)
        }

        FirebaseMessaging.getInstance().subscribeToTopic("all")
    }
    companion object {
        var tvresult: TextView? = null
    }
    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK && myWebView!!.canGoBack()) {
            myWebView!!.goBack()
            return true
        }
        return super.onKeyDown(keyCode, event)
    }
}
