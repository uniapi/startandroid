/* \uFDFD
 *                           \u0648\u0671\u0630\u0643\u0631 \u0631\u0628\u0651\u0643
 *              \u0643\u062A\u0628\u0647 \u0639\u0628\u062F \u0671\u0644\u0643\u0631\u064A\u0645
 *  \u06F1\u06F4\u06F4\u06F8 \u062C\u0645\u0627\u062F\u0649 \u0627\u0644\u0623\u0648\u0644\u0649 \u06F1\u06F4
 */
package localhost.idroid.salamun

import android.util.Log
import android.app.Activity
import android.appwidget.AppWidgetManager
import android.content.SharedPreferences
import android.content.SharedPreferences.Editor
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.Gravity
import android.view.ViewGroup.LayoutParams
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.EditText
import android.widget.RadioGroup
import android.widget.RadioButton
import android.widget.Button
import android.graphics.Color

class ConfigActivity : Activity() {
	companion object {
		private const val TAG = "localhost.idroid.salamun"
		const val WIDGET_PREF = "widget_pref"
		const val WIDGET_TEXT = "widget_text"
		const val WIDGET_COLOR = "widget_color"
	}
	private val RADIO_ID_RED = 1
	private val RADIO_ID_GREEN = 2
	private val RADIO_ID_BLUE = 3
	private lateinit var rgColor: RadioGroup
	private lateinit var etText: EditText
	private var widgetId = AppWidgetManager.INVALID_APPWIDGET_ID
	private lateinit var resultValue: Intent

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		Log.d(TAG, "onCreate config")
		val intent = getIntent()
		val extras = intent.extras
		if (extras != null) {
			widgetId = extras.getInt(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID)
		}
		if (widgetId == AppWidgetManager.INVALID_APPWIDGET_ID) {
			finish()
		}
		resultValue = Intent().apply {
			putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, widgetId)
		}
		setResult(RESULT_CANCELED, resultValue)

		val llRoot = LinearLayout(this).apply {
			layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
			orientation = LinearLayout.VERTICAL
		}
		rgColor = RadioGroup(this).apply {
			layoutParams = LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
		}
		val lParams = RadioGroup.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
		val rbRed = RadioButton(this).apply { id = RADIO_ID_RED; text = getString(R.string.red) }
		val rbGreen = RadioButton(this).apply { id = RADIO_ID_GREEN; text = getString(R.string.green) }
		val rbBlue = RadioButton(this).apply { id = RADIO_ID_BLUE; text = getString(R.string.blue) }
		etText = EditText(this).apply {
			layoutParams = LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
			setText(getString(R.string.ok))
		}
		val btnClick = Button(this).apply {
			layoutParams = LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
			text = getString(R.string.ok)
			setOnClickListener(onClick)
		}
		with(rgColor) {
			addView(rbRed, lParams)
			addView(rbGreen, lParams)
			addView(rbBlue, lParams)
		}
		with(llRoot) {
			addView(rgColor)
			addView(etText)
			addView(btnClick)
		}
		setContentView(llRoot)
	}
	val onClick = View.OnClickListener { v: View? ->
		val selRBColor = rgColor.getCheckedRadioButtonId()
		var color = Color.RED
		when (selRBColor) {
			RADIO_ID_RED -> color = Color.parseColor("#66ff0000")
			RADIO_ID_GREEN -> color = Color.parseColor("#6600ff00")
			RADIO_ID_BLUE -> color = Color.parseColor("#660000ff")
		}
		val sp = getSharedPreferences(WIDGET_PREF, MODE_PRIVATE)
		val editor = sp.edit()
		editor.putString("$WIDGET_TEXT$widgetId", etText.text.toString())
		editor.putInt("$WIDGET_COLOR$widgetId", color)
		editor.commit()
		val appWidgetManager = AppWidgetManager.getInstance(this@ConfigActivity)
		MyWidget.updateWidget(this@ConfigActivity, appWidgetManager, sp, widgetId)
		setResult(RESULT_OK, resultValue)
		Log.d(TAG, "finish config $widgetId")
		finish()
	}
}
