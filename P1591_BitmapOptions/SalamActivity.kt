/* \uFDFD
 *                           \u0648\u0671\u0630\u0643\u0631 \u0631\u0628\u0651\u0643
 *              \u0643\u062A\u0628\u0647 \u0639\u0628\u062F \u0671\u0644\u0643\u0631\u064A\u0645
 *  \u06F1\u06F4\u06F4\u06F8 \u062C\u0645\u0627\u062F\u0649 \u0627\u0644\u0622\u062E\u0631\u0629 \u06F9
 */
package localhost.idroid.salamun

import java.io.File
import java.io.FileOutputStream

import android.util.Log
import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.os.Environment
import android.view.View
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color
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

		init {
			paint = Paint(Paint.ANTI_ALIAS_FLAG)
			val options = BitmapFactory.Options().apply {
				inJustDecodeBounds = false
			}
			bitmap = BitmapFactory.decodeResource(resources, android.R.drawable.btn_star_big_on, options)
			Log.d(TAG, "bitmap = $bitmap, width = ${options.outWidth}, height = ${options.outHeight}, mimetype = ${options.outMimeType}")
			options.inSampleSize = 2
			bitmap = BitmapFactory.decodeResource(resources, android.R.drawable.btn_star_big_on, options)
			Log.d(TAG, "width = ${bitmap.width}, height = ${bitmap.height}")

			val tempBitmap = Bitmap.createBitmap(300, 300, Bitmap.Config.ARGB_8888)
			val options2 = BitmapFactory.Options().apply {
				inBitmap = tempBitmap
			}
			bitmap = BitmapFactory.decodeResource(resources, android.R.drawable.btn_star_big_on, options2)
			Log.d(TAG, "bitmap = $bitmap, (${bitmap.width},${bitmap.height}), tempBitmap = $tempBitmap")
		}
		override fun onDraw(canvas: Canvas) {
			canvas.drawARGB(80, 102, 204, 255)
		}
	}
*/
	inner class DrawView(context: Context) : View(context) {
		private var paint: Paint
		private var bitmap: Bitmap

		init {
			paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
				setTextSize(40f)
			}
			var bmpIcon = BitmapFactory.decodeResource(resources, android.R.drawable.btn_star_big_on)
			bmpIcon = Bitmap.createScaledBitmap(bmpIcon, 500, 500, true)
			bitmap = Bitmap.createBitmap(500, 500, Bitmap.Config.RGB_565)
			val canvas = Canvas(bitmap).apply {
				drawColor(Color.WHITE)
				drawBitmap(bmpIcon, 0f, 0f, paint)
				drawText("Saved bitmap", 100f, 50f, paint)
			}
			val file = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "savedBitmap.png")
			try {
				FileOutputStream(file).use { fos ->
					bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos)
				}
			} catch (e: Exception) {
				e.printStackTrace()
			}
		}
		override fun onDraw(canvas: Canvas) {
			canvas.drawARGB(80, 102, 204, 255)
			canvas.drawBitmap(bitmap, 100f, 100f, paint)
		}
	}
}
