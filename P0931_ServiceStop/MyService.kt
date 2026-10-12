/* \uFDFD
 *                           \u0648\u0671\u0630\u0643\u0631 \u0631\u0628\u0651\u0643
 *              \u0643\u062A\u0628\u0647 \u0639\u0628\u062F \u0671\u0644\u0643\u0631\u064A\u0645
 *  \u06F1\u06F4\u06F4\u06F8 \u062C\u0645\u0627\u062F\u0649 \u0627\u0644\u0623\u0648\u0644\u0649 \u06F1
 */
package localhost.idroid.salamun

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
	private lateinit var es: ExecutorService
	private var someRes: Any? = null

	override fun onCreate() {
		super.onCreate()
		Log.d(TAG, "MyService onCreate()")
		es = Executors.newFixedThreadPool(3)
		someRes = Any()
	}
	override fun onDestroy() {
		super.onDestroy()
		Log.d(TAG, "MyService onDestroy()")
		// Shutdown the executor to prevent thread leaks when the service dies
		es.shutdown()
		someRes = null
	}
	override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
		Log.d(TAG, "MyService onStartCommand")
		val time = intent?.getIntExtra("time", 1) ?: 1
		val mr = MyRun(time, startId)
		es.execute(mr)
		// Note: For custom tasks, returning START_NOT_STICKY is safer than super,
        // but this retains the original implementation return target.
		return super.onStartCommand(intent, flags, startId)
	}
	override fun onBind(intent: Intent?): IBinder? = null
	inner class MyRun(private val time: Int, private val startId: Int) : Runnable {
		init {
			Log.d(TAG, "MyRun#$startId create")
		}
		override fun run() {
			Log.d(TAG, "MyRun#$startId start, time = $time")
			try {
				TimeUnit.SECONDS.sleep(time.toLong())
			} catch (e: InterruptedException) {
				e.printStackTrace()
			}
			try {
				// localRess captures the reference safely to check against Kotlin's strict nullability rules
				val localRes = someRes
				Log.d(TAG, "MyRun#$startId someRess = ${localRes!!.javaClass}")
			} catch (e: NullPointerException) {
				Log.d(TAG, "MyRun#$startId error, null pointer")
			}
			stop()
		}
		private fun stop() {
			Log.d(TAG, "MyRun#$startId end, stopSelfResult($startId) = ${stopSelfResult(startId)}")
		}
	}
}
