/* \uFDFD
 *                           \u0648\u0671\u0630\u0643\u0631 \u0631\u0628\u0651\u0643
 *              \u0643\u062A\u0628\u0647 \u0639\u0628\u062F \u0671\u0644\u0643\u0631\u064A\u0645
 *  \u06F1\u06F4\u06F4\u06F8 \u062C\u0645\u0627\u062F\u0649 \u0627\u0644\u0622\u062E\u0631\u0629 \u06F1\u06F5
 */
package localhost.idroid.salamun

import android.opengl.GLES20.GL_COMPILE_STATUS
import android.opengl.GLES20.GL_LINK_STATUS
import android.opengl.GLES20.glAttachShader
import android.opengl.GLES20.glCompileShader
import android.opengl.GLES20.glCreateProgram
import android.opengl.GLES20.glCreateShader
import android.opengl.GLES20.glDeleteProgram
import android.opengl.GLES20.glDeleteShader
import android.opengl.GLES20.glGetProgramiv
import android.opengl.GLES20.glGetShaderiv
import android.opengl.GLES20.glLinkProgram
import android.opengl.GLES20.glShaderSource
import android.content.Context

object ShaderUtils {
	@JvmStatic
	fun createProgram(vertexShaderId: Int, fragmentShaderId: Int): Int {
		val programId = glCreateProgram()
		if (programId == 0) {
			return 0
		}
		glAttachShader(programId, vertexShaderId)
		glAttachShader(programId, fragmentShaderId)
		glLinkProgram(programId)
		val linkStatus = IntArray(1)
		glGetProgramiv(programId, GL_LINK_STATUS, linkStatus, 0)
		if (linkStatus[0] == 0) {
			glDeleteProgram(programId)
			return 0
		}
		return programId
	}
	@JvmStatic
	fun createShader(context: Context, type: Int, shaderRawId: Int): Int {
		val shaderText = FileUtils.readTextFromRaw(context, shaderRawId)
		return ShaderUtils.createShader(type, shaderText)
	}
	@JvmStatic
	fun createShader(type: Int, shaderText: String): Int {
		val shaderId = glCreateShader(type)
		if (shaderId == 0) return 0
		glShaderSource(shaderId, shaderText)
		glCompileShader(shaderId)
		val compileStatus = IntArray(1)
		glGetShaderiv(shaderId, GL_COMPILE_STATUS, compileStatus, 0)
		if (compileStatus[0] == 0) {
			glDeleteShader(shaderId)
			return 0
		}
		return shaderId
	}
}
