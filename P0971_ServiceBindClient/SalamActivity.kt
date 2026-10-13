/* \uFDFD
 *                           \u0648\u0671\u0630\u0643\u0631 \u0631\u0628\u0651\u0643
 *              \u0643\u062A\u0628\u0647 \u0639\u0628\u062F \u0671\u0644\u0643\u0631\u064A\u0645
 *  \u06F1\u06F4\u06F4\u06F8 \u062C\u0645\u0627\u062F\u0649 \u0627\u0644\u0623\u0648\u0644\u0649 \u06F2
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
import android.view.ViewGroup.LayoutParams
import android.widget.LinearLayout
import android.widget.Button

class SalamActivity : Activity() {
	private companion object {
		const val TAG = "localhost.idroid.salamun"
	}
	private var bound = false
	private lateinit var sConn: ServiceConnection
	private lateinit var sIntent: Intent

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		val llRoot = LinearLayout(this).apply {
			layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
			orientation = LinearLayout.VERTICAL
		}
		val paramsBtn = LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
		val btnStart = Button(this).apply {
			text = getString(R.string.start)
			setOnClickListener(onClickStart)
		}
		val btnStop = Button(this).apply {
			text = getString(R.string.stop)
			setOnClickListener(onClickStop)
		}
		val btnBind = Button(this).apply {
			text = getString(R.string.bind)
			setOnClickListener(onClickBind)
		}
		val btnUnbind = Button(this).apply {
			text = getString(R.string.unbind)
			setOnClickListener(onClickUnbind)
		}
		with(llRoot) {
			addView(btnStart, paramsBtn)
			addView(btnStop, paramsBtn)
			addView(btnBind, paramsBtn)
			addView(btnUnbind, paramsBtn)
		}
		setContentView(llRoot)

		sIntent = Intent("localhost.idroid.salamun.service.MyService")
		sConn = object : ServiceConnection {
			override fun onServiceConnected(name: ComponentName?, binder: IBinder?) {
				Log.d(TAG, "SalamActivity onServiceConnected")
				bound = true
			}
			override fun onServiceDisconnected(name: ComponentName?) {
				Log.d(TAG, "SalamActivity onServiceDisconnected")
				bound = false
			}
		}
	}
	val onClickStart = View.OnClickListener { _: View? ->
		startService(sIntent)
	}
	val onClickStop = View.OnClickListener { _: View? ->
		stopService(sIntent)
	}
	val onClickBind = View.OnClickListener { _: View? ->
		bindService(sIntent, sConn, 0 /*BIND_AUTO_CREATE*/)
	}
	val onClickUnbind = View.OnClickListener { _: View? ->
		if (!bound)
			return@OnClickListener	// Exits the lambda early safely
		unbindService(sConn)
		bound = false
	}
	override fun onDestroy() {
		super.onDestroy()
		// Explicitly call the interface method
		onClickUnbind.onClick(null)
	}
}
