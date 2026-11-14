/* \uFDFD
 *                           \u0648\u0671\u0630\u0643\u0631 \u0631\u0628\u0651\u0643
 *              \u0643\u062A\u0628\u0647 \u0639\u0628\u062F \u0671\u0644\u0643\u0631\u064A\u0645
 *  \u06F1\u06F4\u06F4\u06F8 \u062C\u0645\u0627\u062F\u0649 \u0627\u0644\u0622\u062E\u0631\u0629 \u06F4
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
import android.graphics.Rect
import android.graphics.RectF

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
		private var rect: Rect
		private var rectf: RectF
		private var points: FloatArray
		private var points1: FloatArray
		private var sb: StringBuilder
		init {
			p = Paint()
//			rect = Rect()
			rectf = RectF(700f, 100f, 800f, 150f)
			points = floatArrayOf(100f,50f,150f,100f,150f,200f,50f,200f,50f,100f)
			points1 = floatArrayOf(300f,200f,600f,200f,300f,300f,600f,300f,400f,100f,400f,400f,500f,100f,500f,400f)

			rect = Rect(100, 200, 200, 300)
			sb = StringBuilder()
		}
		override fun onDraw(canvas: Canvas) {
			canvas.drawARGB(80, 102, 204, 255)
//			p.setColor(Color.RED)
//			p.setStrokeWidth(10f)
/*
			with(canvas) {
				drawPoint(50f, 50f, p)
				drawLine(100f, 100f, 500f, 50f, p)
				drawCircle(100f, 200f, 50f, p)
				drawRect(200f, 150f, 400f, 200f, p)
			}
			rect.set(250, 300, 350, 500)	// Top Left (250, 300), Bottom Right (350, 500)
			canvas.drawRect(rect, p)
*/
/*			with(canvas) {
				drawPoints(points, p)
				drawLines(points1, p)
				p.setColor(Color.GREEN)
				drawRoundRect(rectf, 20f, 20f, p)
				rectf.offset(0f, 150f)
				drawOval(rectf, p)
				rectf.offsetTo(900f, 100f)
				rectf.inset(0f, -25f)
				drawArc(rectf, 90f, 270f, true, p)
				rectf.offset(0f, 150f)
				drawArc(rectf, 90f, 270f, false, p)
				p.setStrokeWidth(3f)
				drawLine(150f, 450f, 150f, 600f, p)
				p.setColor(Color.BLUE)
				p.setTextSize(30f)
				drawText("text left", 150f, 500f, p)
				p.setTextAlign(Paint.Align.CENTER)
				drawText("text center", 150f, 525f, p)
				p.setTextAlign(Paint.Align.RIGHT)
				drawText("text right", 150f, 550f, p)
			}
*/
			p.setColor(Color.BLUE)
			p.setStrokeWidth(10f)
			p.setTextSize(30f)
			sb.clear()
			sb.append("width = ${canvas.width}")
				.append(", height = ${canvas.height}")
			canvas.drawText(sb.toString(), 100f, 100f, p)

			p.setStyle(Paint.Style.FILL)
			canvas.drawRect(rect, p)

			p.setStyle(Paint.Style.STROKE)
			rect.offset(150, 0)
			canvas.drawRect(rect, p)

			p.setStyle(Paint.Style.FILL_AND_STROKE)
			rect.offset(150, 0)
			canvas.drawRect(rect, p)
		}
	}
}
