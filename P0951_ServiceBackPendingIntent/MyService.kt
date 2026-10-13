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
import android.app.PendingIntent
import android.app.PendingIntent.CanceledException
import android.app.Service
import android.content.Intent
import android.os.IBinder

class MyService : Service() {
	private companion object {
		const val TAG = "localhost.idroid.salamun"
	}
	private lateinit var es: ExecutorService

	override fun onCreate() {
		super.onCreate()
		Log.d(TAG, "MyService onCreate")
		es = Executors.newFixedThreadPool(2)
	}
	override fun onDestroy() {
		super.onDestroy()
		Log.d(TAG, "MyService onDestroy()")
	}
	override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
		Log.d(TAG, "MyService onStartCommand")
		val time = intent?.getIntExtra(SalamActivity.PARAM_TIME, 1) ?: 1
		val pi = intent?.getParcelableExtra<PendingIntent>(SalamActivity.PARAM_PINTENT)
		val mr = MyRun(time, startId, pi)
		es.execute(mr)
		return super.onStartCommand(intent, flags, startId)
	}
	override fun onBind(intent: Intent?): IBinder? = null

	inner class MyRun(
		private val time: Int,
		private val startId: Int,
		private val pi: PendingIntent?
	) : Runnable {
		init {
			Log.d(TAG, "MyRun#$startId create")
		}
		override fun run() {
			Log.d(TAG, "MyRun#$startId start, time = $time")
			try {
				pi?.send(SalamActivity.STATUS_START)
				TimeUnit.SECONDS.sleep(time.toLong())
				val intent = Intent().putExtra(SalamActivity.PARAM_RESULT, time * 100)
				pi?.send(this@MyService, SalamActivity.STATUS_FINISH, intent)
			} catch (e: InterruptedException) {
				e.printStackTrace()
			} catch (e: CanceledException) {
				e.printStackTrace()
			}
			stop()
		}
		private fun stop() {
			Log.d(TAG, "MyRun#$startId end, stopSelfResult($startId) = ${stopSelfResult(startId)}")
		}
	}
}
