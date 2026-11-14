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
import android.graphics.Path
import android.graphics.RectF
import android.graphics.PointF

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
		private var rectf: RectF
		private var path: Path
		private var path1: Path

		init {
			p = Paint().apply {
				setStrokeWidth(3f)
				setStyle(Paint.Style.STROKE)
			}
			rectf = RectF(350f, 100f, 400f, 150f)
			path = Path()
			path1 = Path()
		}
		override fun onDraw(canvas: Canvas) {
			canvas.drawARGB(80, 102, 204, 255)

			path.reset()
			path.moveTo(100f, 100f)
			path.lineTo(150f, 200f)
			path.lineTo(50f, 200f)

			path.moveTo(250f, 100f)
			path.lineTo(300f, 200f)
			path.lineTo(200f, 200f)
			path.close()

			path.addRect(rectf, Path.Direction.CW)
			path.addCircle(450f, 150f, 25f, Path.Direction.CW)
			p.setColor(Color.BLACK)
			canvas.drawPath(path, p)

			path1.reset()
			path1.moveTo(50f, 50f)
			path1.lineTo(500f, 250f)
			path1.moveTo(500f, 50f)
			path1.lineTo(50f, 250f)
			p.setColor(Color.GREEN)
			canvas.drawPath(path1, p)

			path.addPath(path1)
			path.offset(500f, 100f)
			p.setColor(Color.BLUE)
			canvas.drawPath(path, p)
		}
	}*/
/*	inner class DrawView(context: Context) : View(context) {
		private var p: Paint
		private var path: Path
		private var point1: PointF
		private var point21: PointF
		private var point22: PointF

		init {
			p = Paint(Paint.ANTI_ALIAS_FLAG).apply {
				setStrokeWidth(3f)
			}
			path = Path()
			point1 = PointF(200f, 300f)
			point21 = PointF(500f, 600f)
			point22 = PointF(900f, 200f)
		}
		override fun onDraw(canvas: Canvas) {
			canvas.drawARGB(80, 102, 204, 255)
			p.setColor(Color.BLACK)
			canvas.drawLine(100f, 100f, 600f, 100f, p)

			p.setStyle(Paint.Style.FILL)
			p.setColor(Color.GREEN)
			canvas.drawCircle(point1.x, point1.y, 10f, p)

			path.reset()
			path.moveTo(100f, 100f)
			path.quadTo(point1.x, point1.y, 600f, 100f)
			p.setStyle(Paint.Style.STROKE)
			canvas.drawPath(path, p)

			p.setColor(Color.BLACK)
			canvas.drawLine(400f, 400f, 1100f, 400f, p)

			p.setStyle(Paint.Style.FILL)
			p.setColor(Color.BLUE)
			canvas.drawCircle(point21.x, point21.y, 10f, p)
			canvas.drawCircle(point22.x, point22.y, 10f, p)

			path.reset()
			path.moveTo(400f, 400f)
			path.cubicTo(point21.x, point21.y, point22.x, point22.y, 1100f, 400f)
			p.setStyle(Paint.Style.STROKE)
			canvas.drawPath(path, p)
		}
	}*/
	inner class DrawView(context: Context) : View(context) {
		private var p: Paint
		private var path: Path
		private var text: String

		init {
			p = Paint(Paint.ANTI_ALIAS_FLAG).apply {
				setStrokeWidth(1f)
				setTextSize(20f)
			}
			path = Path()
			text = "Draw the text, with origin at (x, y) using the specified paint"
		}
		override fun onDraw(canvas: Canvas) {
			canvas.drawARGB(80, 102, 204, 255)

			path.reset()
			path.addCircle(200f, 200f, 100f, Path.Direction.CW)
			p.setColor(Color.BLACK)
			canvas.drawTextOnPath(text, path, 0f, 0f, p)

			path.reset()
			path.addCircle(500f, 200f, 100f, Path.Direction.CCW)
			p.setStyle(Paint.Style.FILL)
			p.setColor(Color.BLUE)
			canvas.drawTextOnPath(text, path, 0f, 0f, p)
			p.setStyle(Paint.Style.STROKE)
			canvas.drawPath(path, p)

			path.offset(-300f, 250f)
			p.setStyle(Paint.Style.FILL)
			p.setColor(Color.GREEN)
			canvas.drawTextOnPath(text, path, 100f, 0f, p)
			p.setStyle(Paint.Style.STROKE)
			canvas.drawPath(path, p)

			path.offset(300f, 0f)
			p.setStyle(Paint.Style.FILL)
			p.setColor(Color.RED)
			canvas.drawTextOnPath(text, path, 0f, 30f, p)
			p.setStyle(Paint.Style.STROKE)
			canvas.drawPath(path, p)
		}
	}
}
