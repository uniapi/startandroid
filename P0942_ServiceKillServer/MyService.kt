/* \uFDFD
 *                           \u0648\u0671\u0630\u0643\u0631 \u0631\u0628\u0651\u0643
 *              \u0643\u062A\u0628\u0647 \u0639\u0628\u062F \u0671\u0644\u0643\u0631\u064A\u0645
 *  \u06F1\u06F4\u06F4\u06F8 \u062C\u0645\u0627\u062F\u0649 \u0627\u0644\u0623\u0648\u0644\u0649 \u06F1
 */
package localhost.idroid.salamun.service

import java.util.concurrent.TimeUnit
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

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
		Log.d(TAG, "MyService onCreate")
	}
	override fun onDestroy() {
		super.onDestroy()
		Log.d(TAG, "MyService onDestroy()")
	}
	override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
		Log.d(TAG, "MyService onStartCommand, name ${intent?.getStringExtra("name")}")
		readFlags(flags)
		val mr = MyRun(startId)
		Thread(mr).start()
		return START_REDELIVER_INTENT
	}
	override fun onBind(intent: Intent?): IBinder? = null
	private fun readFlags(flags: Int) {
		if ((flags and START_FLAG_REDELIVERY) == START_FLAG_REDELIVERY)
			Log.d(TAG, "START_FLAG_REDELIVERY")
		if ((flags and START_FLAG_RETRY) == START_FLAG_RETRY)
			Log.d(TAG, "START_FLAG_RETRY")
	}
	inner class MyRun(private val startId: Int) : Runnable {
		init {
			Log.d(TAG, "MyRun#$startId create")
		}
		override fun run() {
			Log.d(TAG, "MyRun#$startId start")
			try {
				TimeUnit.SECONDS.sleep(20)
			} catch (e: InterruptedException) {
				e.printStackTrace()
			}
			stop()
		}
		private fun stop() {
			Log.d(TAG, "MyRun#$startId end, stopSelfResult($startId) = ${stopSelfResult(startId)}")
		}
	}
}
