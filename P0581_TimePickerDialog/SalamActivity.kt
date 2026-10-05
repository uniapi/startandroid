/* \uFDFD
 *                     \u0648\u0671\u0630\u0643\u0631 \u0631\u0628\u0651\u0643
 *        \u0643\u062A\u0628\u0647 \u0639\u0628\u062F \u0671\u0644\u0643\u0631\u064A\u0645
 *  \u06F1\u06F4\u06F4\u06F8 \u0631\u0628\u064A\u0639 \u0671\u0644\u0622\u062E\u0631 \u06F2\u06F5
 */
package localhost.idroid.salamun

import android.util.Log
import android.app.Activity
import android.app.Dialog
import android.app.TimePickerDialog
import android.app.TimePickerDialog.OnTimeSetListener
import android.os.Bundle
import android.view.View
import android.view.Gravity
import android.view.ViewGroup.LayoutParams
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.TimePicker

class SalamActivity : Activity() {
    private companion object {
    	const val TAG = "localhost.idroid.salamun"
		const val DIALOG_TIME = 1
    }
	var myHour = 14
	var myMinute = 35
	private lateinit var tvTime: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val rootLayout = LinearLayout(this).apply {
            layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
            orientation = LinearLayout.VERTICAL
        }
		tvTime = TextView(this).apply {
			layoutParams = LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
			text = getString(R.string.hello)
			textSize = 22f
			isClickable = true
			setOnClickListener {
				showDialog(DIALOG_TIME)
			}
		}
		with(rootLayout) {
			addView(tvTime)
		}
		setContentView(rootLayout)
	}
	override fun onCreateDialog(id: Int): Dialog? {
		when (id) {
			DIALOG_TIME -> return TimePickerDialog(this, myCallBack, myHour, myMinute, true)
			else -> return super.onCreateDialog(id)
		}
	}
	private val myCallBack = OnTimeSetListener { _, hourOfDay, minute ->
		myHour = hourOfDay
		myMinute = minute
		tvTime.text = "Time is $myHour hours $myMinute minutes"
	}
}
