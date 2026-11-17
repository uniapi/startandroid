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
import android.graphics.PorterDuff
import android.graphics.PorterDuffXfermode
import android.graphics.Paint
import android.graphics.Path

class SalamActivity : Activity() {
	companion object {
		private const val TAG = "localhost.idroid.salamun"
	}
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(DrawView(this))
	}
	inner class DrawView(context: Context) : View(context) {
		private var paintSrc: Paint
		private var paintDst: Paint
		private var paintBorder: Paint
		private var pathSrc: Path
		private var pathDst: Path
		private var bitmapSrc: Bitmap
		private var bitmapDst: Bitmap
		private val mode = PorterDuff.Mode.DST_OUT
		private val colorDst = Color.argb(170, 0, 0, 255)
		private val colorSrc = Color.argb(85, 255, 255, 0)

		init {
			if (android.os.Build.VERSION.SDK_INT >= 11) {
				setLayerType(View.LAYER_TYPE_SOFTWARE, null)
			}
			pathDst = Path().apply {
				moveTo(0f, 0f)
				lineTo(500f, 0f)
				lineTo(500f, 500f)
				close()
			}
			bitmapDst = createBitmap(pathDst, colorDst)
			paintDst = Paint()
			pathSrc = Path().apply {
				moveTo(0f, 0f)
				lineTo(500f, 0f)
				lineTo(0f, 500f)
				close()
			}
			bitmapSrc = createBitmap(pathSrc, colorSrc)
			paintSrc = Paint().apply {
				setXfermode(PorterDuffXfermode(mode))
			}
			paintBorder = Paint().apply {
				setStyle(Paint.Style.STROKE)
				setStrokeWidth(3f)
				setColor(Color.BLACK)
			}
		}
		private fun createBitmap(path: Path, color: Int): Bitmap {
			val bitmap = Bitmap.createBitmap(500, 500, Bitmap.Config.ARGB_8888)
			val bitmapCanvas = Canvas(bitmap)
			val paint = Paint().apply {
				setStyle(Paint.Style.FILL_AND_STROKE)
				setColor(color)
			}
			bitmapCanvas.drawPath(path, paint)
			return bitmap
		}
		override fun onDraw(canvas: Canvas) {
			with(canvas) {
				translate(390f, 80f)
				drawBitmap(bitmapDst, 0f, 0f, paintDst)
				drawBitmap(bitmapSrc, 0f, 0f, paintSrc)
				drawRect(0f, 0f, 500f, 500f, paintBorder)
			}
		}
	}
}
