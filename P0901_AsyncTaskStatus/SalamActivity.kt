/* \uFDFD
 *                     \u0648\u0671\u0630\u0643\u0631 \u0631\u0628\u0651\u0643
 *        \u0643\u062A\u0628\u0647 \u0639\u0628\u062F \u0671\u0644\u0643\u0631\u064A\u0645
 *  \u06F1\u06F4\u06F4\u06F8 \u0631\u0628\u064A\u0639 \u0671\u0644\u0622\u062E\u0631 \u06F2\u06F8
 */
package localhost.idroid.salamun

import java.util.concurrent.TimeUnit

import android.util.Log
import android.app.Activity
import android.os.AsyncTask
import android.os.Bundle
import android.view.View
import android.view.Gravity
import android.view.ViewGroup.LayoutParams
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Button
import android.widget.ProgressBar
import android.widget.Toast

class SalamActivity : Activity() {
	private companion object {
		const val TAG = "localhost.idroid.salamun"
		const val ID_BTN_START = 1
		const val ID_BTN_STATUS = 2
	}
	private lateinit var mt: MyTask
	private lateinit var tvInfo: TextView

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		val rootLayout = LinearLayout(this).apply {
			layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
			orientation = LinearLayout.VERTICAL
		}
		val loParams = LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
		val btnStart = Button(this).apply { id = ID_BTN_START; setText(R.string.start); setOnClickListener(onClick) }
		val btnStatus = Button(this).apply { id = ID_BTN_STATUS; setText(R.string.status); setOnClickListener(onClick) }
		tvInfo = TextView(this)
		with(rootLayout) {
			addView(btnStart, loParams)
			addView(btnStatus, loParams)
			addView(tvInfo, loParams)
		}
		setContentView(rootLayout)
	}
	private var onClick = View.OnClickListener { view: View? ->
		when (view?.id) {
			ID_BTN_START -> startTask()
			ID_BTN_STATUS -> showStatus()
		}
	}
	private fun startTask() {
		mt = MyTask()
		mt.execute()
		mt.cancel(false)
	}
	private fun showStatus() {
		if (mt.isCancelled())
			Toast.makeText(this, "CANCELLED", Toast.LENGTH_SHORT).show()
		else
			Toast.makeText(this, mt.getStatus().toString(), Toast.LENGTH_SHORT).show()
	}
	inner class MyTask : AsyncTask<Void?, Void?, Void?>() {
		override fun onPreExecute() {
			super.onPreExecute()
			tvInfo.text = "Begin"
		}
		override fun doInBackground(vararg params: Void?): Void? {
			try {
				for (i in 0..4) {
					if (isCancelled())
						return null
					TimeUnit.SECONDS.sleep(1)
				}
			} catch (e: InterruptedException) {
				e.printStackTrace()
			}
			return null
		}
		override fun onPostExecute(result: Void?) {
			super.onPostExecute(result)
			tvInfo.text = "End"
		}
		override fun onCancelled() {
			super.onCancelled()
			tvInfo.text = "Cancel"
		}
	}
}
