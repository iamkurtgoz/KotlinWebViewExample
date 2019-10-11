package com.iamkurtgoz.webviewexample

import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.webkit.*
import android.widget.ProgressBar

class MainActivity : AppCompatActivity() {

    private val WEB_ADDRESS = "https://webdeyazilim.com/"
    var webView: WebView? = null
    var progressBar: ProgressBar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initWebViewWithHtml()
    }

    private fun initSettings(webView: WebView){
        var settings: WebSettings = webView.settings;

        settings.setSupportMultipleWindows(true) //Çoklu ekran desteği aktif
        settings.javaScriptEnabled = true //JavaScript desteği aktif
        settings.setSupportZoom(true) //Yakınlaştırma aktif
        settings.builtInZoomControls = true //yakınlaşırma kontrolleri aktif
        settings.displayZoomControls = true //yakınlaştırma kontrollerini gösterme
        settings.setAppCacheEnabled(true) //Önbellek aktif
        settings.cacheMode = WebSettings.LOAD_DEFAULT //Önbellek modunu varsayılan olarak ayarladık
        settings.javaScriptCanOpenWindowsAutomatically = true //ek javascript ayarı - pencere izni
    }

    inner class CustomWebViewClient: WebViewClient() {
        override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
            super.onPageStarted(view, url, favicon)
            //Sayfa yüklenmeye başladığında çalışır.
            //ProgressBar görünümünü Visible durumuna getiriyoruz.
            progressBar!!.visibility = View.VISIBLE
        }

        override fun onPageFinished(view: WebView?, url: String?) {
            super.onPageFinished(view, url)
            //Sayfa tamamen yüklendiğinde çalışır
            //ProgressBar görünümünü Gone durumuna getiriyoruz.
            progressBar!!.visibility = View.GONE
        }

        override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
            //Yönlendirme, yeni bir sayfaya geçiş sırasında çalışır.
            if (url!!.contains(WEB_ADDRESS)){ //Eğer yönlendirilen sayfa webadresimizle uyuşuyorsa ilerlemesine izin verioruz
                webView?.loadUrl(url)
                return true
            }
            return false
        }
    }

    inner class CustomWebChromeClient: WebChromeClient(){
        override fun onProgressChanged(view: WebView?, newProgress: Int) {
            super.onProgressChanged(view, newProgress)
            //Sayfa yüklenirken yüklenme değeri anlık olarak değişir.
            //Yükleme değerini progressBar görünümünde göstermek için onProgressChanged kullanıyoruz
            progressBar!!.progress = newProgress
        }
    }

    private fun initWebViewWithWebAdress(){
        webView = findViewById(R.id.activity_main_webView);
        progressBar = findViewById(R.id.activity_main_progressBar)
        initSettings(webView!!) //Ayarları webview'e ekledik.

        webView!!.webViewClient = CustomWebViewClient()
        webView!!.webChromeClient = CustomWebChromeClient()

        webView!!.loadUrl(WEB_ADDRESS)
    }

    private fun initWebViewWithHtml(){
        webView = findViewById(R.id.activity_main_webView);
        progressBar = findViewById(R.id.activity_main_progressBar)
        initSettings(webView!!) //Ayarları webview'e ekledik.

        webView!!.webViewClient = CustomWebViewClient()
        webView!!.webChromeClient = CustomWebChromeClient()

        var htmlData: String = "<html><title>WebdeYazilim</title><body><p>Bu uygulama <a href=\"https://webdeyazilim.com/android-webview-kullanimi-kotlin.html\">WebdeYazılım</a> tarafından geliştirilmiştir.</p></body></html>";
        webView!!.loadData(htmlData, "text/html; charset=utf-8", "UTF-8");
    }
}
