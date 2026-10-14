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
import android.text.TextUtils

class SalamActivity : Activity() {
	companion object {
		const val TAG = "localhost.idroid.salamun"
		const val FILE_NAME = "filename"
	}
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
		val tvInfo = TextView(this).apply {
			layoutParams = LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
		}
		with(llRoot) {
			addView(btnStart, paramsBtn)
			addView(btnStop, paramsBtn)
			addView(tvInfo)
		}
		setContentView(llRoot)

		val fileName = intent.getStringExtra(FILE_NAME)
		if (!TextUtils.isEmpty(fileName))
			tvInfo.text = fileName
	}
	val onClickStart = View.OnClickListener { _: View? ->
		startService(Intent(this, MyService::class.java))
	}
	val onClickStop = View.OnClickListener { _: View? ->
		stopService(Intent(this, MyService::class.java))
	}
}
