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
import android.content.AsyncTaskLoader
import android.content.Context
import android.os.Bundle
import android.text.TextUtils

class TimeAsyncLoader(context: Context, args: Bundle?) : AsyncTaskLoader<String>(context) {
	companion object {
		private const val TAG = "localhost.idroid.salamun"
		const val ARGS_TIME_FORMAT = "time_format"
		const val TIME_FORMAT_SHORT = "h:mm:ss a"
		const val TIME_FORMAT_LONG = "yyyy.MM.dd G 'at' HH:mm:ss"
	}
	private val pause = 10L
	private var format: String? = null

	init {
		Log.d(TAG, "${hashCode()} create TimeAsyncLoader")
		if (args != null) {
			format = args.getString(ARGS_TIME_FORMAT)
		}
		if (TextUtils.isEmpty(format)) {
			format = TIME_FORMAT_SHORT
		}
	}
	override fun loadInBackground(): String? {
		Log.d(TAG, "${hashCode()} loadInBackground start")
		try {
			TimeUnit.SECONDS.sleep(pause)
		} catch (e: InterruptedException) {
			return null
		}
		val sdf = SimpleDateFormat(format)
		return sdf.format(Date())
	}
}
