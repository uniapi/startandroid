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
import android.widget.Toast

class SalamActivity : Activity() {
	private companion object {
		const val TAG = "localhost.idroid.salamun"
	}
	private lateinit var mt: MyTask
	lateinit var tvInfo: TextView

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		val rootLayout = LinearLayout(this).apply {
			layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
			orientation = LinearLayout.VERTICAL
		}
		val loParams = LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
		tvInfo = TextView(this)
		with(rootLayout) {
			addView(tvInfo, loParams)
		}
		setContentView(rootLayout)

		Log.d(TAG, "create MainActivity: ${this.hashCode()}")
		mt = getLastNonConfigurationInstance() as? MyTask ?: MyTask().apply { execute() }
		mt.link(this)
		Log.d(TAG, "create MyTask: ${mt.hashCode()}")
	}
	override fun onRetainNonConfigurationInstance(): Any {
		return mt.apply { unlink() }
	}
	class MyTask : AsyncTask<String, Int, Void?>() {
		private var activity: SalamActivity? = null
		fun link(act: SalamActivity) {
			activity = act
		}
		fun unlink() {
			activity = null
		}
		override fun doInBackground(vararg params: String?): Void? {
			try {
				for (i in 1..10) {
					TimeUnit.SECONDS.sleep(1)
					publishProgress(i)
					Log.d(TAG, "i = $i, MyTask: ${this.hashCode()}, SalamActivity: ${activity.hashCode()}")
				}
			} catch (e: InterruptedException) {
				e.printStackTrace()
			}
			return null
		}
		override fun onProgressUpdate(vararg values: Int?) {
			super.onProgressUpdate(*values)
			activity?.tvInfo?.text = "i = ${values[0]}"
		}
	}
}
