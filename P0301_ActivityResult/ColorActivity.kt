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
import android.graphics.Color

class ColorActivity : Activity(), View.OnClickListener {
	private lateinit var btnRed: Button
	private lateinit var btnGreen: Button
	private lateinit var btnBlue: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val rootLayout = LinearLayout(this).apply {
            layoutParams = LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT
            )
            orientation = LinearLayout.HORIZONTAL
        }
		btnRed = Button(this).apply {
			id = 1
			layoutParams = LinearLayout.LayoutParams(
				LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT,
				1.0f
			).apply { topMargin = dpToPx(this@ColorActivity, 5f) }
			text = "Red"
		}
		btnGreen = Button(this).apply {
			id = 2
			layoutParams = LinearLayout.LayoutParams(
				LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT,
				1.0f
			).apply { topMargin = dpToPx(this@ColorActivity, 5f) }
			text = "Green"
		}
		btnBlue = Button(this).apply {
			id = 3
			layoutParams = LinearLayout.LayoutParams(
				LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT,
				1.0f
			).apply { topMargin = dpToPx(this@ColorActivity, 5f) }
			text = "Blue"
		}
		with(rootLayout) {
			addView(btnRed)
			addView(btnGreen)
			addView(btnBlue)
		}
		setContentView(rootLayout)
		btnRed.setOnClickListener(this)
		btnGreen.setOnClickListener(this)
		btnBlue.setOnClickListener(this)
	}
	override fun onClick(view: View?) {
		val intent = Intent()
		when (view?.id) {
			btnRed.id -> intent.putExtra("color", Color.RED)
			btnGreen.id -> intent.putExtra("color", Color.GREEN)
			btnBlue.id -> intent.putExtra("color", Color.BLUE)
		}
		setResult(RESULT_OK, intent)
		finish()
	}
}
