/* \uFDFD
 *                           \u0648\u0671\u0630\u0643\u0631 \u0631\u0628\u0651\u0643
 *              \u0643\u062A\u0628\u0647 \u0639\u0628\u062F \u0671\u0644\u0643\u0631\u064A\u0645
 *  \u06F1\u06F4\u06F4\u06F8 \u062C\u0645\u0627\u062F\u0649 \u0627\u0644\u0622\u062E\u0631\u0629 \u06F1\u06F6
 */
package localhost.idroid.salamun

import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.FloatBuffer

import android.opengl.GLES20.GL_COLOR_BUFFER_BIT
import android.opengl.GLES20.GL_DEPTH_BUFFER_BIT
import android.opengl.GLES20.GL_FLOAT
import android.opengl.GLES20.GL_VERTEX_SHADER
import android.opengl.GLES20.GL_FRAGMENT_SHADER
import android.opengl.GLES20.GL_TRIANGLES
import android.opengl.GLES20.GL_LINES
import android.opengl.GLES20.GL_LINE_LOOP
import android.opengl.GLES20.GL_POINTS
import android.opengl.GLES20.GL_DEPTH_TEST
import android.opengl.GLES20.glEnable
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
import android.opengl.GLES20.glUniformMatrix4fv
import android.opengl.Matrix
import android.opengl.GLSurfaceView.Renderer
import android.content.Context
import android.os.SystemClock
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

class OpenGLRenderer(private val context: Context) : Renderer {
	companion object {
		private val TIME: Long = 10000
	}
	private var programId = 0
	private lateinit var vertexData: FloatBuffer
	private var uColorLocation = 0
	private var aPositionLocation = 0
	private var uMatrixLocation = 0
	private val mProjectionMatrix = FloatArray(16)
	private val mViewMatrix = FloatArray(16)
	private val mMatrix = FloatArray(16)

	var centerX = 0f
	var centerY = 0f
	var centerZ = 0f
	var upX = 0f
	var upY = 0f
	var upZ = 0f

	override fun onSurfaceCreated(arg0: GL10?, arg1: EGLConfig?) {
		glClearColor(0f, 0f, 0f, 1f)
		glEnable(GL_DEPTH_TEST)
		val vertexShaderId = ShaderUtils.createShader(context, GL_VERTEX_SHADER, R.raw.vertex_shader)
		val fragmentShaderId = ShaderUtils.createShader(context, GL_FRAGMENT_SHADER, R.raw.fragment_shader)
		programId = ShaderUtils.createProgram(vertexShaderId, fragmentShaderId)
		glUseProgram(programId)
		createViewMatrix()
		prepareData()
		bindData()
	}
	override fun onSurfaceChanged(arg0: GL10?, width: Int, height: Int) {
		glViewport(0, 0, width, height)
		createProjectionMatrix(width, height)
		bindMatrix()
	}
	private fun prepareData() {
		val s = 0.4f
		val d = 0.9f
		val l = 3f
		val vertices = floatArrayOf(
			-2*s, -s, d,
			2*s, -s, d,
			0f, s, d,

			-2f*s, -s, -d,
			2f*s, -s, -d,
			0f, s, -d,

			d, -s, -2f*s,
			d, -s, 2f*s,
			d, s, 0f,

			-d, -s, -2f*s,
			-d, -s, 2f*s,
			-d, s, 0f,
			// X
			-l, 0f, 0f,
			l, 0f, 0f,
			// Y
			0f, -l, 0f,
			0f, l, 0f,
			// Z
			0f, 0f, -l,
			0f, 0f, l,
			// Up Vector
			centerX, centerY, centerZ,
			centerX + upX, centerY + upY, centerZ + upZ
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
		glVertexAttribPointer(aPositionLocation, 3, GL_FLOAT, false, 0, vertexData)
		glEnableVertexAttribArray(aPositionLocation)

		uColorLocation = glGetUniformLocation(programId, "u_Color")
		uMatrixLocation = glGetUniformLocation(programId, "u_Matrix")
	}
	private fun createProjectionMatrix(width: Int, height: Int) {
		var ratio = 1.0f
		var left = -1.0f
		var right = 1.0f
		var bottom = -1.0f
		var top = 1.0f
		val near = 2.0f
		val far = 8.0f
		if (width > height) {
			ratio = width.toFloat() / height
			left *= ratio
			right *= ratio
		} else {
			ratio = height.toFloat() / width
			bottom *= ratio
			top *= ratio
		}
		Matrix.frustumM(mProjectionMatrix, 0, left, right, bottom, top, near, far)
	}
	private fun createViewMatrix() {
		val time = (SystemClock.uptimeMillis() % TIME).toFloat() / TIME
		val angle = time * 2f * PI.toFloat()
		// Camera Position Point
		val eyeX = cos(angle) * 4f
		val eyeY = 1f
		val eyeZ = sin(angle) * 4f
		//val eyeX = 2f
		//val eyeY = 3f
		//val eyeZ = 4f
		// Camera Direction Point
		centerX = 0f
		centerY = 0f
		centerZ = 0f
		// Up Vector
		upX = 0f
		upY = 1f
		upZ = 0f
		Matrix.setLookAtM(mViewMatrix, 0, eyeX, eyeY, eyeZ, centerX, centerY, centerZ, upX, upY, upZ)
	}
	private fun bindMatrix() {
		Matrix.multiplyMM(mMatrix, 0, mProjectionMatrix, 0, mViewMatrix, 0)
		glUniformMatrix4fv(uMatrixLocation, 1, false, mMatrix, 0)
	}
	override fun onDrawFrame(arg0: GL10?) {
		createViewMatrix()
		bindMatrix()
		glClear(GL_COLOR_BUFFER_BIT or GL_DEPTH_BUFFER_BIT)
		glUniform4f(uColorLocation, 0.0f, 1.0f, 0.0f, 1.0f)
		glDrawArrays(GL_TRIANGLES, 0, 3)

		glUniform4f(uColorLocation, 0.0f, 0.0f, 1.0f, 1.0f)
		glDrawArrays(GL_TRIANGLES, 3, 3)

		glUniform4f(uColorLocation, 1.0f, 0.0f, 0.0f, 1.0f)
		glDrawArrays(GL_TRIANGLES, 6, 3)

		glUniform4f(uColorLocation, 1.0f, 1.0f, 0.0f, 1.0f)
		glDrawArrays(GL_TRIANGLES, 9, 3)

		glLineWidth(3f)

		glUniform4f(uColorLocation, 0.0f, 1.0f, 1.0f, 1.0f)
		glDrawArrays(GL_LINES, 12, 2)

		glUniform4f(uColorLocation, 0.0f, 0.0f, 1.0f, 1.0f)
		glDrawArrays(GL_LINES, 14, 2)

		glUniform4f(uColorLocation, 0.0f, 0.5f, 0.0f, 1.0f)
		glDrawArrays(GL_LINES, 16, 2)

		glLineWidth(5f)
		glUniform4f(uColorLocation, 1.0f, 1.0f, 1.0f, 1.0f)
		glDrawArrays(GL_LINES, 18, 2)
	}
}
