/* \uFDFD
 *                           \u0648\u0671\u0630\u0643\u0631 \u0631\u0628\u0651\u0643
 *              \u0643\u062A\u0628\u0647 \u0639\u0628\u062F \u0671\u0644\u0643\u0631\u064A\u0645
 *  \u06F1\u06F4\u06F4\u06F8 \u062C\u0645\u0627\u062F\u0649 \u0627\u0644\u0622\u062E\u0631\u0629 \u06F1\u06F5
 */
package localhost.idroid.salamun

import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.FloatBuffer

import android.opengl.GLES20.GL_COLOR_BUFFER_BIT
import android.opengl.GLES20.GL_FLOAT
import android.opengl.GLES20.GL_VERTEX_SHADER
import android.opengl.GLES20.GL_FRAGMENT_SHADER
import android.opengl.GLES20.GL_TRIANGLES
import android.opengl.GLES20.GL_LINES
import android.opengl.GLES20.GL_LINE_LOOP
import android.opengl.GLES20.GL_POINTS
import android.opengl.GLES20.glClear
import android.opengl.GLES20.glClearColor
import android.opengl.GLES20.glDrawArrays
import android.opengl.GLES20.glLineWidth
import android.opengl.GLES20.glEnableVertexAttribArray
import android.opengl.GLES20.glGetAttribLocation
import android.opengl.GLES20.glGetUniformLocation
import android.opengl.GLES20.glUniform4f
import android.opengl.GLES20.glUseProgram
import android.opengl.GLES20.glVertexAttribPointer
import android.opengl.GLES20.glViewport
import android.opengl.GLSurfaceView.Renderer
import android.content.Context

class OpenGLRenderer(private val context: Context) : Renderer {
	private var programId = 0
	private lateinit var vertexData: FloatBuffer
	private var aColorLocation = 0
	private var aPositionLocation = 0

	init {
		prepareData()
	}
	override fun onSurfaceCreated(arg0: GL10?, arg1: EGLConfig?) {
		glClearColor(0f, 0f, 0f, 1f)
		val vertexShaderId = ShaderUtils.createShader(context, GL_VERTEX_SHADER, R.raw.vertex_shader)
		val fragmentShaderId = ShaderUtils.createShader(context, GL_FRAGMENT_SHADER, R.raw.fragment_shader)
		programId = ShaderUtils.createProgram(vertexShaderId, fragmentShaderId)
		glUseProgram(programId)
		bindData()
	}
	override fun onSurfaceChanged(arg0: GL10?, width: Int, height: Int) {
		glViewport(0, 0, width, height)
	}
	private fun prepareData() {
		/*val vertices = floatArrayOf(
			-0.4f, 0.6f, 1.0f, 0.0f, 0.0f,
			0.4f, 0.6f, 0.0f, 1.0f, 0.0f,

			0.6f, 0.4f, 0.0f, 0.0f, 1.0f,
			0.6f, -0.4f, 1.0f, 1.0f, 1.0f,

			0.4f, -0.6f, 1.0f, 1.0f, 0.0f,
			-0.4f, -0.6f, 1.0f, 0.0f, 1.0f
		)*/
		val vertices = floatArrayOf(
			-0.5f, -0.2f, 1.0f, 0.0f, 0.0f,
			0.0f, 0.2f, 0.0f, 1.0f, 0.0f,
			0.5f, -0.2f, 0.0f, 0.0f, 1.0f
		)
		vertexData = ByteBuffer
			.allocateDirect(vertices.size * 4)
			.order(ByteOrder.nativeOrder())
			.asFloatBuffer()
			.apply {
				put(vertices)
			}
	}
	private fun bindData() {
		aPositionLocation = glGetAttribLocation(programId, "a_Position")
		vertexData.position(0)
		glVertexAttribPointer(aPositionLocation, 2, GL_FLOAT, false, 20, vertexData)
		glEnableVertexAttribArray(aPositionLocation)

		aColorLocation = glGetAttribLocation(programId, "a_Color")
		vertexData.position(2)
		glVertexAttribPointer(aColorLocation, 3, GL_FLOAT, false, 20, vertexData)
		glEnableVertexAttribArray(aColorLocation)
	}
	override fun onDrawFrame(arg0: GL10?) {
		//glClear(GL_COLOR_BUFFER_BIT)
		glLineWidth(5f)
		//glDrawArrays(GL_LINE_LOOP, 0, 6)
		glDrawArrays(GL_TRIANGLES, 0, 3)
	}
}
