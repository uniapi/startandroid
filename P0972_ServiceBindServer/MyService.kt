/* \uFDFD
 *                           \u0648\u0671\u0630\u0643\u0631 \u0631\u0628\u0651\u0643
 *              \u0643\u062A\u0628\u0647 \u0639\u0628\u062F \u0671\u0644\u0643\u0631\u064A\u0645
 *  \u06F1\u06F4\u06F4\u06F8 \u062C\u0645\u0627\u062F\u0649 \u0627\u0644\u0623\u0648\u0644\u0649 \u06F2
 */
package localhost.idroid.salamun.service

import android.util.Log
import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.os.Binder

class MyService : Service() {
	private companion object {
		const val TAG = "localhost.idroid.salamun"
	}

	override fun onCreate() {
		super.onCreate()
		Log.d(TAG, "MyService onCreate")
	}
	override fun onBind(intent: Intent?): IBinder? {
		Log.d(TAG, "MyService onBind")
		return Binder()
	}
	override fun onRebind(intent: Intent?) {
		Log.d(TAG, "MyService onRebind")
		super.onRebind(intent)
	}
	override fun onUnbind(intent: Intent?): Boolean {
		Log.d(TAG, "MyService onUnbind")
		// return super.onUnbind(intent)
		return true	// super returns false
	}
	override fun onDestroy() {
		super.onDestroy()
		Log.d(TAG, "MyService onDestroy()")
	}
}
