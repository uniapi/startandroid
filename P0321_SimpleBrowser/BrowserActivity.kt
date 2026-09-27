/* \uFDFD
 *                     \u0648\u0671\u0630\u0643\u0631 \u0631\u0628\u0651\u0643
 *        \u0643\u062A\u0628\u0647 \u0639\u0628\u062F \u0671\u0644\u0643\u0631\u064A\u0645
 *  \u06F1\u06F4\u06F4\u06F8 \u0631\u0628\u064A\u0639 \u0671\u0644\u0622\u062E\u0631 \u06F1\u06F5
 */
package localhost.idroid.salamun

import android.util.Log
import android.content.Intent
import android.net.Uri
import android.app.Activity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup.LayoutParams
import android.widget.LinearLayout
import android.webkit.WebView
import android.webkit.WebViewClient
import android.graphics.Bitmap


class BrowserActivity : Activity() {
	private companion object {
		val TAG = "localhost.idroid.salamun"
	}
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val rootLayout = LinearLayout(this).apply {
            layoutParams = LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT
            )
            orientation = LinearLayout.VERTICAL
        }
		val webView = WebView(this).apply {
			layoutParams = LinearLayout.LayoutParams(
				LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT
			)
			setWebViewClient(object : WebViewClient() {
				override fun onPageStarted(view: WebView?, url: String?, favpicto: Bitmap?) {
					Log.d(TAG, "WebViewClient: onPageStarted()")
					super.onPageStarted(view, url, favpicto)
				}
				override fun onPageFinished(view: WebView?, url: String?) {
					Log.d(TAG, "WebViewClient: onPageFinished()")
					super.onPageFinished(view, url)
				}
			})
			loadUrl(intent.getData().toString())
		}
		with(rootLayout) {
			addView(webView)
		}
		setContentView(rootLayout)
	}
}
