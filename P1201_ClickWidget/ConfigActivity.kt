/* \uFDFD
 *                           \u0648\u0671\u0630\u0643\u0631 \u0631\u0628\u0651\u0643
 *              \u0643\u062A\u0628\u0647 \u0639\u0628\u062F \u0671\u0644\u0643\u0631\u064A\u0645
 *  \u06F1\u06F4\u06F4\u06F8 \u062C\u0645\u0627\u062F\u0649 \u0627\u0644\u0623\u0648\u0644\u0649 \u06F1\u06F5
 */
package localhost.idroid.salamun

import android.util.Log
import android.app.Activity
import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.content.SharedPreferences
import android.view.Gravity
import android.view.ViewGroup.LayoutParams
import android.widget.LinearLayout
import android.widget.EditText

class ConfigActivity : Activity() {
	companion object {
		private const val TAG = "localhost.idroid.salamun"
		const val WIDGET_PREF = "widget_pref"
		const val WIDGET_TIME_FORMAT = "widget_time_format_"
		const val WIDGET_COUNT = "widget_count_"
	}
	private var widgetId = AppWidgetManager.INVALID_APPWIDGET_ID
	private lateinit var resultValue: Intent
	private lateinit var sp: SharedPreferences
	private lateinit var etFormat: EditText

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		val intent = intent
		val extras = intent.extras
		if (extras != null) {
			widgetId = extras.getInt(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID)
		}
		if (widgetId == AppWidgetManager.INVALID_APPWIDGET_ID) {
			finish()
			return		// Prevent further execution if finished
		}
		resultValue = Intent().apply {
			putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, widgetId)
		}
		setResult(RESULT_CANCELED, resultValue)

		setContentView(R.layout.config)
		Log.d(TAG, "onCreate: ${intent.action}, $widgetId")
		sp = getSharedPreferences(WIDGET_PREF, MODE_PRIVATE)
		etFormat = findViewById(R.id.etFormat) as EditText
		etFormat.setText(sp.getString(WIDGET_TIME_FORMAT + widgetId, "HH:mm:ss"))
		val cnt = sp.getInt(WIDGET_COUNT + widgetId, -1)
		if (cnt == -1) {
			sp.edit().putInt(WIDGET_COUNT + widgetId, 0).apply()	// apply() is prefered over commit()
		}
		Log.d(TAG, "onCreate: OK")
	}
	fun onClick(v: View?) {
		Log.d(TAG, "onClick: Init")
		sp.edit().putString(WIDGET_TIME_FORMAT + widgetId, etFormat.text.toString()).commit()
		MyWidget.updateWidget(this, AppWidgetManager.getInstance(this), widgetId, false)
		setResult(RESULT_OK, resultValue)
		Log.d(TAG, "onClick: OK")
		finish()
	}
}
