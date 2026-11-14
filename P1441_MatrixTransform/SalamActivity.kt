/* \uFDFD
 *                           \u0648\u0671\u0630\u0643\u0631 \u0631\u0628\u0651\u0643
 *              \u0643\u062A\u0628\u0647 \u0639\u0628\u062F \u0671\u0644\u0643\u0631\u064A\u0645
 *  \u06F1\u06F4\u06F4\u06F8 \u062C\u0645\u0627\u062F\u0649 \u0627\u0644\u0622\u062E\u0631\u0629 \u06F5
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
		private var matrix: Matrix

		init {
			p = Paint().apply {
				setStrokeWidth(3f)
				setStyle(Paint.Style.STROKE)
			}
			path = Path()
			matrix = Matrix()
		}
		override fun onDraw(canvas: Canvas) {
			canvas.drawARGB(80, 102, 204, 255)
			path.reset()
			path.addRect(300f, 150f, 450f, 200f, Path.Direction.CW)
			path.addRect(350f, 100f, 400f, 250f, Path.Direction.CW)
			p.setColor(Color.GREEN)
			canvas.drawPath(path, p)

			matrix.reset()
			matrix.setTranslate(300f, 200f)
			path.transform(matrix)
			p.setColor(Color.BLUE)
			canvas.drawPath(path, p)
		}
	}
*/
/*	inner class DrawView(context: Context) : View(context) {
		private var p: Paint
		private var path: Path
		private var matrix: Matrix

		init {
			p = Paint().apply {
				setStrokeWidth(3f)
				setStyle(Paint.Style.STROKE)
			}
			path = Path()
			matrix = Matrix()
		}
		override fun onDraw(canvas: Canvas) {
			canvas.drawARGB(80, 102, 204, 255)

			path.reset()
			path.addRect(300f, 150f, 450f, 200f, Path.Direction.CW)
			path.addRect(350f, 100f, 400f, 250f, Path.Direction.CW)

			path.addCircle(375f, 125f, 5f, Path.Direction.CW)

			p.setColor(Color.GREEN)
			canvas.drawPath(path, p)

//			matrix.reset()
//			matrix.setScale(2f, 2.5f, 375f, 100f)
//			path.transform(matrix)
//			p.setColor(Color.BLUE)
//			canvas.drawPath(path, p)
//			p.setColor(Color.BLACK)
//			canvas.drawCircle(375f, 100f, 5f, p)

			matrix.reset()
			matrix.setRotate(120f, 600f, 400f)
			path.transform(matrix)
			p.setColor(Color.BLUE)
			canvas.drawPath(path, p)
			p.setColor(Color.BLACK)
			canvas.drawCircle(600f, 400f, 5f, p)
		}
	}
*/
/*	inner class DrawView(context: Context) : View(context) {
		private var p: Paint
		private var path: Path
		private var pathDst: Path
		private var matrix: Matrix

		init {
			p = Paint().apply {
				setStrokeWidth(3f)
				setStyle(Paint.Style.STROKE)
			}
			path = Path()
			pathDst = Path()
			matrix = Matrix()
		}
		override fun onDraw(canvas: Canvas) {
			canvas.drawARGB(80, 102, 204, 255)

			p.setColor(Color.BLACK)
			canvas.drawCircle(400f, 200f, 10f, p)
			path.reset()
			path.addRect(300f, 100f, 500f, 300f, Path.Direction.CW)
			canvas.drawPath(path, p)

			matrix.reset()
			matrix.setRotate(45f, 400f, 200f)
			matrix.postTranslate(500f, 0f)
			path.transform(matrix, pathDst)
			p.setColor(Color.GREEN)
			canvas.drawPath(pathDst, p)

			matrix.reset()
			matrix.setRotate(45f, 400f, 200f)
			matrix.preTranslate(500f, 0f)
			path.transform(matrix, pathDst)
			p.setColor(Color.RED)
			canvas.drawPath(pathDst, p)
		}
	}
*/
/*	inner class DrawView(context: Context) : View(context) {
		private var p: Paint
		private var path: Path
		private var pathDst: Path
		private var matrix: Matrix

		init {
			p = Paint().apply {
				setStrokeWidth(3f)
				setStyle(Paint.Style.STROKE)
			}
			path = Path().apply {
				addRect(100f, 100f, 200f, 200f, Path.Direction.CW)
			}
			pathDst = Path()
			matrix = Matrix()
		}
		override fun onDraw(canvas: Canvas) {
			canvas.drawARGB(80, 102, 204, 255)

			p.setColor(Color.BLACK)
			canvas.drawPath(path, p)

			p.setColor(Color.GREEN)
			matrix.reset()
			matrix.setTranslate(200f, 0f)
			matrix.postSkew(0.0f, 0.5f, 300f, 100f)
			path.transform(matrix, pathDst)
			canvas.drawPath(pathDst, p)
			canvas.drawCircle(300f, 100f, 5f, p)

			matrix.reset()
			matrix.setTranslate(400f, 0f)
			matrix.postSkew(0.0f, 0.5f, 600f, 100f)
			path.transform(matrix, pathDst)
			canvas.drawPath(pathDst, p)
			canvas.drawCircle(600f, 100f, 5f, p)

			p.setColor(Color.BLUE)
			matrix.reset()
			matrix.setTranslate(0f, 150f)
			matrix.postSkew(0.5f, 0.0f, 100f, 250f)
			path.transform(matrix, pathDst)
			canvas.drawPath(pathDst, p)
			canvas.drawCircle(100f, 250f, 5f, p)

			matrix.reset()
			matrix.setTranslate(0f, 300f)
			matrix.postSkew(0.5f, 0.0f, 100f, 500f)
			path.transform(matrix, pathDst)
			canvas.drawPath(pathDst, p)
			canvas.drawCircle(100f, 500f, 5f, p)
		}
	}
*/
	inner class DrawView(context: Context) : View(context) {
		private var p: Paint
		private var matrix: Matrix
		private var rectf: RectF
		private var rectfDst: RectF
		private var path: Path

		init {
			p = Paint().apply {
				setStrokeWidth(3f)
				setStyle(Paint.Style.STROKE)
			}
			rectf = RectF(100f, 100f, 200f, 200f)
			rectfDst = RectF()
			matrix = Matrix()
			path = Path()
		}
		override fun onDraw(canvas: Canvas) {
			canvas.drawARGB(80, 102, 204, 255)

			path.reset()
			path.addRect(rectf, Path.Direction.CW)
			p.setColor(Color.BLACK)
			canvas.drawPath(path, p)

			matrix.setRotate(45f, 150f, 150f)
			matrix.postScale(1.2f, 0.8f, 150f, 150f)
			matrix.postTranslate(200f, 0f)
			path.transform(matrix)
			p.setColor(Color.GREEN)
			canvas.drawPath(path, p)

			matrix.mapRect(rectfDst, rectf)
			p.setColor(Color.BLUE)
			canvas.drawRect(rectfDst, p)
		}
	}
}
