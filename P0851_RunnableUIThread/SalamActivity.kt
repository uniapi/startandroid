/* \uFDFD
 *                     \u0648\u0671\u0630\u0643\u0631 \u0631\u0628\u0651\u0643
 *        \u0643\u062A\u0628\u0647 \u0639\u0628\u062F \u0671\u0644\u0643\u0631\u064A\u0645
 *  \u06F1\u06F4\u06F4\u06F8 \u0631\u0628\u064A\u0639 \u0671\u0644\u0622\u062E\u0631 \u06F2\u06F8
 */
package localhost.idroid.salamun

import java.util.concurrent.TimeUnit

import android.util.Log
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
		const val TAG = "localhost.idroid.salamun"
	}
	private lateinit var tvInfo: TextView

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		val rootLayout = LinearLayout(this).apply {
			layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
			orientation = LinearLayout.VERTICAL
		}
		tvInfo = TextView(this).apply {
			layoutParams = LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
		}
		with(rootLayout) {
			addView(tvInfo)
		}
		setContentView(rootLayout)

		Thread {
			try {
				TimeUnit.SECONDS.sleep(2)
				runOnUiThread(runn1)
				TimeUnit.SECONDS.sleep(1)
				tvInfo.postDelayed(runn3, 2000)
				tvInfo.post(runn2)
			} catch (e: InterruptedException) {
				e.printStackTrace()
			}
		}.start()
	}
	val runn1 = Runnable {
		tvInfo.text = "runn1"
	}
	val runn2 = object : Runnable {
		override fun run() {
			tvInfo.text = "runn2"
		}
	}
	val runn3 = Runnable {
		tvInfo.text = "runn3"
	}
}
