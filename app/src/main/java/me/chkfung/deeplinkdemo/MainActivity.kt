package me.chkfung.deeplinkdemo

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebChromeClient
import android.webkit.WebViewClient
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    companion object {
        private const val EXTRA_WEBLINK = "EXTRA_WEBLINK"

        fun getStartIntent(context: Context, weblink: String): Intent {
            val intent = Intent(context, MainActivity::class.java)
            intent.putExtra(EXTRA_WEBLINK, weblink)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            return intent
        }
    }

    private var url: String = "https://gold.razer.com/"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        getInputData()
        initView()
    }


    private fun getInputData() {
        val intent = this.intent
        if (intent.hasExtra(EXTRA_WEBLINK))
            url = intent.getStringExtra(EXTRA_WEBLINK)
    }

    private fun initView() {
        web_view.settings.javaScriptEnabled = true
        web_view.settings.domStorageEnabled = true
        web_view.webViewClient = WebViewClient()
        web_view.webChromeClient = WebChromeClient()
        web_view.loadUrl(url)
        supportActionBar?.title = url
    }

    override fun onBackPressed() {
        if (web_view.canGoBack())
            web_view.goBack()
        else
            super.onBackPressed()
    }
}
