/* \uFDFD
 *                           \u0648\u0671\u0630\u0643\u0631 \u0631\u0628\u0651\u0643
 *              \u0643\u062A\u0628\u0647 \u0639\u0628\u062F \u0671\u0644\u0643\u0631\u064A\u0645
 *  \u06F1\u06F4\u06F4\u06F8 \u062C\u0645\u0627\u062F\u0649 \u0627\u0644\u0622\u062E\u0631\u0629 \u06F9
 */
package localhost.idroid.salamun

import android.util.Log
import android.util.DisplayMetrics
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

class SalamActivity : Activity() {
	companion object {
		private const val TAG = "localhost.idroid.salamun"
	}
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(DrawView(this))
	}
/*	inner class DrawView(context: Context) : View(context) {
		private var paint: Paint
		private var bitmap: Bitmap
		private var bitmapSource: Bitmap
		private var m: Matrix

		init {
			paint = Paint(Paint.ANTI_ALIAS_FLAG)
			bitmapSource = BitmapFactory.decodeResource(resources, android.R.drawable.btn_star_big_on)

			m = Matrix().apply {
				postScale(10f, 15f)
				postRotate(45f)
			}
			bitmap = Bitmap.createBitmap(bitmapSource, 0, 0, bitmapSource.width / 2, bitmapSource.height / 2, m, true)
		}
		override fun onDraw(canvas: Canvas) {
			canvas.drawARGB(80, 102, 204, 255)
			canvas.drawBitmap(bitmap, 0f, 0f, paint)
		}
	}
*/
/*	inner class DrawView(context: Context) : View(context) {
		private var paint: Paint
		private var bitmap: Bitmap
		private var bitmapAlpha: Bitmap

		init {
			paint = Paint(Paint.ANTI_ALIAS_FLAG)
			val colors = IntArray(300 * 300).apply {
				fill(Color.argb(85, 255, 0, 0), 0, 300 * 100)
				fill(Color.GREEN, 300 * 100, 300 * 200)
				fill(Color.BLUE, 300 * 200, 300 * 300)
			}
			bitmap = Bitmap.createBitmap(colors, 300, 300, Bitmap.Config.RGB_565)
			bitmapAlpha = Bitmap.createBitmap(colors, 300, 300, Bitmap.Config.ARGB_8888)
		}
		override fun onDraw(canvas: Canvas) {
			canvas.drawBitmap(bitmap, 50f, 50f, paint)
			canvas.drawBitmap(bitmapAlpha, 550f, 50f, paint)
		}
	}
*/
/*	inner class DrawView(context: Context) : View(context) {
		private var paint: Paint
		private var bitmap: Bitmap

		init {
			paint = Paint(Paint.ANTI_ALIAS_FLAG)
			bitmap = Bitmap.createBitmap(100, 100, Bitmap.Config.RGB_565).apply {
				setPixel(20, 20, Color.RED)
				setPixel(70, 50, Color.RED)
				setPixel(30, 80, Color.RED)
			}
			val colors = IntArray(10 * 15).apply {
				fill(Color.GREEN, 0, 10 * 15)
			}
			bitmap.setPixels(colors, 0, 10, 40, 40, 10, 15)
			val canvas = Canvas(bitmap)
			val p = Paint().apply {
				setColor(Color.BLUE)
			}
			canvas.drawCircle(80f, 80f, 10f, p)
		}
		override fun onDraw(canvas: Canvas) {
			canvas.drawARGB(80, 102, 204, 255)
			canvas.drawBitmap(bitmap, 50f, 50f, paint)
		}
	}
*/
	inner class DrawView(context: Context) : View(context) {
		private var paint: Paint
		private var bitmap1: Bitmap
		private var bitmap2: Bitmap
		private var bitmap3: Bitmap

		init {
			paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
				setStyle(Paint.Style.STROKE)
			}
			bitmap1 = Bitmap.createBitmap(100, 100, Bitmap.Config.RGB_565)
			bitmap2 = Bitmap.createBitmap(100, 100, Bitmap.Config.RGB_565)
			bitmap3 = Bitmap.createBitmap(100, 100, Bitmap.Config.RGB_565)
			val bitmapIcon = BitmapFactory.decodeResource(resources, android.R.drawable.btn_star_big_on)
			val canvas1 = Canvas(bitmap1)
			canvas1.drawBitmap(bitmapIcon, 0f, 0f, paint)
			val canvas2 = Canvas(bitmap2)
			canvas2.drawBitmap(bitmapIcon, 0f, 0f, paint)
			bitmap2.setDensity(DisplayMetrics.DENSITY_XHIGH)
			bitmap3.setDensity(DisplayMetrics.DENSITY_XHIGH)
			val canvas3 = Canvas(bitmap3)
			canvas3.drawBitmap(bitmapIcon, 0f, 0f, paint)
		}
		override fun onDraw(canvas: Canvas) {
			with(canvas) {
				drawARGB(80, 102, 204, 255)

				translate(100f, 100f)
				drawRect(0f, 0f, 100f, 100f, paint)
				drawBitmap(bitmap1, 0f, 0f, paint)

				translate(200f, 0f)
				drawRect(0f, 0f, 100f, 100f, paint)
				drawBitmap(bitmap2, 0f, 0f, paint)

				translate(200f, 0f)
				drawRect(0f, 0f, 100f, 100f, paint)
				drawBitmap(bitmap3, 0f, 0f, paint)
			}
		}
	}
}
