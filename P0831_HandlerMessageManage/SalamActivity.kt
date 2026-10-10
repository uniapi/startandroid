/* \uFDFD
 *                     \u0648\u0671\u0630\u0643\u0631 \u0631\u0628\u0651\u0643
 *        \u0643\u062A\u0628\u0647 \u0639\u0628\u062F \u0671\u0644\u0643\u0631\u064A\u0645
 *  \u06F1\u06F4\u06F4\u06F8 \u0631\u0628\u064A\u0639 \u0671\u0644\u0622\u062E\u0631 \u06F2\u06F8
 */
package localhost.idroid.salamun

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

class SalamActivity : Activity() {
	private companion object {
		const val TAG = "localhost.idroid.salamun"
	}
	private lateinit var h: Handler
// Using Anonymos Object:
/*	private val hc = object : Handler.Callback {
		override fun handleMessage(msg: Message): Boolean {
			Log.d(TAG, "what = ${msg.what}")
			return false
		}
	}*/
// Using SAM Conversion:
	private val hc = Handler.Callback { msg ->
		Log.d(TAG, "what = ${msg.what}")
		false
	}
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		val rootLayout = LinearLayout(this).apply {
			layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
			orientation = LinearLayout.VERTICAL
		}
		setContentView(rootLayout)

		h = Handler(/*Looper.getMainLooper(),*/ hc)
		sendMessages()
	}
	private fun sendMessages() {
		Log.d(TAG, "send messages")
		h.sendEmptyMessageDelayed(1, 1000)
		h.sendEmptyMessageDelayed(2, 2000)
		h.sendEmptyMessageDelayed(3, 3000)
		h.sendEmptyMessageDelayed(2, 4000)
		h.sendEmptyMessageDelayed(5, 5000)
		h.sendEmptyMessageDelayed(2, 6000)
		h.sendEmptyMessageDelayed(7, 7000)
		h.removeMessages(2)
	}
}
