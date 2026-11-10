/* \uFDFD
 *                           \u0648\u0671\u0630\u0643\u0631 \u0631\u0628\u0651\u0643
 *              \u0643\u062A\u0628\u0647 \u0639\u0628\u062F \u0671\u0644\u0643\u0631\u064A\u0645
 *  \u06F1\u06F4\u06F4\u06F8 \u062C\u0645\u0627\u062F\u0649 \u0627\u0644\u0623\u0648\u0644\u0649 \u06F3\u06F0
 */
package localhost.idroid.salamun

import java.io.File

import android.util.Log
import android.app.Activity
import android.graphics.Bitmap
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.View
import android.view.Gravity
import android.view.ViewGroup.LayoutParams
import android.widget.RelativeLayout
import android.widget.ImageView
import android.widget.Button

class SalamActivity : Activity() {
	private companion object {
		const val TAG = "localhost.idroid.salamun"
	}
	private lateinit var directory: File
	private val typePhoto = 1
	private val typeVideo = 2
	private val requestCodePhoto = 1
	private val requestCodeVideo = 2
	private lateinit var ivPhoto: ImageView
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		val llRoot = RelativeLayout(this).apply {
			layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
		}
		val btnPhoto = Button(this).apply {
			id = R.id.btnPhoto
			layoutParams = RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
			text = getString(R.string.photo)
			setOnClickListener { onClickPhoto(it) }
		}
		val btnVideo = Button(this).apply {
			id = R.id.btnVideo
			layoutParams = RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT).apply {
				addRule(RelativeLayout.BELOW, R.id.btnPhoto)
			}
			text = getString(R.string.video)
			setOnClickListener { onClickVideo(it) }
		}
		ivPhoto = ImageView(this).apply {
			id = R.id.ivPhoto
			layoutParams = RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT).apply {
				addRule(RelativeLayout.BELOW, R.id.btnVideo)
			}
		}
		with(llRoot) {
			addView(btnPhoto)
			addView(btnVideo)
			addView(ivPhoto)
		}
		setContentView(llRoot)

		createDirectory()
	}
	fun onClickPhoto(view: View?) {
		val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
//		intent.putExtra(MediaStore.EXTRA_OUTPUT, generateFileUri(typePhoto))
		startActivityForResult(intent, requestCodePhoto)
	}
	fun onClickVideo(view: View?) {
		val intent = Intent(MediaStore.ACTION_VIDEO_CAPTURE)
//		intent.putExtra(MediaStore.EXTRA_OUTPUT, generateFileUri(typeVideo))
		startActivityForResult(intent, requestCodeVideo)
	}
	override fun onActivityResult(requestCode: Int, resultCode: Int, intent: Intent?) {
		super.onActivityResult(requestCode, resultCode, intent)
		if (requestCode == requestCodePhoto) {
			when (resultCode) {
				RESULT_OK -> {
					if (intent == null) {
						Log.d(TAG, "Intent is null")
					} else {
						Log.d(TAG, "Photo uri: ${intent.data}")
						val bndl = intent.extras
						if (bndl != null) {
							val obj = intent.extras?.get("data")
							if (obj is Bitmap) {
								Log.d(TAG, "bitmap ${obj.width} x ${obj.height}")
								ivPhoto.setImageBitmap(obj)
							}
						}
					}
				}
				RESULT_CANCELED -> Log.d(TAG, "Canceled")
			}
		}
		if (requestCode == requestCodeVideo) {
			when (resultCode) {
				RESULT_OK -> {
					if (intent == null) {
						Log.d(TAG, "Intent is null")
					} else {
						Log.d(TAG, "Video uri: " + intent.data)
					}
				}
				RESULT_CANCELED -> Log.d(TAG, "Canceled")
			}
		}
	}
	private fun generateFileUri(type: Int): Uri? {
		var file: File? = null
		when (type) {
			typePhoto -> {
				file = File(directory.path + "/" + "photo_" + System.currentTimeMillis() + ".jpg")
			}
			typeVideo -> {
				file = File(directory.path + "/" + "video_" + System.currentTimeMillis() + ".mp4")
			}
		}
		Log.d(TAG, "fileName = $file")
		return if (file != null) Uri.fromFile(file) else null
	}
	private fun createDirectory() {
		directory = File(
			Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
			"MyFolder"
		)
		if (!directory.exists()) {
			directory.mkdirs()
		}
	}
}
