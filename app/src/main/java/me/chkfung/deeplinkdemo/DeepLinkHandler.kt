package me.chkfung.deeplinkdemo

import android.content.Context
import android.content.Intent
import android.net.Uri


class DeeplinkHandler {
    companion object {
        const val HOME = "/ho"
        const val PROMO = "/promo"
        const val ACTIVATE = "/activate"
        const val ACCOUNT = "/account"
        const val WEBLINK = "/weblink"
    }

    private fun isLoginRequiredForDeeplink(path: String?, uri: Uri): Boolean {
        if (path.isNullOrEmpty()) {
            return false
        }
        return when (path) {
            HOME, PROMO, ACTIVATE, ACCOUNT, WEBLINK -> false
            else -> false
        }
    }

    fun getIntentForDeepLink(
        context: Context,
        path: String?,
        uri: Uri
    ): Intent {
        var intent: Intent
        if (isLoginRequiredForDeeplink(path, uri))
            throw LoginRequiredException()
        if (uri.scheme.equals("https"))
            intent = MainActivity.getStartIntent(context, uri.toString())
        else
            when (path) {
                HOME -> intent = MainActivity.getStartIntent(context, "https://gold.razer.com")
                PROMO -> intent =
                    MainActivity.getStartIntent(context, "https://gold.razer.com/silver/promotions")
                ACTIVATE -> intent =
                    MainActivity.getStartIntent(context, "https://gold.razer.com/silver/redeem")
                ACCOUNT -> intent =
                    MainActivity.getStartIntent(context, "https://gold.razer.com/help?section=3")
                WEBLINK -> {
                    val url = uri.getQueryParameter("url")
                    if (url.isNullOrEmpty())
                        intent = MainActivity.getStartIntent(
                            context,
                            "https://gold.razer.com/help?section=3"
                        )
                    else
                        intent = MainActivity.getStartIntent(context, url)
                }
                else -> intent = Intent(context, MainActivity::class.java)
            }


        return intent
    }

    class InvalidDeepLinkException internal constructor() : Exception()

    class LoginRequiredException internal constructor() : Exception()
}


/*
To Test Deep Link, you can use ADB to send the intent:
adb shell am start -a android.intent.action.VIEW -d "DEEP_LINK_HERE"

demo://gold.razer.com/home
demo://gold.razer.com/promo
demo://gold.razer.com/activate
demo://gold.razer.com/account
demo://gold.razer.com/weblink?url=https://www.google.com



*/