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
		const val STATUS_NONE = 0		// no connection
		const val STATUS_CONNECTING = 1
		const val STATUS_CONNECTED = 2
	}
	private lateinit var h: Handler
	private lateinit var tvStatus: TextView
	private lateinit var pbConnect: ProgressBar
	private lateinit var btnConnect: Button
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		val rootLayout = LinearLayout(this).apply {
			layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
			orientation = LinearLayout.VERTICAL
		}
		val params = LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)

		btnConnect = Button(this).apply { text = getString(R.string.connect); setOnClickListener(this@SalamActivity) }
		tvStatus = TextView(this)
		pbConnect = ProgressBar(this).apply { isIndeterminate = true; visibility = View.GONE }
		with(rootLayout) {
			addView(btnConnect, params)
			addView(tvStatus, params)
			addView(pbConnect, params)
		}
		setContentView(rootLayout)

		h = object : Handler() {
			override fun handleMessage(msg: Message) {
				when (msg.what) {
					STATUS_NONE -> {
						btnConnect.setEnabled(true)
						tvStatus.text = "Not connected"
					}
					STATUS_CONNECTING -> {
						btnConnect.setEnabled(false)
						pbConnect.visibility = View.VISIBLE
						tvStatus.text = "Connecting"
					}
					STATUS_CONNECTED -> {
						pbConnect.visibility = View.GONE
						tvStatus.text = "Connected"
					}
				}
			}
		}
		h.sendEmptyMessage(STATUS_NONE)
	}
	override fun onClick(view: View?) {
		Thread(Runnable {
			try {
				h.sendEmptyMessage(STATUS_CONNECTING)
				TimeUnit.SECONDS.sleep(2)
				h.sendEmptyMessage(STATUS_CONNECTED)
				TimeUnit.SECONDS.sleep(3)	// immitating some work
				h.sendEmptyMessage(STATUS_NONE)
			} catch (e: InterruptedException) {
				e.printStackTrace()
			}
		}).start()
	}
}
