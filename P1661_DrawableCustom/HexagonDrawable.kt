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
import android.graphics.Paint
import android.graphics.Path
import android.graphics.PixelFormat
import android.graphics.Rect
import android.graphics.drawable.Drawable

open class HexagonDrawable : Drawable() {
	companion object {
		private const val TAG = "localhost.idroid.salamun"
	}
	private val mPaint = Paint(Paint.ANTI_ALIAS_FLAG)
	private val mPath = Path()

	override fun draw(canvas: Canvas) {
		canvas.drawPath(mPath, mPaint)
	}
	override fun getOpacity(): Int {
		return PixelFormat.TRANSLUCENT
	}
	override fun setAlpha(alpha: Int) {
		mPaint.setAlpha(alpha)
	}
	override fun setColorFilter(colorFilter: ColorFilter) {
		mPaint.setColorFilter(colorFilter)
	}
	override fun onBoundsChange(bounds: Rect) {
		super.onBoundsChange(bounds)
		val width = bounds.width()
		val height = bounds.height()
		with(mPath) {
			reset()
			moveTo(0f, (height / 2).toFloat())
			lineTo((width / 4).toFloat(), 0f)
			lineTo((width * 3 / 4).toFloat(), 0f)
			lineTo(width.toFloat(), (height / 2).toFloat())
			lineTo((width * 3 /4).toFloat(), height.toFloat())
			lineTo((width / 4).toFloat(), height.toFloat())
			close()
		}
	}
	protected fun getPaint(): Paint = mPaint
}
