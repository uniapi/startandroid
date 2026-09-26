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
import android.view.Gravity
import android.view.ViewGroup.LayoutParams
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Button

class SalamActivity : Activity() {
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
            orientation = LinearLayout.HORIZONTAL
        }
		val btnWeb = Button(this).apply {
			layoutParams = LinearLayout.LayoutParams(
				LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT,
				1.0f
			).apply { setMargins(10, 10, 10, 10) }
			text = "Web"
		}
		val btnMap = Button(this).apply {
			layoutParams = LinearLayout.LayoutParams(
				LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT,
				1.0f
			).apply { setMargins(10, 10, 10, 10) }
			text = "Map"
		}
		val btnCall = Button(this).apply {
			layoutParams = LinearLayout.LayoutParams(
				LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT,
				1.0f
			).apply { setMargins(10, 10, 10, 10) }
			text = "Call"
		}
		with(rootLayout) {
			addView(btnWeb)
			addView(btnMap)
			addView(btnCall)
		}
		btnWeb.setOnClickListener { _: View? ->
			startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("http://developer.android.com")))
		}
		btnMap.setOnClickListener { _: View? ->
			with(Intent()) {
				setAction(Intent.ACTION_VIEW)
				setData(Uri.parse("geo:55.754283,37.62002"))
				startActivity(this)
			}
		}
		btnCall.setOnClickListener { _: View? ->
			with(Intent(Intent.ACTION_DIAL)) {
				setData(Uri.parse("tel:12345"))
				startActivity(this)
			}
		}
		setContentView(rootLayout)
	}
}
