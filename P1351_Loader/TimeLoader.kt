/* \uFDFD
 *                           \u0648\u0671\u0630\u0643\u0631 \u0631\u0628\u0651\u0643
 *              \u0643\u062A\u0628\u0647 \u0639\u0628\u062F \u0671\u0644\u0643\u0631\u064A\u0645
 *  \u06F1\u06F4\u06F4\u06F8 \u062C\u0645\u0627\u062F\u0649 \u0627\u0644\u0622\u062E\u0631\u0629 \u06F2
 */
package localhost.idroid.salamun

import java.util.concurrent.TimeUnit
import java.util.Date
import java.text.SimpleDateFormat

import android.util.Log
import android.app.Activity
import android.content.Context
import android.content.Loader
import android.os.AsyncTask
import android.os.Bundle
import android.text.TextUtils

class TimeLoader(context: Context, args: Bundle?) : Loader<String>(context) {
	companion object {
		private const val TAG = "localhost.idroid.salamun"
		const val ARGS_TIME_FORMAT = "time_format"
		const val TIME_FORMAT_SHORT = "h:mm:ss a"
		const val TIME_FORMAT_LONG = "yyyy.MM.dd G 'at' HH:mm:ss"
	}
	private val pause = 10L
	private var getTimeTask: GetTimeTask? = null
	private var format: String? = null

	init {
		Log.d(TAG, "${hashCode()} create TimeLoader")
		if (args != null) {
			format = args.getString(ARGS_TIME_FORMAT)
		}
		if (TextUtils.isEmpty(format)) {
			format = TIME_FORMAT_SHORT
		}
	}
	override fun onStartLoading() {
		super.onStartLoading()
		Log.d(TAG, "${hashCode()} onStartLoading")
		if (takeContentChanged()) {
			forceLoad()
		}
	}
	override fun onStopLoading() {
		super.onStopLoading()
		Log.d(TAG, "${hashCode()} onStopLoading")
	}
	override fun onForceLoad() {
		super.onForceLoad()
		Log.d(TAG, "${hashCode()} onForceLoad")
		getTimeTask?.cancel(true)
		getTimeTask = GetTimeTask().apply {
			executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, format)
		}
	}
	override fun onAbandon() {
		super.onAbandon()
		Log.d(TAG, "${hashCode()} onAbandon")
	}
	override fun onReset() {
		super.onReset()
		Log.d(TAG, "${hashCode()} onReset")
	}
	internal fun getResultFromTask(result: String?) {
		deliverResult(result)
	}
	inner class GetTimeTask : AsyncTask<String, Void, String?>() {
		override fun doInBackground(vararg params: String?): String? {
			Log.d(TAG, "${this@TimeLoader.hashCode()} doInBackground")
			try {
				TimeUnit.SECONDS.sleep(pause)
			} catch (e: InterruptedException) {
				return null
			}
			val pattern = params[0] ?: TIME_FORMAT_SHORT
			val sdf = SimpleDateFormat(pattern)
			return sdf.format(Date())
		}
		override fun onPostExecute(result: String?) {
			super.onPostExecute(result)
			Log.d(TAG, "${this@TimeLoader.hashCode()} onPostExecute $result")
			getResultFromTask(result)
		}
	}
}
