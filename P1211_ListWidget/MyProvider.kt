/* \uFDFD
 *                           \u0648\u0671\u0630\u0643\u0631 \u0631\u0628\u0651\u0643
 *              \u0643\u062A\u0628\u0647 \u0639\u0628\u062F \u0671\u0644\u0643\u0631\u064A\u0645
 *  \u06F1\u06F4\u06F4\u06F8 \u062C\u0645\u0627\u062F\u0649 \u0627\u0644\u0623\u0648\u0644\u0649 \u06F1\u06F6
 */
package localhost.idroid.salamun

import java.text.SimpleDateFormat
import java.util.Date

import android.util.Log
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.app.PendingIntent
import android.content.Intent
import android.content.Context
import android.widget.RemoteViews
import android.widget.Toast
import android.net.Uri

class MyProvider : AppWidgetProvider() {
	companion object {
		private const val TAG = "localhost.idroid.salamun"
		const val ITEM_POSITION = "item_position"
	}
	private val ACTION_ON_CLICK = "$TAG.itemonclick"
	private val sdf = SimpleDateFormat("HH:mm:ss")

	override fun onUpdate(context: Context?, appWidgetManager: AppWidgetManager?, appWidgetIds: IntArray?) {
		super.onUpdate(context, appWidgetManager, appWidgetIds)
		if (context == null || appWidgetManager == null || appWidgetIds == null) return
		for (i in appWidgetIds) {
			updateWidget(context, appWidgetManager, i)
		}
	}
	private fun updateWidget(context: Context, appWidgetManager: AppWidgetManager, appWidgetId: Int) {
		Log.d(TAG, "updateWidget: $appWidgetId")
		val rv = RemoteViews(context.packageName, R.layout.widget)
		setUpdateTv(rv, context, appWidgetId)
		setList(rv, context, appWidgetId)
		setListClick(rv, context, appWidgetId)
		appWidgetManager.updateAppWidget(appWidgetId, rv)
		appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetId, R.id.lvList)
		Log.d(TAG, "updateWidget: OK")
	}
	private fun setUpdateTv(rv: RemoteViews, context: Context, appWidgetId: Int) {
		rv.setTextViewText(R.id.tvUpdate, sdf.format(Date(System.currentTimeMillis())))
		val updIntent = Intent(context, MyProvider::class.java).apply {
			action = AppWidgetManager.ACTION_APPWIDGET_UPDATE
			putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, intArrayOf(appWidgetId))
		}
		// Modern Android requires a mutability flag
		val flags = PendingIntent.FLAG_UPDATE_CURRENT
		val updPIntent= PendingIntent.getBroadcast(context, appWidgetId, updIntent, flags)
		rv.setOnClickPendingIntent(R.id.tvUpdate, updPIntent)
	}
	private fun setList(rv: RemoteViews, context: Context, appWidgetId: Int) {
		val adapter = Intent(context, MyService::class.java).apply {
			putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId)
		}
		val data = Uri.parse(adapter.toUri(Intent.URI_INTENT_SCHEME))
		adapter.setData(data)
		rv.setRemoteAdapter(R.id.lvList, adapter)
	}
	private fun setListClick(rv: RemoteViews, context: Context, appWidgetId: Int) {
		val listClickIntent = Intent(context, MyProvider::class.java).apply {
			setAction(ACTION_ON_CLICK)
		}
		val listClickPIntent = PendingIntent.getBroadcast(context, 0, listClickIntent, 0)
		rv.setPendingIntentTemplate(R.id.lvList, listClickPIntent)
	}
	override fun onReceive(context: Context?, intent: Intent?) {
		super.onReceive(context, intent)
		Log.d(TAG, "onReceive: ${intent?.action}")
		if (intent?.action == ACTION_ON_CLICK) {
			val itemPos = intent.getIntExtra(ITEM_POSITION, -1)
			if (itemPos != -1) {
				Toast.makeText(context, "Clicked on item $itemPos", Toast.LENGTH_SHORT).show()
			}
		}
		Log.d(TAG, "onReceive: OK")
	}
}
