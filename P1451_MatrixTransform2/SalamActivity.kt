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
		private var pathDst: Path
		private var rectfBounds: RectF
		private var rectfDst: RectF
		private var matrix: Matrix

		init {
			p = Paint().apply {
				setStrokeWidth(3f)
				setStyle(Paint.Style.STROKE)
			}
			rectfDst = RectF()
			rectfBounds = RectF()

			path = Path().apply {
				addCircle(200f, 100f, 50f, Path.Direction.CW)
				addCircle(200f, 225f, 75f, Path.Direction.CW)
				addCircle(200f, 400f, 100f, Path.Direction.CW)
			}
			pathDst = Path()
			matrix = Matrix()
		}
		override fun onDraw(canvas: Canvas) {
			canvas.drawARGB(80, 102, 204, 255)
			rectfDst.set(500f, 50f, 800f, 150f)

			p.setColor(Color.BLUE)
			canvas.drawPath(path, p)
			path.computeBounds(rectfBounds, true)
			p.setColor(Color.GREEN)
			canvas.drawRect(rectfBounds, p)

			p.setColor(Color.BLACK)
			canvas.drawRect(rectfDst, p)
			matrix.reset()
			matrix.setRectToRect(rectfBounds, rectfDst, Matrix.ScaleToFit.START)
			path.transform(matrix, pathDst)
			p.setColor(Color.BLUE)
			canvas.drawPath(pathDst, p)

			rectfDst.offset(0f, 150f)
			p.setColor(Color.BLACK)
			canvas.drawRect(rectfDst, p)
			matrix.reset()
			matrix.setRectToRect(rectfBounds, rectfDst, Matrix.ScaleToFit.CENTER)
			path.transform(matrix, pathDst)
			p.setColor(Color.BLUE)
			canvas.drawPath(pathDst, p)

			rectfDst.offset(0f, 150f)
			p.setColor(Color.BLACK)
			canvas.drawRect(rectfDst, p)
			matrix.reset()
			matrix.setRectToRect(rectfBounds, rectfDst, Matrix.ScaleToFit.END)
			path.transform(matrix, pathDst)
			p.setColor(Color.BLUE)
			canvas.drawPath(pathDst, p)

			rectfDst.offset(0f, 150f)
			p.setColor(Color.BLACK)
			canvas.drawRect(rectfDst, p)
			matrix.reset()
			matrix.setRectToRect(rectfBounds, rectfDst, Matrix.ScaleToFit.FILL)
			path.transform(matrix, pathDst)
			p.setColor(Color.BLUE)
			canvas.drawPath(pathDst, p)
		}
	}
