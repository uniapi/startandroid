/* \uFDFD
 *                           \u0648\u0671\u0630\u0643\u0631 \u0631\u0628\u0651\u0643
 *              \u0643\u062A\u0628\u0647 \u0639\u0628\u062F \u0671\u0644\u0643\u0631\u064A\u0645
 *  \u06F1\u06F4\u06F4\u06F8 \u062C\u0645\u0627\u062F\u0649 \u0627\u0644\u0622\u062E\u0631\u0629 \u06F1\u06F4
 */
package localhost.idroid.salamun

import android.util.Log
import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.View
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.BitmapShader
import android.graphics.Shader
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Matrix
import android.graphics.LinearGradient

class SalamActivity : Activity() {
	companion object {
		private const val TAG = "localhost.idroid.salamun"
	}
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(DrawView(this))
	}
	inner class DrawView(context: Context) : View(context) {
		private var paint: Paint
		private var bitmap: Bitmap? = null
		private var scaledBitmap: Bitmap? = null

		init {
			val shader = createShader()
			paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
				setShader(shader)
			}
		}
		override fun onDraw(canvas: Canvas) {
			canvas.drawARGB(80, 102, 204, 255)
			canvas.drawRect(100f, 100f, 400f, 300f, paint)
			canvas.drawCircle(300f, 400f, 100f, paint)
		}
/*		private fun createShader(): Shader {
			bitmap = BitmapFactory.decodeResource(resources, android.R.drawable.ic_menu_gallery)
			val shader = BitmapShader(bitmap, Shader.TileMode.REPEAT, Shader.TileMode.REPEAT)
			val matrix = Matrix().apply {
				postScale(2f, 1.5f)
				postRotate(45f)
			}
			return shader.apply { setLocalMatrix(matrix) }
		}
*/
/*		private fun createShader(): Shader {
			val shader = LinearGradient(0f, 0f, 100f, 20f, Color.RED, Color.GREEN, Shader.TileMode.MIRROR)
			return shader
		}
*/
		private fun createShader(): Shader {
			val shader = LinearGradient(120f, 0f, 380f, 20f,
				intArrayOf(Color.RED, Color.BLUE, Color.GREEN),
				floatArrayOf(0f, 0.7f, 1f),
				Shader.TileMode.REPEAT
			)
			return shader
		}
	}
}
