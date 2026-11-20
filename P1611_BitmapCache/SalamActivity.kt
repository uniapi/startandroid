/* \uFDFD
 *                           \u0648\u0671\u0630\u0643\u0631 \u0631\u0628\u0651\u0643
 *              \u0643\u062A\u0628\u0647 \u0639\u0628\u062F \u0671\u0644\u0643\u0631\u064A\u0645
 *  \u06F1\u06F4\u06F4\u06F8 \u062C\u0645\u0627\u062F\u0649 \u0627\u0644\u0622\u062E\u0631\u0629 \u06F9
 */
package localhost.idroid.salamun

import java.io.File
import java.lang.Runtime

import android.util.Log
import android.util.LruCache
import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.os.Environment
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams
import android.view.LayoutInflater
import android.graphics.Bitmap
import android.widget.RelativeLayout
import android.widget.ImageView
import android.widget.ListView
import android.widget.ArrayAdapter

class SalamActivity : Activity() {
	companion object {
		private const val TAG = "localhost.idroid.salamun"
	}
	private lateinit var mLvImages: ListView

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		val rlRoot = RelativeLayout(this).apply {
			layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
		}
		mLvImages = ListView(this).apply {
			layoutParams = RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
		}
		rlRoot.addView(mLvImages)
		setContentView(rlRoot)

		val dir = File(Environment.getExternalStorageDirectory(), "Download/L0161")
		val filesArray = dir.listFiles()
		if (filesArray != null) {
			val adapter = ImageAdapter(this, filesArray)
			mLvImages.setAdapter(adapter)
		}
	}
	class ImageAdapter(context: Context, objects: Array<File>) : ArrayAdapter<File>(context, R.layout.list_item, objects) {
		private var mInflater: LayoutInflater
		private var mSize: Int
		private val mMemoryCache: LruCache<String, Bitmap>

		init {
			mInflater = LayoutInflater.from(context)
			mSize = context.resources.getDimensionPixelSize(R.dimen.image_size)
			val maxMemory = Runtime.runtime.maxMemory() as Int
			val cacheSize = maxMemory / 8

			mMemoryCache = object : LruCache<String, Bitmap>(cacheSize) {
				override fun sizeOf(key: String, bitmap: Bitmap): Int {
					return bitmap.byteCount
				}
			}
		}
		override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
			val view = convertView ?: mInflater.inflate(R.layout.list_item, parent, false)
			val imageView = view.findViewById(R.id.imageView) as? ImageView
			val bitmap = getBitmap(position)
			imageView?.setImageBitmap(bitmap)
			return view
		}
		private fun getBitmap(position: Int): Bitmap {
			val filePath = getItem(position).absolutePath
			var bitmap = getBitmapFromMemCache(filePath)
			if (bitmap == null) {
				bitmap = Utils.decodeSampledBitmapFromResource(filePath, mSize, mSize)
				addBitmapToMemoryCache(filePath, bitmap)
			}
			return bitmap
		}
		fun addBitmapToMemoryCache(key: String, bitmap: Bitmap) {
			if (getBitmapFromMemCache(key) == null) {
				mMemoryCache.put(key, bitmap)
			}
		}
		fun getBitmapFromMemCache(key: String): Bitmap {
			return mMemoryCache.get(key)
		}
	}
}
