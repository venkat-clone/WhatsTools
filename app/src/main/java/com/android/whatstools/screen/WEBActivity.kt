package com.android.whatstools.screen

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.webkit.WebView
import android.webkit.WebViewClient
import com.android.whatstools.R
import com.android.whatstools.databinding.ActivityWebactivityBinding

class WEBActivity : AppCompatActivity() {
    lateinit var binding:ActivityWebactivityBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWebactivityBinding.inflate(LayoutInflater.from(this))
        binding.lifecycleOwner=this

        setContentView(binding.root)
        setWebView(binding.webView)

    }


    fun setWebView(webView: WebView){
        webView.webViewClient = WebViewClient()
        webView.settings.javaScriptEnabled = true
        webView.settings.domStorageEnabled = true
        webView.settings.loadWithOverviewMode= true

        webView.settings.userAgentString ="Mozilla/5.0 (X11; U; Linux i686; en-US) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/90.0.2357.121 Safari/537.36"
        webView.loadUrl("https://web.whatsapp.com")

    }


}