
/* \uFDFD
 *                     \u0648\u0671\u0630\u0643\u0631 \u0631\u0628\u0651\u0643
 *        \u0643\u062A\u0628\u0647 \u0639\u0628\u062F \u0671\u0644\u0643\u0631\u064A\u0645
 *  \u06F1\u06F4\u06F4\u06F8 \u0631\u0628\u064A\u0639 \u0671\u0644\u0622\u062E\u0631 \u06F1\u06F4
 */
package localhost.idroid.salamun

import android.util.Log
import android.content.Intent
import android.app.Activity
import android.os.Bundle
import android.view.View
import android.view.Gravity
import android.view.ViewGroup.LayoutParams
import android.widget.LinearLayout
import android.widget.TextView
import java.text.SimpleDateFormat
import java.util.Date

class ActivityDate : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val rootLayout = LinearLayout(this).apply {
            layoutParams = LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT
            )
            orientation = LinearLayout.VERTICAL
        }
		val tvDate = TextView(this).apply {
			layoutParams = LinearLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT
			).apply { gravity = Gravity.CENTER_HORIZONTAL; topMargin = dpToPx(this@ActivityDate, 20f) }
			textSize = 30f
		}
		with(rootLayout) {
			addView(tvDate)
		}
		setContentView(rootLayout)
		tvDate.text = SimpleDateFormat("dd.MM.yyyy").format(Date(System.currentTimeMillis()))
	}
}
