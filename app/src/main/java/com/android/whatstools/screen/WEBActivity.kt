package com.android.whatstools.screen

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.net.ConnectivityManager
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.LayoutInflater
import android.webkit.*
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.android.whatstools.databinding.ActivityWebactivityBinding
import com.android.whatstools.screen.Web.JavaScriptInterface
import kotlinx.android.synthetic.main.activity_magic_text.view.*
import kotlinx.android.synthetic.main.activity_webactivity.*
import java.net.InetAddress


class WEBActivity : AppCompatActivity() {
    lateinit var binding:ActivityWebactivityBinding
    lateinit var url:String
    lateinit var mimetype:String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWebactivityBinding.inflate(LayoutInflater.from(this))
        binding.lifecycleOwner=this

        setContentView(binding.root)
        checkNet()
        init()
    }

    private fun checkNet(){

        if(isNetworkConnected()) {
            binding.network = true
            setWebView(binding.webView)
        }
        else{
            binding.network=false
        }

    }

    override fun onRequestPermissionsResult( requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode){
            500->{
                if (grantResults.isNotEmpty() && grantResults[0]==PackageManager.PERMISSION_GRANTED)
                    webView.loadUrl(JavaScriptInterface.getBase64StringFromBlobUrl(url,mimetype));
            }
        }

    }
    fun init(){

        binding.toolbar.setNavigationOnClickListener(){
            onBackPressed()
        }

        binding.swipe.setOnRefreshListener {
            binding.swipe.isRefreshing = false
            checkNet()
        }

        binding.retry.setOnClickListener(){
            Toast.makeText(this,"Checking",Toast.LENGTH_SHORT).show()
            checkNet()
        }

        webView.setDownloadListener(DownloadListener { url, userAgent, contentDisposition, mimetype, contentLength ->
            this.url = url
            this.mimetype = mimetype
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                Log.d("myz", "" + Build.VERSION.SDK_INT)
                if (!Environment.isExternalStorageManager()) {
                    ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.MANAGE_EXTERNAL_STORAGE), 500
                    ) //permission request code is just an int
                }
                else webView.loadUrl(JavaScriptInterface.getBase64StringFromBlobUrl(url,mimetype))
            } else {
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                ) {
                    ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE), 500)
                }
                else webView.loadUrl(JavaScriptInterface.getBase64StringFromBlobUrl(url,mimetype))
            }
//            if(applicationContext.checkCallingPermission(android.Manifest.permission_group.STORAGE)==PackageManager.PERMISSION_DENIED){
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
//                    requestPermissions(arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE),500)
//
//            }
//            else
//                webView.loadUrl(JavaScriptInterface.getBase64StringFromBlobUrl(url,mimetype))
//
////            Log.d(TAG, "downloadFile: url = $url")
//            val manager = getSystemService(Activity.DOWNLOAD_SERVICE) as DownloadManager
//
//            val uri = Uri.parse(url.replace("blob:",""))
//            Log.i("Web Activity",url.replace("blob:",""))
//            val request = DownloadManager.Request(uri)
//            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
////            request.setMimeType(mimetype)
//            request.allowScanningByMediaScanner()
//            request.setAllowedOverMetered(true)
//
//            request.setDestinationInExternalPublicDir(
//                Environment.DIRECTORY_DOWNLOADS,
//                "Downloading"
//            )
//            val reference: Long = manager.enqueue(request)
//            val request = DownloadManager.Request(
//                Uri.parse(url.trim())
//            )
//            request.allowScanningByMediaScanner()
//            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
//            request.setDestinationInExternalPublicDir(
//                Environment.DIRECTORY_DOWNLOADS,
//                "name of your_file"
//            )
//            val dm = getSystemService(DOWNLOAD_SERVICE) as DownloadManager
//            dm.enqueue(request)
            //you could show a message here
        })


    }


    fun setWebView(webView: WebView){
        webView.webViewClient = WebViewClient()
//        webView.settings.pluginState = WebSettings.PluginState.ON
//        webView.addJavascriptInterface(JavaScriptInterface(applicationContext),"Web")

        webView.loadUrl("https://web.whatsapp.com")
        webView.settings.setSupportZoom(true)
        webView.settings.builtInZoomControls = true
        webView.settings.useWideViewPort = true
        webView.settings.javaScriptEnabled = true
        webView.settings.allowFileAccess = true
        webView.settings.allowContentAccess = true
        webView.settings.domStorageEnabled = true
        webView.settings.loadWithOverviewMode= true
        webView.settings.setAppCacheEnabled(true)
        webView.settings.setSupportMultipleWindows(true)
        webView.settings.setGeolocationEnabled(true)

        webView.settings.cacheMode = WebSettings.LOAD_DEFAULT
        webView.settings.useWideViewPort = true

        webView.settings.userAgentString ="Mozilla/5.0 (X11; U; Linux i686; en-US) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/90.0.2357.121 Safari/537.36"

//        webView.settings.userAgentString ="Mozilla/5.0 (X11; U; Linux i686; en-US; rv:1.9.0.4) Gecko/20100101 Firefox/70.0"
        webView.setInitialScale(100)
        webView.addJavascriptInterface(JavaScriptInterface(applicationContext),"Web")
        webView.settings.pluginState = WebSettings.PluginState.ON
        webView.webChromeClient = WebChromeClient()
//        webView.loadUrl("https://web.whatsapp.com")

    }

    private fun isNetworkConnected(): Boolean {
        val cm = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return cm.activeNetworkInfo != null && cm.activeNetworkInfo!!.isConnected
    }


}