/* \uFDFD
 *                     \u0648\u0671\u0630\u0643\u0631 \u0631\u0628\u0651\u0643
 *        \u0643\u062A\u0628\u0647 \u0639\u0628\u062F \u0671\u0644\u0643\u0631\u064A\u0645
 *  \u06F1\u06F4\u06F4\u06F8 \u0631\u0628\u064A\u0639 \u0671\u0644\u0622\u062E\u0631 \u06F1\u06F5
 */
package localhost.idroid.salamun

import android.content.Intent
import android.app.Activity
import android.os.Bundle
import android.view.View
import android.view.Gravity
import android.view.ViewGroup.LayoutParams
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Button

class AlignActivity : Activity(), View.OnClickListener {
	private lateinit var btnLeft: Button
	private lateinit var btnCenter: Button
	private lateinit var btnRight: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val rootLayout = LinearLayout(this).apply {
            layoutParams = LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT
            )
            orientation = LinearLayout.HORIZONTAL
        }
		btnLeft = Button(this).apply {
			id = 1
			layoutParams = LinearLayout.LayoutParams(
				LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT,
				1.0f
			).apply { topMargin = dpToPx(this@AlignActivity, 5f) }
			text = "Left"
		}
		btnCenter = Button(this).apply {
			id = 2
			layoutParams = LinearLayout.LayoutParams(
				LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT,
				1.0f
			).apply { topMargin = dpToPx(this@AlignActivity, 5f) }
			text = "Center"
		}
		btnRight = Button(this).apply {
			id = 3
			layoutParams = LinearLayout.LayoutParams(
				LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT,
				1.0f
			).apply { topMargin = dpToPx(this@AlignActivity, 5f) }
			text = "Right"
		}
		with(rootLayout) {
			addView(btnLeft)
			addView(btnCenter)
			addView(btnRight)
		}
		setContentView(rootLayout)
		btnLeft.setOnClickListener(this)
		btnCenter.setOnClickListener(this)
		btnRight.setOnClickListener(this)
	}
	override fun onClick(view: View?) {
		val intent = Intent()
		when (view?.id) {
			btnLeft.id -> intent.putExtra("alignment", Gravity.LEFT)
			btnCenter.id -> intent.putExtra("alignment", Gravity.CENTER)
			btnRight.id -> intent.putExtra("alignment", Gravity.RIGHT)
		}
		setResult(RESULT_OK, intent)
		finish()
	}
}
