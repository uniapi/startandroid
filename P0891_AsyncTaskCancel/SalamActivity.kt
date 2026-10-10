/* \uFDFD
 *                     \u0648\u0671\u0630\u0643\u0631 \u0631\u0628\u0651\u0643
 *        \u0643\u062A\u0628\u0647 \u0639\u0628\u062F \u0671\u0644\u0643\u0631\u064A\u0645
 *  \u06F1\u06F4\u06F4\u06F8 \u0631\u0628\u064A\u0639 \u0671\u0644\u0622\u062E\u0631 \u06F2\u06F8
 */
package localhost.idroid.salamun

import java.util.concurrent.TimeUnit
import java.util.concurrent.ExecutionException
import java.util.concurrent.TimeoutException

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
		const val ID_BTN_CANCEL = 2
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
		val btnCancel = Button(this).apply { id = ID_BTN_CANCEL; setText(R.string.cancel); setOnClickListener(onClick) }
		tvInfo = TextView(this)
		with(rootLayout) {
			addView(btnStart, loParams)
			addView(btnCancel, loParams)
			addView(tvInfo, loParams)
		}
		setContentView(rootLayout)
	}
	private var onClick = View.OnClickListener { view: View? ->
		when (view?.id) {
			ID_BTN_START -> {
				mt = MyTask()
				mt.execute()
			}
			ID_BTN_CANCEL -> cancelTask()
		}
	}
	private fun cancelTask() {
		Log.d(TAG, "cancel result: ${mt.cancel(true)}")
	}
	inner class MyTask : AsyncTask<Void?, Void?, Void?>() {
		override fun onPreExecute() {
			super.onPreExecute()
			tvInfo.text = "Begin"
			Log.d(TAG, "Begin")
		}
		override fun doInBackground(vararg params: Void?): Void? {
			try {
				for (i in 0..4) {
					TimeUnit.SECONDS.sleep(1)
				//	if (isCancelled())
				//		return null
					Log.d(TAG, "isCancelled:  ${isCancelled()}")
				}
			} catch (e: InterruptedException) {
				e.printStackTrace()
			}
			return null
		}
		override fun onPostExecute(result: Void?) {
			super.onPostExecute(result)
			tvInfo.text = "End"
			Log.d(TAG, "End")
		}
		override fun onCancelled() {
			super.onCancelled()
			tvInfo.text = "Cancel"
			Log.d(TAG, "Cancel")
		}
	}
}
