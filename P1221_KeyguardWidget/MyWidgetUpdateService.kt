/* \uFDFD
 *                           \u0648\u0671\u0630\u0643\u0631 \u0631\u0628\u0651\u0643
 *              \u0643\u062A\u0628\u0647 \u0639\u0628\u062F \u0671\u0644\u0643\u0631\u064A\u0645
 *  \u06F1\u06F4\u06F4\u06F8 \u062C\u0645\u0627\u062F\u0649 \u0627\u0644\u0623\u0648\u0644\u0649 \u06F1\u06F6
 */
package localhost.idroid.salamun

import java.text.SimpleDateFormat
import java.util.Date

import android.util.Log
import android.app.Notification
import android.app.Service
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.ComponentName
import android.content.Intent
import android.content.Context
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import android.widget.RemoteViews

class MyWidgetUpdateService : Service() {
	companion object {
		private const val TAG = "localhost.idroid.salamun"
	}
	private val handler = Handler(Looper.getMainLooper())
	private val timeFormat = SimpleDateFormat("HH:mm:ss")

	private val updateRunnable = object : Runnable {
		override fun run() {
			updateWidgetTime(applicationContext)
			// Schedule the next execution precisely 1 second from now
			handler.postDelayed(this, 1000)
		}
	}
	override fun onCreate() {
		super.onCreate()
		// API 17 requires a Foreground Service notification to prevent the OS from killing the loop
		val notification = Notification.Builder(this)
			.setContentTitle("Time Widget Active")
			.setContentText("Updating clock seconds...")
			.setSmallIcon(android.R.drawable.ic_menu_my_calendar)
			.build()
		startForeground(101, notification)
		handler.post(updateRunnable)
	}
	private fun updateWidgetTime(context: Context) {
		val appWidgetManager = AppWidgetManager.getInstance(context) ?: return
		val widget = ComponentName(context.packageName, MyWidget::class.java.name)
		val allWidgetIds = appWidgetManager.getAppWidgetIds(widget) ?: return
		if (allWidgetIds.isEmpty()) {
			stopSelf()	// Automatically shut down service if no widgets exist on screen
			return
		}
		val views = RemoteViews(context.packageName, R.layout.widget)
		views.setTextViewText(R.id.tvTime, timeFormat.format(Date()))
		for (widgetId in allWidgetIds) {
			appWidgetManager.updateAppWidget(widgetId, views)
		}
	}
	override fun onDestroy() {
		super.onDestroy()
		handler.removeCallbacks(updateRunnable)
	}
	override fun onBind(intent: Intent?): IBinder? = null
}
