/* \uFDFD
 *                           \u0648\u0671\u0630\u0643\u0631 \u0631\u0628\u0651\u0643
 *              \u0643\u062A\u0628\u0647 \u0639\u0628\u062F \u0671\u0644\u0643\u0631\u064A\u0645
 *  \u06F1\u06F4\u06F4\u06F8 \u062C\u0645\u0627\u062F\u0649 \u0627\u0644\u0623\u0648\u0644\u0649 \u06F9
 */
package localhost.idroid.salamun

import android.util.Log
import android.app.Activity
import android.os.Bundle
import android.view.View
import android.view.MotionEvent
import android.widget.TextView
import android.widget.Button

class SalamActivity : Activity(), View.OnTouchListener {
	private companion object {
		const val TAG = "localhost.idroid.salamun"
	}
	private lateinit var tvInfo: TextView
	private val sb = StringBuilder()
	private var upPI = 0
	private var downPI = 0
	private var inTouch = false
	private var result = ""

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		tvInfo = TextView(this).apply { textSize = 30f }
		setContentView(tvInfo)
		tvInfo.setOnTouchListener(this)
	}
	override fun onTouch(view: View, event: MotionEvent): Boolean {
		val actionMask = event.actionMasked
		val pointerIndex = event.actionIndex
		val pointerCount = event.pointerCount
		when (actionMask) {
			MotionEvent.ACTION_DOWN -> {		// first touch
				inTouch = true
				downPI = pointerIndex
			}
			MotionEvent.ACTION_POINTER_DOWN -> {// next touches
				downPI = pointerIndex
			}
			MotionEvent.ACTION_UP -> {			// interruption of the last touch
				inTouch = false
				sb.setLength(0)
				upPI = pointerIndex
			}
			MotionEvent.ACTION_POINTER_UP -> {	// interruption of touches
				upPI = pointerIndex
			}
			MotionEvent.ACTION_MOVE -> {
				sb.setLength(0)
				for (i in 0 until 10) {
					sb.append("Index = $i")
					if (i < pointerCount) {
						sb.append(", ID = ${event.getPointerId(i)}")
						sb.append(", X = ${event.getX(i)}")
						sb.append(", Y = ${event.getY(i)}")
					} else {
						sb.append(", ID = ")
						sb.append(", X = ")
						sb.append(", Y = ")
					}
					sb.append("\r\n")
				}
			}
		}
		result = "down: $downPI\nup: $upPI\n"
		if (inTouch) {
			result += "pointerCount = $pointerCount\n$sb"
		}
		tvInfo.text = result
		return true		// the event is handled
	}
}
