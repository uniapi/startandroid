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
	private var x: Float = 0f
	private var y: Float = 0f
	private var sDown: String? = null
	private var sMove: String? = null
	private var sUp: String? = null

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		tvInfo = TextView(this)
		setContentView(tvInfo)
		tvInfo.setOnTouchListener(this)
	}
	override fun onTouch(view: View, event: MotionEvent): Boolean {
		x = event.x
		y = event.y
		when (event.action) {
			MotionEvent.ACTION_DOWN -> {
				sDown = "Down: $x,$y"
				sMove = ""
				sUp = ""
			}
			MotionEvent.ACTION_MOVE -> sMove = "Move: $x,$y"
			MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
				sMove = ""
				sUp = "Up: $x,$y"
			}
		}
		tvInfo.text = "$sDown\n$sMove\n$sUp"
		return true		// the event is handled
	}
}
