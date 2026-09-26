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
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import android.widget.EditText
import android.widget.Button

class SalamActivity : Activity() {
    private companion object {
        val TAG = "localhost.idroid.salamun"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val rootLayout = LinearLayout(this).apply {
            layoutParams = LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT
            )
            orientation = LinearLayout.VERTICAL
        }
		val viewer = TextView(this).apply {
			layoutParams = LinearLayout.LayoutParams(
				LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT
			).apply { topMargin = dpToPx(this@SalamActivity, 10f) }
			gravity = Gravity.CENTER_HORIZONTAL
			text = "Input your name"
		}
		val tableLayout = TableLayout(this).apply {
			layoutParams = LinearLayout.LayoutParams(
				LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT
			).apply { setMargins(10, 10, 10, 10) }
			isStretchAllColumns = true
		}
		val tvFName = TextView(this).apply {
			layoutParams = TableRow.LayoutParams(
				LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT
			)
			text = "First Name"
		}
		val etFName = EditText(this).apply {
			layoutParams = TableRow.LayoutParams(
				LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT
			).apply { leftMargin = dpToPx(this@SalamActivity, 5f) }
		}
		val tvLName = TextView(this).apply {
			layoutParams = TableRow.LayoutParams(
				LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT
			)
			text = "Last Name"
		}
		val etLName = EditText(this).apply {
			layoutParams = TableRow.LayoutParams(
				LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT
			).apply { leftMargin = dpToPx(this@SalamActivity, 5f) }
		}
		with(tableLayout) {
			addView(TableRow(this@SalamActivity).apply {
				layoutParams = TableLayout.LayoutParams(
					LayoutParams.MATCH_PARENT,
					LayoutParams.WRAP_CONTENT
				)
				addView(tvFName)
				addView(etFName)
			})
			addView(TableRow(this@SalamActivity).apply {
				layoutParams = TableLayout.LayoutParams(
					LayoutParams.MATCH_PARENT,
					LayoutParams.WRAP_CONTENT
				)
				addView(tvLName)
				addView(etLName)
			})
		}
		val btnSubmit = Button(this).apply {
			layoutParams = LinearLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT
			).apply { gravity = Gravity.CENTER_HORIZONTAL }
			text = "Submit"
		}
		with(rootLayout) {
			addView(viewer)
			addView(tableLayout)
			addView(btnSubmit)
		}
		setContentView(rootLayout)
		btnSubmit.setOnClickListener { _: View? ->
			val intent = Intent(this, ViewActivity::class.java)
			with(intent) {
				putExtra("fname", etFName.text.toString())
				putExtra("lname", etLName.text.toString())
				startActivity(this)
			}
		}
	}
}
