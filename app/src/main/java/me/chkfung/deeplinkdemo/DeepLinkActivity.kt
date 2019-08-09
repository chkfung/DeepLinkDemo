package me.chkfung.deeplinkdemo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class DeepLinkActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val deeplinkHandler = DeeplinkHandler()
        val uri = intent.data
        var path: String? = null
        uri?.let {
            path = it.path
        }
        try {
            startActivity(deeplinkHandler.getIntentForDeepLink(this, path, uri))
        } catch (e: DeeplinkHandler.LoginRequiredException) {
            startActivity(Intent(this, MainActivity::class.java))
        }
        finish()
    }
}
