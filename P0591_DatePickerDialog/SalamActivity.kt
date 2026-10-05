/* \uFDFD
 *                     \u0648\u0671\u0630\u0643\u0631 \u0631\u0628\u0651\u0643
 *        \u0643\u062A\u0628\u0647 \u0639\u0628\u062F \u0671\u0644\u0643\u0631\u064A\u0645
 *  \u06F1\u06F4\u06F4\u06F8 \u0631\u0628\u064A\u0639 \u0671\u0644\u0622\u062E\u0631 \u06F2\u06F5
 */
package localhost.idroid.salamun

import android.util.Log
import android.app.Activity
import android.app.Dialog
import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.os.Bundle
import android.view.View
import android.view.ViewGroup.LayoutParams
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.DatePicker

class SalamActivity : Activity() {
    private companion object {
    	const val TAG = "localhost.idroid.salamun"
		const val DIALOG_DATE = 1
    }
	var myYear = 2011
	var myMonth = 2
	var myDay = 3
	private lateinit var tvDate: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val rootLayout = LinearLayout(this).apply {
            layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
            orientation = LinearLayout.VERTICAL
        }
		tvDate = TextView(this).apply {
			layoutParams = LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
			text = getString(R.string.hello)
			textSize = 22f
			isClickable = true
			setOnClickListener {
				showDialog(DIALOG_DATE)
			}
		}
		with(rootLayout) {
			addView(tvDate)
		}
		setContentView(rootLayout)
	}
	override fun onCreateDialog(id: Int): Dialog? {
		when (id) {
			DIALOG_DATE -> return DatePickerDialog(this, myCallBack, myYear, myMonth, myDay)
			else -> return super.onCreateDialog(id)
		}
	}
	private val myCallBack = OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
		myYear = year
		myMonth = monthOfYear + 1
		myDay = dayOfMonth
		tvDate.text = "Today is $myDay/$myMonth/$myYear"
	}
}
