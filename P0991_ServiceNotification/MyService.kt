/* \uFDFD
 *                           \u0648\u0671\u0630\u0643\u0631 \u0631\u0628\u0651\u0643
 *              \u0643\u062A\u0628\u0647 \u0639\u0628\u062F \u0671\u0644\u0643\u0631\u064A\u0645
 *  \u06F1\u06F4\u06F4\u06F8 \u062C\u0645\u0627\u062F\u0649 \u0627\u0644\u0623\u0648\u0644\u0649 \u06F3
 */
package localhost.idroid.salamun

import java.util.concurrent.TimeUnit

import android.util.Log
import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.os.IBinder

class MyService : Service() {
	private companion object {
		const val TAG = "localhost.idroid.salamun"
	}
	private lateinit var nm: NotificationManager

	override fun onCreate() {
		super.onCreate()
		nm = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
	}
	override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
		try {
			TimeUnit.SECONDS.sleep(5)
		} catch (e: InterruptedException) {
			e.printStackTrace()
		}
		sendNotif()
		return super.onStartCommand(intent, flags, startId)
	}
	private fun sendNotif() {
		// 1-st part
		val notif = Notification(android.R.drawable.sym_def_app_icon, "Text in status bar", System.currentTimeMillis())
		// 3-rd part
		val intent = Intent(this, SalamActivity::class.java).apply {
			putExtra(SalamActivity.FILE_NAME, "somefile")
		}
		val pIntent = PendingIntent.getActivity(this, 0, intent, 0)
		// 2-nd part
		notif.setLatestEventInfo(this, "Notificationn's title", "Notification's text", pIntent)
		// putting the flag in order the notifiation disappeard after touching
		notif.flags = notif.flags or Notification.FLAG_AUTO_CANCEL
		notif.defaults = Notification.DEFAULT_VIBRATE or Notification.DEFAULT_LIGHTS
		// sending
		nm.notify(1, notif)
	}
	override fun onBind(intent: Intent?): IBinder? = null
}
