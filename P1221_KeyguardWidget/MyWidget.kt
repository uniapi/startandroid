/* \uFDFD
 *                           \u0648\u0671\u0630\u0643\u0631 \u0631\u0628\u0651\u0643
 *              \u0643\u062A\u0628\u0647 \u0639\u0628\u062F \u0671\u0644\u0643\u0631\u064A\u0645
 *  \u06F1\u06F4\u06F4\u06F8 \u062C\u0645\u0627\u062F\u0649 \u0627\u0644\u0623\u0648\u0644\u0649 \u06F1\u06F7
 */
package localhost.idroid.salamun

import android.util.Log
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.ComponentName
import android.content.Intent
import android.content.Context

class MyWidget : AppWidgetProvider() {
	companion object {
		private const val TAG = "localhost.idroid.salamun"
	}
	override fun onEnabled(context: Context?) {
		super.onEnabled(context)
		context?.let {
			val intent = Intent(it, MyWidgetUpdateService::class.java)
			it.startService(intent)
		}
	}
	override fun onDisabled(context: Context?) {
		super.onDisabled(context)
		context?.let {
			val intent = Intent(it, MyWidgetUpdateService::class.java)
			it.stopService(intent)
		}
	}
	override fun onUpdate(context: Context?, appWidgetManager: AppWidgetManager?, appWidgetIds: IntArray?) {
		super.onUpdate(context, appWidgetManager, appWidgetIds)
		// Keep service alive if system restarts the provider component
		context?.let {
			val intent = Intent(it, MyWidgetUpdateService::class.java)
			it.startService(intent)
		}
	}
}
