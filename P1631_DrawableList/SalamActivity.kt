/* \uFDFD
 *                           \u0648\u0671\u0630\u0643\u0631 \u0631\u0628\u0651\u0643
 *              \u0643\u062A\u0628\u0647 \u0639\u0628\u062F \u0671\u0644\u0643\u0631\u064A\u0645
 *  \u06F1\u06F4\u06F4\u06F8 \u062C\u0645\u0627\u062F\u0649 \u0627\u0644\u0622\u062E\u0631\u0629 \u06F9
 */
package localhost.idroid.salamun

import android.util.Log
import android.app.Activity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup.LayoutParams
import android.widget.RelativeLayout
import android.widget.ImageView
import android.graphics.Color
import android.graphics.drawable.GradientDrawable

class SalamActivity : Activity() {
	companion object {
		private const val TAG = "localhost.idroid.salamun"
	}
	private lateinit var imageView: ImageView

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		val density = resources.displayMetrics.density
        fun dp(value: Int): Int = (value * density).toInt()
		val dp16 = dp(16)
		val dp200 = dp(200)
		val rlRoot = RelativeLayout(this).apply {
			layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
			setPadding(dp16, dp16, dp16, dp16)
		}
		imageView = ImageView(this).apply {
			layoutParams = RelativeLayout.LayoutParams(dp200, dp200).apply {
				addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE)
			}
			setBackgroundColor(Color.parseColor("#CCCCCC"))
			scaleType = ImageView.ScaleType.CENTER
		}
		rlRoot.addView(imageView)
		setContentView(rlRoot)

		setDrawable()
	}
	private fun setDrawable() {
		val drawable = resources.getDrawable(R.drawable.layer)
		imageView.scaleType = ImageView.ScaleType.FIT_XY
		imageView.setImageDrawable(drawable)
	}
}
