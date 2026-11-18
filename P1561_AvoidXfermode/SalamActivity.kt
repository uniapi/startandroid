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
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.AvoidXfermode
import android.graphics.AvoidXfermode.Mode
import android.graphics.Paint
import android.graphics.Rect

class SalamActivity : Activity() {
	companion object {
		private const val TAG = "localhost.idroid.salamun"
	}
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(DrawView(this))
	}
	inner class DrawView(context: Context) : View(context) {
		private var paint: Paint
		private var rect: Rect
		private var bitmap: Bitmap? = null
		// private val mode = Mode.AVOID
		private val mode = Mode.TARGET

		init {
			paint = Paint(Paint.ANTI_ALIAS_FLAG)
			rect = Rect(0, 0, 50, 100)
			createBitmap()
		}
		override fun onDraw(canvas: Canvas) {
			canvas.drawARGB(80, 102, 204, 255)
			canvas.drawBitmap(bitmap, 0f, 0f, paint)
		}
		private fun createBitmap() {
			bitmap = Bitmap.createBitmap(1100, 700, Bitmap.Config.ARGB_8888)
			val bitmapCanvas = Canvas(bitmap)
			val redPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
				setStyle(Paint.Style.FILL_AND_STROKE)
				setColor(Color.RED)
			}
			drawBitmap(bitmapCanvas, redPaint)
			redPaint.setXfermode(AvoidXfermode(Color.BLUE, 0, mode))
			drawBitmap(bitmapCanvas, redPaint)
			redPaint.setXfermode(AvoidXfermode(Color.BLUE, 127, mode))
			drawBitmap(bitmapCanvas, redPaint)
			redPaint.setXfermode(AvoidXfermode(Color.BLUE, 255, mode))
			drawBitmap(bitmapCanvas, redPaint)
		}
		private fun drawBitmap(bitmapCanvas: Canvas, redPaint: Paint) {
			val bitmapPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
				setStyle(Paint.Style.FILL_AND_STROKE)
			}
			bitmapCanvas.save()
			bitmapCanvas.translate(25f, 25f)
			for (i in 0 until 20) {
				bitmapPaint.setColor(Color.rgb(i * 10, i * 10, 255))
				bitmapCanvas.drawRect(rect, bitmapPaint)
				bitmapCanvas.translate(50f, 0f)
			}
			bitmapCanvas.restore()
			bitmapCanvas.drawRect(10f, 75f, 1035f, 150f, redPaint)
			bitmapCanvas.translate(0f, 150f)
		}
	}
}
