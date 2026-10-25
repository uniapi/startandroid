/* \uFDFD
 *                           \u0648\u0671\u0630\u0643\u0631 \u0631\u0628\u0651\u0643
 *              \u0643\u062A\u0628\u0647 \u0639\u0628\u062F \u0671\u0644\u0643\u0631\u064A\u0645
 *  \u06F1\u06F4\u06F4\u06F8 \u062C\u0645\u0627\u062F\u0649 \u0627\u0644\u0623\u0648\u0644\u0649 \u06F1\u06F5
 */
package localhost.idroid.salamun

import android.util.Log
import android.app.Activity
import android.app.AlarmManager
import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.SystemClock
import android.os.Bundle
import android.view.View
import android.view.Gravity
import android.view.ViewGroup.LayoutParams
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Button

class SalamActivity : Activity() {
	companion object {
		private const val TAG = "localhost.idroid.salamun"
	}
	private lateinit var nm: NotificationManager
	private lateinit var am: AlarmManager
	private var intent1: Intent? = null
	private var intent2: Intent? = null
	private var pIntent1: PendingIntent? = null
	private var pIntent2: PendingIntent? = null

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)

		val llRoot = LinearLayout(this).apply {
			layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
			orientation = LinearLayout.VERTICAL
		}
		val button1 = Button(this).apply {
			layoutParams = LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
			text = getString(R.string.button1)
			setOnClickListener(onClick1)
		}
		val button2 = Button(this).apply {
			layoutParams = LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
			text = getString(R.string.button2)
			setOnClickListener(onClick2)
		}
		with(llRoot) {
			addView(button1)
			addView(button2)
		}
		setContentView(llRoot)

		nm = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
		am = getSystemService(Context.ALARM_SERVICE) as AlarmManager
	}
	val onClick1 = View.OnClickListener { v: View? ->
		intent1 = createIntent("action 1", "extra 1")
		pIntent1 = PendingIntent.getBroadcast(this, 0, intent1, 0)
		intent2 = createIntent("action 2", "extra 2")
		pIntent2 = PendingIntent.getBroadcast(this, 0, intent2, 0)
		Log.d(TAG, "start")
		am.set(AlarmManager.RTC, System.currentTimeMillis() + 4000, pIntent1)
		am.setRepeating(AlarmManager.ELAPSED_REALTIME, SystemClock.elapsedRealtime() + 3000, 5000, pIntent2)
	}
	val onClick2 = View.OnClickListener { v: View? ->
		am.cancel(pIntent2)
	}
	private fun createIntent(action: String, extra: String): Intent {
		return Intent(this, Receiver::class.java).apply {
			setAction(action)
			putExtra("extra", extra)
		}
	}
	private fun compare() {
		Log.d(TAG, "intent1 = intent2: ${intent1?.filterEquals(intent2)}")
		Log.d(TAG, "pIntent1 = pIntent2: ${pIntent1 == pIntent2}")
	}
	private fun sendNotif(id: Int, pIntent: PendingIntent) {
		val notif = Notification(android.R.drawable.ic_dialog_alert, "Notif $id", System.currentTimeMillis()).apply {
			flags = flags or Notification.FLAG_AUTO_CANCEL
			setLatestEventInfo(this@SalamActivity, "Title $id", "Content $id", pIntent)
		}
		nm.notify(id, notif)
	}
}
