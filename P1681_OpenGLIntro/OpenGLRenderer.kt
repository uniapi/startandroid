/* \uFDFD
 *                           \u0648\u0671\u0630\u0643\u0631 \u0631\u0628\u0651\u0643
 *              \u0643\u062A\u0628\u0647 \u0639\u0628\u062F \u0671\u0644\u0643\u0631\u064A\u0645
 *  \u06F1\u06F4\u06F4\u06F8 \u062C\u0645\u0627\u062F\u0649 \u0627\u0644\u0622\u062E\u0631\u0629 \u06F1\u06F5
 */
package localhost.idroid.salamun

import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10

import android.opengl.GLES20.GL_COLOR_BUFFER_BIT
import android.opengl.GLES20.glClear
import android.opengl.GLES20.glClearColor
import android.opengl.GLES20.glViewport
import android.opengl.GLSurfaceView.Renderer

class OpenGLRenderer : Renderer {
	override fun onDrawFrame(arg0: GL10?) {
		glClear(GL_COLOR_BUFFER_BIT)
	}
	override fun onSurfaceChanged(arg0: GL10?, width: Int, height: Int) {
		glViewport(0, 0, width, height)
	}
	override fun onSurfaceCreated(arg0: GL10?, arg1: EGLConfig?) {
		glClearColor(0f, 1f, 0f, 1f)
	}
}
