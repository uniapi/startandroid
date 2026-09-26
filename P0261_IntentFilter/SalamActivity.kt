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
import android.view.ViewGroup.LayoutParams
import android.widget.LinearLayout
import android.widget.Button

class SalamActivity : Activity() {
	private companion object {
		val TAG = "localhost.idroid.salamun"
	}
	private lateinit var btnTime: Button
	private lateinit var btnDate: Button
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		val rootLayout = LinearLayout(this).apply {
			layoutParams = LayoutParams(
				LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT
			)
			orientation = LinearLayout.HORIZONTAL
		}
		btnTime = Button(this).apply {
			layoutParams = LinearLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT
			)
			text = "Show time"
		}
		btnDate = Button(this).apply {
			layoutParams = LinearLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT
			)
			text = "Show date"
		}
		btnTime.setOnClickListener { _: View? ->
			startActivity(Intent("localhost.idroid.intent.action.showtime"))
		}
		btnDate.setOnClickListener { _: View? ->
			startActivity(Intent("localhost.idroid.intent.action.showdate"))
		}
		with(rootLayout) {
			addView(btnTime)
			addView(btnDate)
		}
		setContentView(rootLayout)
	}
}
