/* \uFDFD
 *                     \u0648\u0671\u0630\u0643\u0631 \u0631\u0628\u0651\u0643
 *        \u0643\u062A\u0628\u0647 \u0639\u0628\u062F \u0671\u0644\u0643\u0631\u064A\u0645
 *  \u06F1\u06F4\u06F4\u06F8 \u0631\u0628\u064A\u0639 \u0671\u0644\u0622\u062E\u0631 \u06F1\u06F5
 */
package localhost.idroid.salamun

import android.content.Intent
import android.net.Uri
import android.app.Activity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup.LayoutParams
import android.widget.LinearLayout
import android.widget.Button

class SalamActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val rootLayout = LinearLayout(this).apply {
            layoutParams = LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT
            )
            orientation = LinearLayout.VERTICAL
        }
		val btnWeb = Button(this).apply {
			layoutParams = LinearLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT
			)
			text = "web"
		}
		btnWeb.setOnClickListener { view: View? ->
			startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("http://www.ya.ru")))
		}
		with(rootLayout) {
			addView(btnWeb)
		}
		setContentView(rootLayout)
	}
}
