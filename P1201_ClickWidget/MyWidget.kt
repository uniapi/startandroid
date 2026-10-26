/* \uFDFD
 *                           \u0648\u0671\u0630\u0643\u0631 \u0631\u0628\u0651\u0643
 *              \u0643\u062A\u0628\u0647 \u0639\u0628\u062F \u0671\u0644\u0643\u0631\u064A\u0645
 *  \u06F1\u06F4\u06F4\u06F8 \u062C\u0645\u0627\u062F\u0649 \u0627\u0644\u0623\u0648\u0644\u0649 \u06F1\u06F5
 */
package localhost.idroid.salamun

import java.util.Date
import java.text.SimpleDateFormat

import android.util.Log
import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.RemoteViews

class MyWidget : AppWidgetProvider() {
	companion object {
		private const val TAG = "localhost.idroid.salamun"
		const val ACTION_CHANGE = "$TAG.change_count"
		const val ACTION_REFRESH = "$TAG.refresh_widget"
		const val WIDGET_LAST_TIME = "widget_last_time_"

		@JvmStatic
		fun updateWidget(context: Context?, appWidgetManager: AppWidgetManager?, widgetId: Int, onlyCount: Boolean) {
			Log.d(TAG, "updateWidget $widgetId")
			if (context == null || appWidgetManager == null) return
			val sp = context.getSharedPreferences(ConfigActivity.WIDGET_PREF, Context.MODE_PRIVATE) ?: return
			val timeFormat = sp.getString(ConfigActivity.WIDGET_TIME_FORMAT + widgetId, null) ?: return

			val sdf = SimpleDateFormat(timeFormat)
			var actualTime = sdf.format(Date(System.currentTimeMillis()))
			if (onlyCount) {
				actualTime = sp.getString(WIDGET_LAST_TIME + widgetId, actualTime)
			} else {
				sp.edit()?.putString(WIDGET_LAST_TIME + widgetId, actualTime)?.apply()
			}
			val count = sp.getInt(ConfigActivity.WIDGET_COUNT + widgetId, 0).toString()
Log.d(TAG, "count: $count")
			val widgetView = RemoteViews(context.packageName, R.layout.widget).apply {
				setTextViewText(R.id.tvTime, actualTime)
				setTextViewText(R.id.tvCount, count)
			}

			val configIntent = Intent(context, ConfigActivity::class.java).apply {
				action = AppWidgetManager.ACTION_APPWIDGET_CONFIGURE
				putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, widgetId)
			}
			val pIntentConfig = PendingIntent.getActivity(context, widgetId, configIntent, PendingIntent.FLAG_UPDATE_CURRENT)
			widgetView.setOnClickPendingIntent(R.id.tvPressConfig, pIntentConfig)
			val updateIntent = Intent(context, MyWidget::class.java).apply {
				/*
				action = AppWidgetManager.ACTION_APPWIDGET_UPDATE
				putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, intArrayOf(widgetId))
				*/
				action = ACTION_REFRESH
				putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, widgetId)
			}
			val pIntentUpdate = PendingIntent.getBroadcast(context, widgetId, updateIntent, PendingIntent.FLAG_UPDATE_CURRENT)
			widgetView.setOnClickPendingIntent(R.id.tvPressUpdate, pIntentUpdate)
			val countIntent = Intent(context, MyWidget::class.java).apply {
				action = ACTION_CHANGE
				putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, widgetId)
			}
			val pIntentCount = PendingIntent.getBroadcast(context, widgetId, countIntent, PendingIntent.FLAG_UPDATE_CURRENT)
			widgetView.setOnClickPendingIntent(R.id.tvPressCount, pIntentCount)

			appWidgetManager.updateAppWidget(widgetId, widgetView)
			Log.d(TAG, "updateWidget: OK")
		}
	}
	override fun onUpdate(context: Context?, appWidgetManager: AppWidgetManager?, appWidgetIds: IntArray?) {
		super.onUpdate(context, appWidgetManager, appWidgetIds)
		Log.d(TAG, "onUpdate ${appWidgetIds?.contentToString()}")
		appWidgetIds?.forEach { widgetId ->
			updateWidget(context, appWidgetManager, widgetId, false)
		}
	}
	override fun onDeleted(context: Context?, appWidgetIds: IntArray?) {
		super.onDeleted(context, appWidgetIds)
		Log.d(TAG, "onDeleted ${appWidgetIds?.contentToString()}")
		if (context == null || appWidgetIds == null) return
		val editor = context.getSharedPreferences(ConfigActivity.WIDGET_PREF, Context.MODE_PRIVATE)?.edit() ?: return
		appWidgetIds.forEach { widgetId ->
			editor.remove(ConfigActivity.WIDGET_TIME_FORMAT + widgetId)
			editor.remove(ConfigActivity.WIDGET_COUNT + widgetId)
			editor.remove(WIDGET_LAST_TIME + widgetId)
		}
		editor.apply()
	}
	override fun onReceive(context: Context?, intent: Intent?) {
		super.onReceive(context, intent)
		Log.d(TAG, "onReceive: Init(${intent?.action})")
		if (context == null || intent == null) return
		val action = intent.action
		val mAppWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID)
		Log.d(TAG, "onReceive: ${intent.action}, $mAppWidgetId")
		if (mAppWidgetId != AppWidgetManager.INVALID_APPWIDGET_ID) {
			val sp = context.getSharedPreferences(ConfigActivity.WIDGET_PREF, Context.MODE_PRIVATE) ?: return
			var cnt = sp.getInt(ConfigActivity.WIDGET_COUNT + mAppWidgetId, 0)
			++cnt
			sp.edit()?.putInt(ConfigActivity.WIDGET_COUNT + mAppWidgetId, cnt)?.apply()
			when (action) {
				ACTION_CHANGE -> {
					updateWidget(context, AppWidgetManager.getInstance(context), mAppWidgetId, true)
				}
				ACTION_REFRESH -> {
					// Full refresh updates both time string calculations and the counter
					updateWidget(context, AppWidgetManager.getInstance(context), mAppWidgetId, false)
				}
			}
		}
		Log.d(TAG, "onReceive: OK")
	}
}
