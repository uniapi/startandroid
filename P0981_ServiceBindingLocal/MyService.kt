/* \uFDFD
 *                           \u0648\u0671\u0630\u0643\u0631 \u0631\u0628\u0651\u0643
 *              \u0643\u062A\u0628\u0647 \u0639\u0628\u062F \u0671\u0644\u0643\u0631\u064A\u0645
 *  \u06F1\u06F4\u06F4\u06F8 \u062C\u0645\u0627\u062F\u0649 \u0627\u0644\u0623\u0648\u0644\u0649 \u06F3
 */
package localhost.idroid.salamun

import java.util.Timer
import java.util.TimerTask

import android.util.Log
import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder

class MyService : Service() {
	private companion object {
		const val TAG = "localhost.idroid.salamun"
	}
	private val binder = MyBinder()
	private lateinit var timer: Timer
	private var tTask: TimerTask? = null
	private var interval: Long = 1000

	override fun onCreate() {
		super.onCreate()
		Log.d(TAG, "MyService onCreate")
		timer = Timer()
		schedule()
	}
	private fun schedule() {
		tTask?.cancel()	// Safely calls cancel() if tTask is not null
		if (interval > 0) {
			tTask = object : TimerTask() {
				override fun run() {
					Log.d(TAG, "run")
				}
			}
			timer.schedule(tTask, 1000, interval)
		}
	}
	fun upInterval(gap: Long): Long {
		interval += gap
		schedule()
		return interval
	}
	fun downInterval(gap: Long): Long {
		interval -= gap
		if (interval < 0)
			interval = 0
		schedule()
		return interval
	}
	override fun onBind(intent: Intent?): IBinder {
		Log.d(TAG, "MyService onBind")
		return binder
	}
	override fun onDestroy() {
		super.onDestroy()
		tTask?.cancel()
		timer.cancel()	// Commpletely stops the timer thread
		Log.d(TAG, "MyService onDestroy")
	}
	inner class MyBinder : Binder() {
		fun getService(): MyService {
			return this@MyService
		}
	}
}
