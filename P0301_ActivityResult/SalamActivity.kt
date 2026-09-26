/* \uFDFD
 *                     \u0648\u0671\u0630\u0643\u0631 \u0631\u0628\u0651\u0643
 *        \u0643\u062A\u0628\u0647 \u0639\u0628\u062F \u0671\u0644\u0643\u0631\u064A\u0645
 *  \u06F1\u06F4\u06F4\u06F8 \u0631\u0628\u064A\u0639 \u0671\u0644\u0622\u062E\u0631 \u06F1\u06F5
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
import android.widget.Button
import android.widget.Toast
import android.graphics.Color

class SalamActivity : Activity() {
	private companion object {
		val TAG = "localhost.idroid.salamun"
	}
	val REQUEST_CODE_COLOR = 1
	val REQUEST_CODE_ALIGN = 2
	private lateinit var tvText: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val rootLayout = LinearLayout(this).apply {
            layoutParams = LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT
            )
            orientation = LinearLayout.VERTICAL
        }
		tvText = TextView(this).apply {
			layoutParams = LinearLayout.LayoutParams(
				LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT
			).apply { topMargin = dpToPx(this@SalamActivity, 20f) }
			gravity = Gravity.CENTER_HORIZONTAL
			text = "Hello World"
			textSize = 20f
		}
		val layout = LinearLayout(this).apply {
			layoutParams = LinearLayout.LayoutParams(
				LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT
			).apply { setMargins(20, 20, 20, 20) }
		}
		val btnColor = Button(this).apply {
			layoutParams = LinearLayout.LayoutParams(
				LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT,
				1.0f
			).apply { rightMargin = dpToPx(this@SalamActivity, 5f) }
			text = "Color"
		}
		val btnAlign = Button(this).apply {
			layoutParams = LinearLayout.LayoutParams(
				LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT,
				1.0f
			).apply { rightMargin = dpToPx(this@SalamActivity, 5f) }
			text = "Alignment"
		}
		with(layout) {
			addView(btnColor)
			addView(btnAlign)
		}
		with(rootLayout) {
			addView(tvText)
			addView(layout)
		}
		setContentView(rootLayout)
		btnColor.setOnClickListener { _: View? ->
			val intent = Intent(this, ColorActivity::class.java)
			startActivityForResult(intent, REQUEST_CODE_COLOR)
		}
		btnAlign.setOnClickListener { _: View? ->
			val intent = Intent(this, AlignActivity::class.java)
			startActivityForResult(intent, REQUEST_CODE_ALIGN)
		}
	}
	override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
		Log.d(TAG, "requestCode = $requestCode, resultCode = $resultCode")
		if (resultCode == RESULT_OK) {
			when(requestCode) {
				REQUEST_CODE_COLOR -> {
					data?.let {
						val color = it.getIntExtra("color", Color.WHITE)
						tvText.setTextColor(color)
					}
				}
				REQUEST_CODE_ALIGN -> {
					data?.let {
						val align = it.getIntExtra("alignment", Gravity.LEFT)
						tvText.setGravity(align)
					}
				}
			}
		}
		else {
			Toast.makeText(this, "Wrong result", Toast.LENGTH_SHORT).show()
		}
	}
}
