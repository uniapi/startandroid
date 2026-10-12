/* \uFDFD
 *                           \u0648\u0671\u0630\u0643\u0631 \u0631\u0628\u0651\u0643
 *              \u0643\u062A\u0628\u0647 \u0639\u0628\u062F \u0671\u0644\u0643\u0631\u064A\u0645
 *  \u06F1\u06F4\u06F4\u06F8 \u062C\u0645\u0627\u062F\u0649 \u0627\u0644\u0623\u0648\u0644\u0649 \u06F1
 */
package localhost.idroid.salamun

import java.util.concurrent.TimeUnit

import android.util.Log
import android.app.Service
import android.content.Intent
import android.os.IBinder

class MyService : Service() {
	private companion object {
		const val TAG = "localhost.idroid.salamun"
	}

	override fun onCreate() {
		super.onCreate()
		Log.d(TAG, "onCreate()")
	}
	override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
		Log.d(TAG, "onStartCommand")
		someTask()
		return super.onStartCommand(intent, flags, startId)
	}
	override fun onDestroy() {
		super.onDestroy()
		Log.d(TAG, "onDestroy()")
	}
	// Intent is marked as nullable (Intent?) to match standard platform SDK signatures safely
	override fun onBind(intent: Intent?): IBinder? {
		Log.d(TAG, "onBind")
		return null
	}
	fun someTask() {
		Thread {
			for (i in 1..5) {
				Log.d(TAG, "i = $i")
				try {
					TimeUnit.SECONDS.sleep(1)
				} catch (e: InterruptedException) {
					e.printStackTrace()
				}
			}
			stopSelf()
		}.start()
	}
}
