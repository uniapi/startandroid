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
import android.graphics.ColorFilter
import android.graphics.ColorMatrix
import android.graphics.ColorMatrixColorFilter
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
		//private var bitmap: Bitmap
		private var rect: Rect
		private var cmData = floatArrayOf(
			-1f, 0f, 0f, 0f, 255f,
			0f, -1f, 0f, 0f, 255f,
			0f, 0f, -1f, 0f, 255f,
			0f, 0f, 0f, 1f, 0f
		)
		private var cm: ColorMatrix
		private var filter: ColorFilter
		private var icon: Bitmap

		init {
			rect = Rect(0, 0, 200, 200)
			paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
				setStyle(Paint.Style.FILL_AND_STROKE)
			}
			icon = BitmapFactory.decodeResource(context.resources, android.R.drawable.btn_star_big_on)
			cm = ColorMatrix(cmData).apply {
				setScale(1f, 1f, 0f, 0.5f)
			}
			filter = ColorMatrixColorFilter(cm)
		}
		override fun onDraw(canvas: Canvas) {
			canvas.drawARGB(80, 102, 204, 255)

			canvas.translate(100f, 100f)
			drawObjects(canvas)

			paint.setColorFilter(filter)
			canvas.translate(0f, 300f)
			drawObjects(canvas)
		}
		private fun drawObjects(canvas: Canvas) {
			canvas.save()
			paint.setColor(Color.RED)
			canvas.drawRect(rect, paint)

			paint.setColor(Color.GREEN)
			canvas.translate(220f, 0f)
			canvas.drawRect(rect, paint)
			paint.setColor(Color.BLUE)
			canvas.translate(220f, 0f)
			canvas.drawRect(rect, paint)
			paint.setColor(Color.WHITE)
			canvas.translate(220f, 0f)
			canvas.drawRect(rect, paint)

			canvas.translate(220f, 0f)
			canvas.drawBitmap(icon, null, rect, paint)
			canvas.restore()
		}
	}
}
