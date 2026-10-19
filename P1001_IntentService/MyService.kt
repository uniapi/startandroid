/* \uFDFD
 *                           \u0648\u0671\u0630\u0643\u0631 \u0631\u0628\u0651\u0643
 *              \u0643\u062A\u0628\u0647 \u0639\u0628\u062F \u0671\u0644\u0643\u0631\u064A\u0645
 *  \u06F1\u06F4\u06F4\u06F8 \u062C\u0645\u0627\u062F\u0649 \u0627\u0644\u0623\u0648\u0644\u0649 \u06F1
 */
package localhost.idroid.salamun

import java.util.concurrent.TimeUnit

import android.util.Log
import android.app.IntentService
import android.content.Intent

class MyService : IntentService("myname") {
	private companion object {
		const val TAG = "localhost.idroid.salamun"
	}

	override fun onCreate() {
		super.onCreate()
		Log.d(TAG, "MyService onCreate")
	}
	override fun onHandleIntent(intent: Intent?) {
		if (intent == null) return
		val tm = intent.getIntExtra("time", 0)
		val label = intent.getStringExtra("label")
		Log.d(TAG, "onHandleIntent start $label")
		try {
			TimeUnit.SECONDS.sleep(tm.toLong())
		} catch (e: InterruptedException) {
			Thread.currentThread().interrupt()
			return
		}
		Log.d(TAG, "onHandleIntent end $label")
	}
	override fun onDestroy() {
		super.onDestroy()
		Log.d(TAG, "MyService onDestroy()")
	}
}
