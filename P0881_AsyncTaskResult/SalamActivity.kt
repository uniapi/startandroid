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
		const val ID_BTN_GET = 2
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
		val btnGet = Button(this).apply { id = ID_BTN_GET; setText(R.string.get); setOnClickListener(onClick) }
		val pbProgress = ProgressBar(this)
		tvInfo = TextView(this)
		with(rootLayout) {
			addView(btnStart, loParams)
			addView(btnGet, loParams)
			addView(pbProgress, loParams)
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
			ID_BTN_GET -> showResult()
		}
	}
	private fun showResult() {
		if (mt == null)
			return
		var result = -1
		try {
			Log.d(TAG, "Try to get result")
			result = mt.get(1, TimeUnit.SECONDS)
			Log.d(TAG, "get returns $result")
			Toast.makeText(this, "get returns $result", Toast.LENGTH_SHORT).show()
		} catch (e: InterruptedException) {
			e.printStackTrace()
		} catch (e: ExecutionException) {
			e.printStackTrace()
		} catch (e: TimeoutException) {
			Log.d(TAG, "get timeout, result = $result")
		}
	}
	inner class MyTask : AsyncTask<Void?, Void?, Int>() {
		override fun onPreExecute() {
			super.onPreExecute()
			tvInfo.text = "Begin"
			Log.d(TAG, "Begin")
		}
		override fun doInBackground(vararg params: Void?): Int {
			try {
				TimeUnit.SECONDS.sleep(5)
			} catch (e: InterruptedException) {
				e.printStackTrace()
			}
			return 100500
		}
		override fun onPostExecute(result: Int) {
			super.onPostExecute(result)
			tvInfo.text = "End. Result = $result"
			Log.d(TAG, "End. Result = $result")
		}
	}
}
