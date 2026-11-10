/* \uFDFD
 *                           \u0648\u0671\u0630\u0643\u0631 \u0631\u0628\u0651\u0643
 *              \u0643\u062A\u0628\u0647 \u0639\u0628\u062F \u0671\u0644\u0643\u0631\u064A\u0645
 *  \u06F1\u06F4\u06F4\u06F8 \u062C\u0645\u0627\u062F\u0649 \u0627\u0644\u0623\u0648\u0644\u0649 \u06F3\u06F0
 */
package localhost.idroid.salamun

import java.io.IOException

import android.util.Log
import android.app.Activity
import android.os.Bundle
import android.hardware.Camera
import android.hardware.Camera.CameraInfo
import android.hardware.Camera.Size
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.view.Gravity
import android.view.ViewGroup.LayoutParams
import android.view.Display
import android.view.Surface
import android.view.SurfaceHolder
import android.view.SurfaceView
import android.widget.FrameLayout
import android.graphics.Matrix
import android.graphics.RectF

class SalamActivity : Activity() {
	companion object {
		private const val TAG = "localhost.idroid.salamun"
	}
	private lateinit var sv: SurfaceView
	private lateinit var holder: SurfaceHolder
	private lateinit var holderCallback: HolderCallback
	private var camera: Camera? = null
	private val cameraId = 0
	private val fullScreen = true

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		requestWindowFeature(Window.FEATURE_NO_TITLE)
		window.setFlags(
			WindowManager.LayoutParams.FLAG_FULLSCREEN,
			WindowManager.LayoutParams.FLAG_FULLSCREEN
		)
		val flRoot = FrameLayout(this).apply {
			layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
		}
		sv = SurfaceView(this).apply {
			layoutParams = FrameLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT).apply {
				gravity = Gravity.CENTER
			}
		}
		with(flRoot) {
			addView(sv)
		}
		setContentView(flRoot)

		holder = sv.holder
		holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS)

		holderCallback = HolderCallback()
		holder.addCallback(holderCallback)
	}
	override fun onResume() {
		super.onResume()
		camera = Camera.open(cameraId)
		setPreviewSize(fullScreen)
	}
	override fun onPause() {
		super.onPause()
		camera?.release()
		camera = null
	}
	inner class HolderCallback : SurfaceHolder.Callback {
		override fun surfaceCreated(holder: SurfaceHolder) {
			try {
				camera?.setPreviewDisplay(holder)
				camera?.startPreview()
			} catch (e: IOException) {
				e.printStackTrace()
			}
		}
		override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {
			camera?.stopPreview()
			setCameraDisplayOrientation(cameraId)
			try {
				camera?.setPreviewDisplay(holder)
				camera?.startPreview()
			} catch (e: Exception) {
				e.printStackTrace()
			}
		}
		override fun surfaceDestroyed(holder: SurfaceHolder) {

		}
	}
	private fun setPreviewSize(fullScreen: Boolean) {
		val currentCamera = camera ?: return

		val display = windowManager.defaultDisplay
		val widthIsMax = display.width > display.height
		val size = currentCamera.parameters.previewSize

		val rectDisplay = RectF()
		val rectPreview = RectF()
		rectDisplay.set(0f, 0f, display.width.toFloat(), display.height.toFloat())
		// RectF preview
		if (widthIsMax) {
			// preview is in horizontal orientation
			rectPreview.set(0f, 0f, size.width.toFloat(), size.height.toFloat())
		} else {
			// preview is in vertical orientation
			rectPreview.set(0f, 0f, size.height.toFloat(), size.width.toFloat())
		}
		val matrix = Matrix()
		// preparing conversion matrix
		if (!fullScreen) {
			matrix.setRectToRect(rectPreview, rectDisplay, Matrix.ScaleToFit.START)
		} else {
			matrix.setRectToRect(rectDisplay, rectPreview, Matrix.ScaleToFit.START)
			matrix.invert(matrix)
		}
		matrix.mapRect(rectPreview)
		sv.layoutParams.height = rectPreview.bottom.toInt()
		sv.layoutParams.width = rectPreview.right.toInt()
	}
	private fun setCameraDisplayOrientation(cameraId: Int) {
		val rotation = windowManager.defaultDisplay.rotation
		var degrees = 0
		when (rotation) {
			Surface.ROTATION_0 -> degrees = 0
			Surface.ROTATION_90 -> degrees = 90
			Surface.ROTATION_180 -> degrees = 180
			Surface.ROTATION_270 -> degrees = 270
		}
		var result: Int
		val info = CameraInfo()
		Camera.getCameraInfo(cameraId, info)
		// rear camera
		if (info.facing == CameraInfo.CAMERA_FACING_BACK) {
			result = (360 - degrees) + info.orientation
		} else {
			// front camera
			result = (360 - degrees) - info.orientation
			result += 360
		}
		result %= 360
		camera?.setDisplayOrientation(result)
	}
}
