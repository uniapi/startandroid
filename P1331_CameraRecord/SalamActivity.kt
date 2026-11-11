/* \uFDFD
 *                           \u0648\u0671\u0630\u0643\u0631 \u0631\u0628\u0651\u0643
 *              \u0643\u062A\u0628\u0647 \u0639\u0628\u062F \u0671\u0644\u0643\u0631\u064A\u0645
 *  \u06F1\u06F4\u06F4\u06F8 \u062C\u0645\u0627\u062F\u0649 \u0627\u0644\u0622\u062E\u0631\u0629 \u06F1
 */
package localhost.idroid.salamun

import java.io.File
import java.io.FileOutputStream

import android.util.Log
import android.app.Activity
import android.hardware.Camera
import android.hardware.Camera.PictureCallback
import android.media.CamcorderProfile
import android.media.MediaRecorder
import android.os.Bundle
import android.os.Environment
import android.view.SurfaceView
import android.view.SurfaceHolder
import android.view.View
import android.view.Gravity
import android.view.ViewGroup.LayoutParams
import android.widget.RelativeLayout
import android.widget.Button

class SalamActivity : Activity() {
	companion object {
		private const val TAG = "localhost.idroid.salamun"
	}
	private lateinit var surfaceView: SurfaceView
	private var camera: Camera? = null
	private var mediaRecorder: MediaRecorder? = null
	private lateinit var photoFile: File
	private lateinit var videoFile: File

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		val rlRoot = RelativeLayout(this).apply {
			layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
		}
		surfaceView = SurfaceView(this).apply {
			id = R.id.surfaceView
			layoutParams = RelativeLayout.LayoutParams(200, 150).apply {
				addRule(RelativeLayout.ALIGN_PARENT_LEFT)
				addRule(RelativeLayout.ALIGN_PARENT_TOP)
			}
		}
		val btnTakePicture = Button(this).apply {
			id = R.id.btnTakePicture
			layoutParams = RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT).apply {
				addRule(RelativeLayout.ALIGN_PARENT_LEFT)
				addRule(RelativeLayout.BELOW, R.id.surfaceView)
				rightMargin = 10
				topMargin = 10
			}
			text = getString(R.string.take_picture)
			setOnClickListener { onClickPicture(it) }
		}
		val btnStartRecord = Button(this).apply {
			id = R.id.btnStartRecord
			layoutParams = RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT).apply {
				addRule(RelativeLayout.ALIGN_TOP, R.id.btnTakePicture)
				addRule(RelativeLayout.RIGHT_OF, R.id.btnTakePicture)
			}
			text = getString(R.string.start_record)
			setOnClickListener { onClickStartRecord(it) }
		}
		val btnStopRecord = Button(this).apply {
			id = R.id.btnStopRecord
			layoutParams = RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT).apply {
				addRule(RelativeLayout.ALIGN_TOP, R.id.btnStartRecord)
				addRule(RelativeLayout.RIGHT_OF, R.id.btnStartRecord)
			}
			text = getString(R.string.stop_record)
			setOnClickListener { onClickStopRecord(it) }
		}
		with(rlRoot) {
			addView(surfaceView)
			addView(btnTakePicture)
			addView(btnStartRecord)
			addView(btnStopRecord)
		}
		setContentView(rlRoot)

		val pictures = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
		photoFile = File(pictures, "myphoto.jpg")
		videoFile = File(pictures, "myvideo.3gp")

		val holder = surfaceView.holder
		holder.addCallback(object : SurfaceHolder.Callback {
			override fun surfaceCreated(holder: SurfaceHolder) {
				try {
					camera?.setPreviewDisplay(holder)
					camera?.startPreview()
				} catch (e: Exception) {
					e.printStackTrace()
				}
			}
			override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {

			}
			override fun surfaceDestroyed(holder: SurfaceHolder) {

			}
		})
	}
	override fun onResume() {
		super.onResume()
		camera = Camera.open()
	}
	override fun onPause() {
		super.onPause()
		releaseMediaRecorder()
		camera?.release()
		camera = null
	}
	fun onClickPicture(view: View?) {
		camera?.takePicture(null, null, object : PictureCallback {
			override fun onPictureTaken(data: ByteArray?, camera: Camera?) {
				if (data == null) return
				try {
					val fos = FileOutputStream(photoFile)
					fos.write(data)
					fos.close()
				} catch (e: Exception) {
					e.printStackTrace()
				}
			}
		})
	}
	fun onClickStartRecord(view: View?) {
		if (prepareVideoRecorder()) {
			mediaRecorder?.start()
		} else {
			releaseMediaRecorder()
		}
	}
	fun onClickStopRecord(view: View?) {
		mediaRecorder?.let { recorder ->
			recorder.stop()
			releaseMediaRecorder()
		}
	}
	private fun prepareVideoRecorder(): Boolean {
		val currentCamera = camera ?: return false
		try {
			currentCamera.unlock()
		} catch (e: Exception) {
			e.printStackTrace()
			return false
		}
		val recorder = MediaRecorder().also { mediaRecorder = it }
		recorder.setCamera(currentCamera)
		recorder.setAudioSource(MediaRecorder.AudioSource.CAMCORDER)
		recorder.setVideoSource(MediaRecorder.VideoSource.CAMERA)

		recorder.setProfile(CamcorderProfile.get(CamcorderProfile.QUALITY_HIGH))
		recorder.setOutputFile(videoFile.absolutePath)
		recorder.setPreviewDisplay(surfaceView.holder.surface)
		return try {
			recorder.prepare()
			true
		} catch (e: Exception) {
			e.printStackTrace()
			releaseMediaRecorder()
			false
		}
	}
	private fun releaseMediaRecorder() {
		mediaRecorder?.let { recorder ->
			recorder.reset()
			recorder.release()
			mediaRecorder = null
			try {
				camera?.lock()
			} catch (e: Exception) {
				e.printStackTrace()
			}
		}
	}
}
