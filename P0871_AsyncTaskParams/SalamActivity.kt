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

class SalamActivity : Activity() {
	private companion object {
		const val TAG = "localhost.idroid.salamun"
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
		val btnClick = Button(this).apply { setText(R.string.start); setOnClickListener(onClick) }
		tvInfo = TextView(this)
		with(rootLayout) {
			addView(btnClick, loParams)
			addView(tvInfo, loParams)
		}
		setContentView(rootLayout)
	}
	private var onClick = View.OnClickListener {
		mt = MyTask()
		mt.execute("file_path_1", "file_path_2", "file_path_3", "file_path_4")
	}
	inner class MyTask : AsyncTask<String, Int, Void?>() {
		override fun onPreExecute() {
			super.onPreExecute()
			tvInfo.text = "Begin"
		}
		override fun doInBackground(vararg urls: String): Void? {
			try {
				var cnt = 0
				for (url in urls) {
					downloadFile(url)
					publishProgress(++cnt)
				}
				TimeUnit.SECONDS.sleep(1)
			} catch (e: InterruptedException) {
				e.printStackTrace()
			}
			return null
		}
		override fun onProgressUpdate(vararg values: Int?) {
			super.onProgressUpdate(*values)
			tvInfo.text = "Downloaded ${values[0]} files"
		}
		override fun onPostExecute(result: Void?) {
			super.onPostExecute(result)
			tvInfo.text = "End"
		}
		private fun downloadFile(url: String) {
			TimeUnit.SECONDS.sleep(2)
		}
	}
}
