/* \uFDFD
 *                           \u0648\u0671\u0630\u0643\u0631 \u0631\u0628\u0651\u0643
 *              \u0643\u062A\u0628\u0647 \u0639\u0628\u062F \u0671\u0644\u0643\u0631\u064A\u0645
 *  \u06F1\u06F4\u06F4\u06F8 \u062C\u0645\u0627\u062F\u0649 \u0627\u0644\u0622\u062E\u0631\u0629 \u06F9
 */
package localhost.idroid.salamun

import java.io.File
import java.lang.Runtime

import android.util.Log
import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.os.Environment
import android.view.View
import android.view.ViewGroup.LayoutParams
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.widget.RelativeLayout
import android.widget.ImageView
class SalamActivity : Activity() {
	companion object {
		private const val TAG = "localhost.idroid.salamun"

		@JvmStatic
		fun decodeSampledBitmapFromResource(path: String, reqWidth: Int, reqHeight: Int): Bitmap {
			val options = BitmapFactory.Options().apply {
				inJustDecodeBounds = true
			}
			BitmapFactory.decodeFile(path, options)
			options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight)
			options.inJustDecodeBounds = false
			options.inPreferredConfig = Bitmap.Config.RGB_565
			return BitmapFactory.decodeFile(path, options)
		}
		fun calculateInSampleSize(options: BitmapFactory.Options, reqWidth: Int, reqHeight: Int): Int {
			val height = options.outHeight
			val width = options.outWidth
			var inSampleSize = 1
			if (height > reqHeight || width > reqWidth) {
				val halfHeight = height / 2
				val halfWidth = width / 2

				while ((halfHeight / inSampleSize) > reqHeight && (halfWidth / inSampleSize) > reqWidth) {
					inSampleSize *= 2
				}
			}
			return inSampleSize
		}
	}
	private lateinit var mImageView: ImageView

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		val rlRoot = RelativeLayout(this).apply {
			layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
		}
		val imageSize = resources.getDimensionPixelSize(R.dimen.image_size)
		mImageView = ImageView(this).apply {
			layoutParams = RelativeLayout.LayoutParams(imageSize, imageSize).apply {
				addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE)
			}
		}
		rlRoot.addView(mImageView)
		setContentView(rlRoot)

		logMemory()
		readImage()
		logMemory()
	}
	private fun readImage() {
		val px = resources.getDimensionPixelSize(R.dimen.image_size)
		val file = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "map.jpg")
		Log.d(TAG, "absolute path: ${file.absolutePath}")
		// val bitmap = BitmapFactory.decodeFile(file.absolutePath)
		val bitmap = decodeSampledBitmapFromResource(file.absolutePath, px, px)
		Log.d(TAG, "Required size = $px, bitmap size = ${bitmap.width}x${bitmap.height}, byteCount = ${bitmap.byteCount / 1024}")
		mImageView.setImageBitmap(bitmap)
	}
	private fun logMemory() {
		Log.d(TAG, "Total memory = ${Runtime.getRuntime().totalMemory() / 1024}")
	}
}
