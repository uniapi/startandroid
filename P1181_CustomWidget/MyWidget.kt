/* \uFDFD
 *                           \u0648\u0671\u0630\u0643\u0631 \u0631\u0628\u0651\u0643
 *              \u0643\u062A\u0628\u0647 \u0639\u0628\u062F \u0671\u0644\u0643\u0631\u064A\u0645
 *  \u06F1\u06F4\u06F4\u06F8 \u062C\u0645\u0627\u062F\u0649 \u0627\u0644\u0623\u0648\u0644\u0649 \u06F1\u06F4
 */
package localhost.idroid.salamun

import android.util.Log
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.SharedPreferences
import android.content.SharedPreferences.Editor
import android.widget.RemoteViews

class MyWidget : AppWidgetProvider() {
	companion object {
		private const val TAG = "localhost.idroid.salamun"
		@JvmStatic
		fun updateWidget(
			context: Context?,
			appWidgetManager: AppWidgetManager?,
			sp: SharedPreferences?,
			widgetId: Int
		) {
			Log.d(TAG, "updateWidget $widgetId")
			val widgetText = sp?.getString(ConfigActivity.WIDGET_TEXT + widgetId, null) ?: return
			val widgetColor = sp.getInt(ConfigActivity.WIDGET_COLOR + widgetId, 0)
			val packageName = context?.getPackageName() ?: return
			val widgetView = RemoteViews(packageName, R.layout.widget)
			widgetView.setTextViewText(R.id.tv, widgetText)
			widgetView.setInt(R.id.tv, "setBackgroundColor", widgetColor)
			appWidgetManager?.updateAppWidget(widgetId, widgetView)
		}
	}
	override fun onEnabled(context: Context?) {
		super.onEnabled(context)
		Log.d(TAG, "onEnabled")
	}
	override fun onUpdate(context: Context?, appWidgetManager: AppWidgetManager?, appWidgetIds: IntArray?) {
		super.onUpdate(context, appWidgetManager, appWidgetIds)
		Log.d(TAG, "onUpdate ${appWidgetIds?.contentToString()}")
		val sp = context?.getSharedPreferences(ConfigActivity.WIDGET_PREF, Context.MODE_PRIVATE)
		appWidgetIds?.forEach { widgetId ->
			updateWidget(context, appWidgetManager, sp, widgetId)
		}
	}
	override fun onDeleted(context: Context?, appWidgetIds: IntArray?) {
		super.onDeleted(context, appWidgetIds)
		Log.d(TAG, "onDeleted ${appWidgetIds?.contentToString()}")
		val editor = context?.getSharedPreferences(ConfigActivity.WIDGET_PREF, Context.MODE_PRIVATE)?.edit()
		if (editor != null) {
			appWidgetIds?.forEach { widgetId ->
				editor.remove(ConfigActivity.WIDGET_TEXT + widgetId)
				editor.remove(ConfigActivity.WIDGET_COLOR + widgetId)
			}
			editor.apply()
		}
	}
	override fun onDisabled(context: Context?) {
		super.onDisabled(context)
		Log.d(TAG, "onDisabled")
	}
}