*/
/*	inner class DrawView(context: Context) : View(context) {
		private var p: Paint
		private var path: Path
		private var pathDst: Path
		private var rectf: RectF
		private var matrix: Matrix
		private var src: FloatArray
		private var dst: FloatArray

		init {
			p = Paint().apply {
				setStrokeWidth(3f)
				setStyle(Paint.Style.STROKE)
			}
			path = Path()
			pathDst = Path()
			matrix = Matrix()
			rectf = RectF(100f, 100f, 200f, 200f)
			src = floatArrayOf(100f, 100f)
			dst = floatArrayOf(150f, 120f)
		}
		override fun onDraw(canvas: Canvas) {
			canvas.drawARGB(80, 102, 204, 255)
			path.reset()
			path.addRect(rectf, Path.Direction.CW)
			p.setColor(Color.GREEN)
			canvas.drawPath(path, p)
			matrix.setPolyToPoly(src, 0, dst, 0, 1)
			path.transform(matrix, pathDst)
			p.setColor(Color.BLUE)
			canvas.drawPath(pathDst, p)
		}
	}
*/
/*	inner class DrawView(context: Context) : View(context) {
		private var p: Paint
		private var pBlack: Paint
		private var path: Path
		private var pathDst: Path
		private var rectf: RectF
		private var matrix: Matrix
		private var src: FloatArray
		private var dst: FloatArray
		private var dst2: FloatArray
		private var points = 2
		init {
			p = Paint().apply {
				setStrokeWidth(3f)
				setStyle(Paint.Style.STROKE)
			}
			pBlack = Paint().apply {
				setColor(Color.BLACK)
				setStrokeWidth(3f)
			}
			path = Path()
			pathDst = Path()
			matrix = Matrix()
			rectf = RectF(100f, 100f, 200f, 200f)
			src = floatArrayOf(100f, 100f, 200f, 200f)
			dst = floatArrayOf(50f, 300f, 250f, 500f)
			dst2 = floatArrayOf(400f, 200f, 500f, 200f)
		}
		override fun onDraw(canvas: Canvas) {
			canvas.drawARGB(80, 102, 204, 255)

			path.reset()
			path.addRect(rectf, Path.Direction.CW)
			p.setColor(Color.GREEN)
			canvas.drawPath(path, p)
			canvas.drawLine(src[0], src[1], src[2], src[3], pBlack)

			matrix.setPolyToPoly(src, 0, dst, 0, points)
			path.transform(matrix, pathDst)
			p.setColor(Color.BLUE)
			canvas.drawPath(pathDst, p)
			canvas.drawLine(dst[0], dst[1], dst[2], dst[3], pBlack)

			matrix.setPolyToPoly(src, 0, dst2, 0, points)
			path.transform(matrix, pathDst)
			p.setColor(Color.RED)
			canvas.drawPath(pathDst, p)
			canvas.drawLine(dst2[0], dst2[1], dst2[2], dst2[3], pBlack)
		}
	}
*/
/*	inner class DrawView(context: Context) : View(context) {
		private var p: Paint
		private var pBlack: Paint
		private var pGray: Paint
		private var path: Path
		private var pathDst: Path
		private var rectf: RectF
		private var matrix: Matrix
		private var src: FloatArray
		private var dst: FloatArray
		private var dst2: FloatArray
		private var points = 3
		init {
			p = Paint().apply {
				setStrokeWidth(3f)
				setStyle(Paint.Style.STROKE)
			}
			pGray = Paint().apply {
				setColor(Color.GRAY)
				setStrokeWidth(3f)
			}
			pBlack = Paint().apply {
				setColor(Color.BLACK)
				setStrokeWidth(3f)
			}
			path = Path()
			pathDst = Path()
			matrix = Matrix()
			rectf = RectF(100f, 100f, 200f, 200f)
			src = floatArrayOf(100f, 100f, 200f, 200f, 200f, 100f)
			dst = floatArrayOf(50f, 300f, 250f, 500f, 230f, 350f)
			dst2 = floatArrayOf(400f, 200f, 500f, 200f, 440f, 100f)
		}
		override fun onDraw(canvas: Canvas) {
			canvas.drawARGB(80, 102, 204, 255)

			path.reset()
			path.addRect(rectf, Path.Direction.CW)
			p.setColor(Color.GREEN)
			canvas.drawPath(path, p)
			canvas.drawLine(src[0], src[1], src[2], src[3], pBlack)
			canvas.drawLine(src[0], src[1], src[4], src[5], pGray)

			matrix.setPolyToPoly(src, 0, dst, 0, points)
			path.transform(matrix, pathDst)
			p.setColor(Color.BLUE)
			canvas.drawPath(pathDst, p)
			canvas.drawLine(dst[0], dst[1], dst[2], dst[3], pBlack)
			canvas.drawLine(dst[0], dst[1], dst[4], dst[5], pGray)

			matrix.setPolyToPoly(src, 0, dst2, 0, points)
			path.transform(matrix, pathDst)
			p.setColor(Color.RED)
			canvas.drawPath(pathDst, p)
			canvas.drawLine(dst2[0], dst2[1], dst2[2], dst2[3], pBlack)
			canvas.drawLine(dst2[0], dst2[1], dst2[4], dst2[5], pGray)
		}
	}
*/
	inner class DrawView(context: Context) : View(context) {
		private var p: Paint
		private var pBlack: Paint
		private var pGray: Paint
		private var pWhite: Paint
		private var path: Path
		private var pathDst: Path
		private var rectf: RectF
		private var matrix: Matrix
		private var src: FloatArray
		private var dst: FloatArray
		private var dst2: FloatArray
		private var points = 4
		init {
			p = Paint().apply {
				setStrokeWidth(3f)
				setStyle(Paint.Style.STROKE)
			}
			pGray = Paint().apply {
				setColor(Color.GRAY)
				setStrokeWidth(3f)
			}
			pBlack = Paint().apply {
				setColor(Color.BLACK)
				setStrokeWidth(3f)
			}
			pWhite = Paint().apply {
				setColor(Color.WHITE)
				setStrokeWidth(3f)
			}
			path = Path()
			pathDst = Path()
			matrix = Matrix()
			rectf = RectF(100f, 100f, 200f, 200f)
			src = floatArrayOf(100f, 100f, 200f, 200f, 200f, 100f, 100f, 200f)
			dst = floatArrayOf(50f, 300f, 250f, 500f, 230f, 350f, 40f, 550f)
			dst2 = floatArrayOf(400f, 200f, 500f, 200f, 440f, 100f, 440f, 230f)
		}
		override fun onDraw(canvas: Canvas) {
			canvas.drawARGB(80, 102, 204, 255)

			path.reset()
			path.addRect(rectf, Path.Direction.CW)
			p.setColor(Color.GREEN)
			with(canvas) {
				drawPath(path, p)
				drawLine(src[0], src[1], src[2], src[3], pBlack)
				drawLine(src[0], src[1], src[4], src[5], pGray)
				drawLine(src[0], src[1], src[6], src[7], pWhite)
			}

			matrix.setPolyToPoly(src, 0, dst, 0, points)
			path.transform(matrix, pathDst)
			p.setColor(Color.BLUE)
			with(canvas) {
				drawPath(pathDst, p)
				drawLine(dst[0], dst[1], dst[2], dst[3], pBlack)
				drawLine(dst[0], dst[1], dst[4], dst[5], pGray)
				drawLine(dst[0], dst[1], dst[6], dst[7], pWhite)
			}

			matrix.setPolyToPoly(src, 0, dst2, 0, points)
			path.transform(matrix, pathDst)
			p.setColor(Color.RED)
			with(canvas) {
				drawPath(pathDst, p)
				drawLine(dst2[0], dst2[1], dst2[2], dst2[3], pBlack)
				drawLine(dst2[0], dst2[1], dst2[4], dst2[5], pGray)
				drawLine(dst2[0], dst2[1], dst2[6], dst2[7], pWhite)
			}
		}
	}
}
