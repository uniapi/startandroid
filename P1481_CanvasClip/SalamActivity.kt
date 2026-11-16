/* \uFDFD
 *                           \u0648\u0671\u0630\u0643\u0631 \u0631\u0628\u0651\u0643
 *              \u0643\u062A\u0628\u0647 \u0639\u0628\u062F \u0671\u0644\u0643\u0631\u064A\u0645
 *  \u06F1\u06F4\u06F4\u06F8 \u062C\u0645\u0627\u062F\u0649 \u0627\u0644\u0622\u062E\u0631\u0629 \u06F6
 */
package localhost.idroid.salamun

import android.util.Log
import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.View
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.graphics.Rect
import android.graphics.Region

class SalamActivity : Activity() {
	companion object {
		private const val TAG = "localhost.idroid.salamun"
	}
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(DrawView(this))
	}
	inner class DrawView(context: Context) : View(context) {
		private var p: Paint
		private var rect1: Rect
		private var rect2: Rect
		private val op = Region.Op.UNION

		init {
			p = Paint().apply {
				setStrokeWidth(3f)
				setStyle(Paint.Style.STROKE)
			}
			rect1 = Rect(180, 220, 340, 380)
			rect2 = Rect(280, 320, 440, 480)
		}
		override fun onDraw(canvas: Canvas) {
			canvas.drawARGB(80, 102, 204, 255)

			p.setColor(Color.BLUE)
			drawGrid(canvas)
			p.setColor(Color.RED)
			canvas.drawRect(rect1, p)
			canvas.drawRect(rect2, p)

			canvas.translate(600f, 0f)
			canvas.clipRect(rect1)
			canvas.clipRect(rect2, op)

			p.setColor(Color.BLUE)
			drawGrid(canvas)
		}
		private fun drawGrid(canvas: Canvas) {
			for (i in 25 until 400 step 25) {
				canvas.drawLine(100f + i, 100f, 100f + i, 600f, p)
			}
			for (i in 25 until 400 step 25) {
				canvas.drawLine(100f, 100f + i, 500f, 100f + i, p)
			}
		}
	}
}
