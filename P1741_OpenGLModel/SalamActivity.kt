/* \uFDFD
 *                           \u0648\u0671\u0630\u0643\u0631 \u0631\u0628\u0651\u0643
 *              \u0643\u062A\u0628\u0647 \u0639\u0628\u062F \u0671\u0644\u0643\u0631\u064A\u0645
 *  \u06F1\u06F4\u06F4\u06F8 \u062C\u0645\u0627\u062F\u0649 \u0627\u0644\u0622\u062E\u0631\u0629 \u06F1\u06F5
 */
package localhost.idroid.salamun

import android.util.Log
import android.app.Activity
import android.app.ActivityManager
import android.content.Context
import android.content.pm.ConfigurationInfo
import android.os.Bundle
import android.opengl.GLSurfaceView
import android.widget.Toast

class SalamActivity : Activity() {
	companion object {
		private const val TAG = "localhost.idroid.salamun"
	}
	private lateinit var glSurfaceView: GLSurfaceView

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		if (!supportES2()) {
			Toast.makeText(this, "OpenGL ES 2.0 is not supported", Toast.LENGTH_LONG).show()
			finish()
			return
		}
		glSurfaceView = GLSurfaceView(this)
		glSurfaceView.setEGLContextClientVersion(2)
		glSurfaceView.setRenderer(OpenGLRenderer(this))
		setContentView(glSurfaceView)
	}
	override fun onPause() {
		super.onPause()
		glSurfaceView.onPause()
	}
	override fun onResume() {
		super.onResume()
		glSurfaceView.onResume()
	}
	private fun supportES2(): Boolean {
		val activityManager = getSystemService(Context.ACTIVITY_SERVICE) as? ActivityManager
		val configurationInfo = activityManager?.getDeviceConfigurationInfo()
		if (configurationInfo == null) {
			return false
		}
		return configurationInfo.reqGlEsVersion >= 0x20000
	}
}
