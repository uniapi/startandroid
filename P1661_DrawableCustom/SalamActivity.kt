/* \uFDFD
 *                           \u0648\u0671\u0630\u0643\u0631 \u0631\u0628\u0651\u0643
 *              \u0643\u062A\u0628\u0647 \u0639\u0628\u062F \u0671\u0644\u0643\u0631\u064A\u0645
 *  \u06F1\u06F4\u06F4\u06F8 \u062C\u0645\u0627\u062F\u0649 \u0627\u0644\u0622\u062E\u0631\u0629 \u06F1\u06F5
 */
package localhost.idroid.salamun

import android.util.Log
import android.app.Activity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup.LayoutParams
import android.widget.LinearLayout
import android.graphics.Color
import android.graphics.Canvas
import android.graphics.ColorFilter
import android.graphics.Bitmap
import android.graphics.BitmapFactory

class SalamActivity : Activity() {
	companion object {
		private const val TAG = "localhost.idroid.salamun"
	}

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		val density = resources.displayMetrics.density
        fun dp(value: Int): Int = (value * density).toInt()
		val dp16 = dp(16)
		val dp200 = dp(200)
		val llRoot = LinearLayout(this).apply {
			layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
			orientation = LinearLayout.VERTICAL
			setPadding(dp16, dp16, dp16, dp16)
		}
/*		val dr = ColorHexagonDrawable(Color.GREEN).apply {
			setAlpha(50)
		}
*/		val bitmap = BitmapFactory.decodeResource(resources, R.drawable.picture)
		val dr = BitmapHexagonDrawable(bitmap)
		val view = View(this).apply {
			layoutParams = LinearLayout.LayoutParams(dp200, dp200)
			//setBackgroundColor(Color.parseColor("#CCCCCC"))
			setBackgroundDrawable(dr)
		}
		llRoot.addView(view)
		setContentView(llRoot)

	}
}
