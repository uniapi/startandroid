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
import android.graphics.Matrix
import android.graphics.RectF

class SalamActivity : Activity() {
	companion object {
		private const val TAG = "localhost.idroid.salamun"
	}
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(DrawView(this))
	}
/*	inner class DrawView(context: Context) : View(context) {
		private var p: Paint
		private var path: Path
		private var rectf: RectF
		private var matrix: Matrix

		init {
			p = Paint().apply {
				setStrokeWidth(3f)
				setStyle(Paint.Style.STROKE)
			}
			rectf = RectF(100f, 100f, 200f, 200f)
			path = Path()
			matrix = Matrix()
		}
		override fun onDraw(canvas: Canvas) {
			canvas.drawARGB(80, 102, 204, 255)

//			path.reset()
//			path.addRect(rectf, Path.Direction.CW)
//			p.setColor(Color.GREEN)
//			canvas.drawPath(path, p)

//			matrix.reset()
//			matrix.preRotate(30f)
//			matrix.preTranslate(500f, 0f)
//			path.transform(matrix)
//			p.setColor(Color.BLUE)
//			canvas.drawPath(path, p)

			p.setColor(Color.BLACK)
			canvas.drawRect(rectf, p)
			canvas.rotate(30f)
			canvas.translate(500f, 0f)
			p.setColor(Color.GREEN)
			canvas.drawRect(rectf, p)
		}
	}
*/
	inner class DrawView(context: Context) : View(context) {
		private var p: Paint
		private var rectf1: RectF
		private var rectf2: RectF

		init {
			p = Paint().apply {
				setStrokeWidth(3f)
				setStyle(Paint.Style.STROKE)
			}
			rectf1 = RectF(50f, 50f, 100f, 100f)
			rectf2 = RectF(50f, 150f, 100f, 200f)
		}
		override fun onDraw(canvas: Canvas) {
			canvas.drawARGB(80, 102, 204, 255)
			val initSave = canvas.save()

			p.setColor(Color.GREEN)
			canvas.drawRect(rectf1, p)

			with(canvas) {
				translate(100f, 0f)
				drawRect(rectf1, p)
				translate(100f, 0f)
				drawRect(rectf1, p)
//				translate(100f, 0f)
//				drawRect(rectf1, p)
			}
			canvas.save()

			p.setColor(Color.YELLOW)
			with(canvas) {
				translate(100f, 0f)
				drawRect(rectf1, p)
				translate(100f, 0f)
				drawRect(rectf1, p)
			}
			val needSave = canvas.save()

			p.setColor(Color.RED)
			with(canvas) {
				translate(100f, 0f)
				drawRect(rectf1, p)
				translate(100f, 0f)
				drawRect(rectf1, p)
			}
//			canvas.restore()
			canvas.save()

			p.setColor(Color.BLUE)
			canvas.translate(100f, 0f)
			canvas.drawRect(rectf1, p)
			canvas.translate(100f, 0f)
			canvas.drawRect(rectf1, p)
//			canvas.restore()
			canvas.restoreToCount(needSave)

			p.setColor(Color.BLACK)
			canvas.drawRect(rectf2, p)
//			canvas.restore()
			canvas.restoreToCount(initSave)

			p.setColor(Color.MAGENTA)
			canvas.drawRect(rectf2, p)
		}
	}
}
