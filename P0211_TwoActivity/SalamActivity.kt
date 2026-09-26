/* \uFDFD
 *                     \u0648\u0671\u0630\u0643\u0631 \u0631\u0628\u0651\u0643
 *        \u0643\u062A\u0628\u0647 \u0639\u0628\u062F \u0671\u0644\u0643\u0631\u064A\u0645
 *  \u06F1\u06F4\u06F4\u06F8 \u0631\u0628\u064A\u0639 \u0671\u0644\u0622\u062E\u0631 \u06F1\u06F4
 */
package localhost.idroid.salamun

import android.app.Activity
import android.os.Bundle
import android.content.Intent
import android.view.View
import android.view.ViewGroup.LayoutParams
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Button

class SalamActivity : Activity() {
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		val rootLayout = LinearLayout(this).apply {
			layoutParams = LayoutParams(
				LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT
			)
			orientation = LinearLayout.VERTICAL
		}
		val btnActTwo = Button(this).apply {
			id = 1
			layoutParams = LinearLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT
			)
			text = "Go to Activity Two"
		}
		btnActTwo.setOnClickListener { view: View? ->
			when(view?.id) {
				btnActTwo.id -> startActivity(Intent(this, ActivityTwo::class.java))
			}
		}
		with(rootLayout) {
			addView(btnActTwo)
		}
		setContentView(rootLayout)
	}
}
