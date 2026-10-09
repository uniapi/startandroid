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
import android.os.Message
import android.os.Bundle
import android.view.View
import android.view.Gravity
import android.view.ViewGroup.LayoutParams
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Button
import android.widget.ProgressBar

class SalamActivity : Activity(), View.OnClickListener {
	private companion object {
		const val TAG = "localhost.idroid.salamun"
		const val ID_BTN_START = 1
		const val ID_BTN_TEST = 2
	}
	private lateinit var h: Handler
	private lateinit var tvInfo: TextView
	private lateinit var btnStart: Button
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		h = object : Handler(/*Looper.getMainLooper()*/) {
			override fun handleMessage(msg: Message) {
				tvInfo.setText("Закачано файлов: ${msg.what}")
				if (msg.what == 10)
					btnStart.setEnabled(true)
			}
		}
		val rootLayout = LinearLayout(this).apply {
			layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
			orientation = LinearLayout.VERTICAL
		}
		val params = LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
		val pbProgress = ProgressBar(this).apply { isIndeterminate = true }
		tvInfo = TextView(this).apply {
			layoutParams = LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
		}
		btnStart = Button(this).apply {
			id = ID_BTN_START
			text = getString(R.string.start)
			setOnClickListener(this@SalamActivity)
		}
		val btnTest = Button(this).apply {
			id = ID_BTN_TEST
			text = getString(R.string.test)
			setOnClickListener(this@SalamActivity)
		}
		with(rootLayout) {
			addView(pbProgress, params)
			addView(tvInfo)
			addView(btnStart, params)
			addView(btnTest, params)
		}
		setContentView(rootLayout)
	}
	override fun onClick(view: View?) {
		when (view?.id) {
			ID_BTN_START -> {
				btnStart.setEnabled(false)
				/*val t = */Thread {
					for (i in 1..10) {
						downloadFile()
						h.sendEmptyMessage(i)
						Log.d(TAG, "Закачано файлов: $i")
					}
				}.start()
			}
			ID_BTN_TEST -> Log.d(TAG, "test")
		}
	}
	private fun downloadFile() {
		try {
			TimeUnit.SECONDS.sleep(1)
		} catch (e: InterruptedException) {
			e.printStackTrace()
		}
	}
}
