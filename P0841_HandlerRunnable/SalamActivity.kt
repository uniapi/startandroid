/* \uFDFD
 *                     \u0648\u0671\u0630\u0643\u0631 \u0631\u0628\u0651\u0643
 *        \u0643\u062A\u0628\u0647 \u0639\u0628\u062F \u0671\u0644\u0643\u0631\u064A\u0645
 *  \u06F1\u06F4\u06F4\u06F8 \u0631\u0628\u064A\u0639 \u0671\u0644\u0622\u062E\u0631 \u06F2\u06F8
 */
package localhost.idroid.salamun

import java.util.concurrent.TimeUnit

import android.util.Log
import android.app.Activity
import android.os.Handler
import android.os.Bundle
import android.view.View
import android.view.Gravity
import android.view.ViewGroup.LayoutParams
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.CheckBox
import android.widget.ProgressBar
import android.widget.CompoundButton
import android.widget.CompoundButton.OnCheckedChangeListener

class SalamActivity : Activity() {
	private companion object {
		const val TAG = "localhost.idroid.salamun"
	}
	private lateinit var pbCount: ProgressBar
	private lateinit var tvInfo: TextView
	private lateinit var cbInfo: CheckBox
	private lateinit var h: Handler
	private var max = 100
	private var cnt = 0

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		val rootLayout = LinearLayout(this).apply {
			layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
			orientation = LinearLayout.VERTICAL
		}
		val loParams = LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
		pbCount = ProgressBar(this, null, android.R.attr.progressBarStyleHorizontal).apply {
			layoutParams = LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT).apply {
				setMargins(0, dpToPx(this@SalamActivity, 20f), 0, 0)
			}
		}
		cbInfo = CheckBox(this).apply { text = getString(R.string.info) }
		tvInfo = TextView(this).apply { visibility = View.GONE }
		with(rootLayout) {
			addView(pbCount)
			addView(cbInfo, loParams)
			addView(tvInfo, loParams)
		}
		setContentView(rootLayout)

		h = Handler()
		showInfo = Runnable {
			Log.d(TAG, "showInfo")
			tvInfo.text = "Count = $cnt"
			h.postDelayed(showInfo, 1000)
		}
		pbCount.max = max
		pbCount.progress = 0
		cbInfo.setOnCheckedChangeListener { buttonView: CompoundButton, isChecked: Boolean ->
			if (isChecked) {
				tvInfo.visibility = View.VISIBLE
				h.post(showInfo)
			}
			else {
				tvInfo.visibility = View.GONE
				h.removeCallbacks(showInfo)
			}
		}
		Thread {
			try {
				cnt = 1
				while (cnt < max) {
					TimeUnit.MILLISECONDS.sleep(100)
					h.post(updateProgress)
					cnt++
				}
			} catch (e: InterruptedException) {
				e.printStackTrace()
			}
		}.start()
	}
	private val updateProgress = Runnable {
		pbCount.progress = cnt
	}
	private lateinit var showInfo: Runnable
}
