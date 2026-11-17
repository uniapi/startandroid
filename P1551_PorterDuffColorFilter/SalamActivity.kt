/* \uFDFD
 *                           \u0648\u0671\u0630\u0643\u0631 \u0631\u0628\u0651\u0643
 *              \u0643\u062A\u0628\u0647 \u0639\u0628\u062F \u0671\u0644\u0643\u0631\u064A\u0645
 *  \u06F1\u06F4\u06F4\u06F8 \u062C\u0645\u0627\u062F\u0649 \u0627\u0644\u0622\u062E\u0631\u0629 \u06F8
 */
package localhost.idroid.salamun

import android.util.Log
import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.View
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.graphics.Paint

class SalamActivity : Activity() {
	companion object {
		private const val TAG = "localhost.idroid.salamun"
	}
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(DrawView(this))
	}
	inner class DrawView(context: Context) : View(context) {
		private var paints: Array<Paint>
		private var paintBorder: Paint
		private var bitmap: Bitmap
		private val size = 200
		// private val mode = PorterDuff.Mode.SRC
		// private val colorSrc = intArrayOf(Color.WHITE, Color.LTGRAY, Color.GRAY, Color.DKGRAY, Color.BLACK)
		private val mode = PorterDuff.Mode.MULTIPLY
		private val colorSrc = intArrayOf(Color.rgb(50, 0, 0), Color.rgb(100, 0, 0), Color.rgb(150, 0, 0),
			Color.rgb(200, 0, 0), Color.rgb(250, 0, 0)
		)

		init {
			if (android.os.Build.VERSION.SDK_INT >= 11) {
				setLayerType(View.LAYER_TYPE_SOFTWARE, null)
			}
			bitmap = BitmapFactory.decodeResource(resources, android.R.drawable.btn_star_big_on)
			bitmap = Bitmap.createScaledBitmap(bitmap, size, size, true)
			paints = Array(colorSrc.size) { i ->
				Paint(Paint.ANTI_ALIAS_FLAG).apply {
					colorFilter = PorterDuffColorFilter(colorSrc[i], mode)
				}
			}
			paintBorder = Paint().apply {
				setStyle(Paint.Style.STROKE)
				setStrokeWidth(3f)
				setColor(Color.BLACK)
			}
		}
		override fun onDraw(canvas: Canvas) {
			canvas.translate(0f, 200f)
			val delta = (canvas.width - size * paints.size) / (paints.size + 1)
			for (i in 0 until paints.size) {
				canvas.translate(delta.toFloat(), 0f)
				canvas.drawBitmap(bitmap, 0f, 0f, paints[i])
				canvas.drawRect(0f, 0f, size.toFloat(), size.toFloat(), paintBorder)
				canvas.translate(size.toFloat(), 0f)
			}
		}
	}
}
