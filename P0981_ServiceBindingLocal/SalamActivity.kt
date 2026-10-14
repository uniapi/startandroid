/* \uFDFD
 *                           \u0648\u0671\u0630\u0643\u0631 \u0631\u0628\u0651\u0643
 *              \u0643\u062A\u0628\u0647 \u0639\u0628\u062F \u0671\u0644\u0643\u0631\u064A\u0645
 *  \u06F1\u06F4\u06F4\u06F8 \u062C\u0645\u0627\u062F\u0649 \u0627\u0644\u0623\u0648\u0644\u0649 \u06F3
 */
package localhost.idroid.salamun

import android.util.Log
import android.app.Activity
import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.view.View
import android.view.Gravity
import android.view.ViewGroup.LayoutParams
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Button

class SalamActivity : Activity() {
	companion object {
		const val TAG = "localhost.idroid.salamun"
	}
	private var bound = false
	private lateinit var sConn: ServiceConnection
	private lateinit var sIntent: Intent
	private lateinit var myService: MyService
	private lateinit var tvInterval: TextView
	private var interval: Long = 0

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		val llRoot = LinearLayout(this).apply {
			layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
			orientation = LinearLayout.VERTICAL
		}
		val paramsBtn = LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
		tvInterval = TextView(this).apply {
			layoutParams = LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
		}
		val btnStart = Button(this).apply {
			text = getString(R.string.start)
			setOnClickListener(onClickStart)
		}
		val btnUp = Button(this).apply {
			text = getString(R.string.up)
			setOnClickListener(onClickUp)
		}
		val btnDown = Button(this).apply {
			text = getString(R.string.down)
			setOnClickListener(onClickDown)
		}
		with(llRoot) {
			addView(tvInterval)
			addView(btnStart, paramsBtn)
			addView(btnUp, paramsBtn)
			addView(btnDown, paramsBtn)
		}
		setContentView(llRoot)

		sIntent = Intent(this, MyService::class.java)
		sConn = object : ServiceConnection {
			override fun onServiceConnected(name: ComponentName?, binder: IBinder?) {
				Log.d(TAG, "SalamActivity onServiceConnected")
				myService = (binder as MyService.MyBinder).getService()
				bound = true
			}
			override fun onServiceDisconnected(name: ComponentName?) {
				Log.d(TAG, "SalamActivity onServiceDisconnected")
				bound = false
			}
		}
	}
	override fun onStart() {
		super.onStart()
		bindService(sIntent, sConn, 0)
	}
	override fun onStop() {
		super.onStop()
		if (bound) {
			unbindService(sConn)
			bound = false
		}
	}
	val onClickStart = View.OnClickListener { _: View? ->
		startService(sIntent)
	}
	val onClickUp = View.OnClickListener { _: View? ->
		if (bound) {
			interval = myService.upInterval(500)
			tvInterval.text = "interval = $interval"
		}
	}
	val onClickDown = View.OnClickListener { _: View? ->
		if (bound) {
			interval = myService.downInterval(500)
			tvInterval.text = "interval = $interval"
		}
	}
}
