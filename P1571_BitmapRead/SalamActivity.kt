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
import android.graphics.Matrix
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
		private var bitmap: Bitmap
		private var rectSrc: Rect
		private var rectDst: Rect
		private var m: Matrix

		init {
			paint = Paint(Paint.ANTI_ALIAS_FLAG)
			bitmap = BitmapFactory.decodeResource(resources, android.R.drawable.btn_star_big_on)
			Log.d(TAG, "Info: size = ${bitmap.width} x ${bitmap.height}, bytes = ${bitmap.byteCount} (${bitmap.rowBytes}), config = ${bitmap.config}")
			m = Matrix().apply {
				postRotate(45f)
				postScale(2f, 3f)
				postTranslate(200f, 50f)
			}
			rectSrc = Rect(0, 0, bitmap.width / 2, bitmap.height / 2)
			rectDst = Rect(300, 100, 500, 200)
		}
		override fun onDraw(canvas: Canvas) {
			canvas.drawARGB(80, 102, 204, 255)
			canvas.drawBitmap(bitmap, 50f, 50f, paint)
			canvas.drawBitmap(bitmap, m, paint)
			canvas.drawBitmap(bitmap, rectSrc, rectDst, paint)
		}
	}
}
