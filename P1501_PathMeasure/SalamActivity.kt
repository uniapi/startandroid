/* \uFDFD
 *                           \u0648\u0671\u0630\u0643\u0631 \u0631\u0628\u0651\u0643
 *              \u0643\u062A\u0628\u0647 \u0639\u0628\u062F \u0671\u0644\u0643\u0631\u064A\u0645
 *  \u06F1\u06F4\u06F4\u06F8 \u062C\u0645\u0627\u062F\u0649 \u0627\u0644\u0622\u062E\u0631\u0629 \u06F7
 */
package localhost.idroid.salamun

import android.util.Log
import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.graphics.PathMeasure
import android.graphics.Matrix
import android.graphics.Rect

class SalamActivity : Activity() {
	companion object {
		private const val TAG = "localhost.idroid.salamun"
	}
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		requestWindowFeature(Window.FEATURE_NO_TITLE)
		window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
		setContentView(DrawView(this))
	}
/*	inner class DrawView(context: Context) : View(context) {
		private var paint: Paint
		private var paintText: Paint
		private var path: Path
		private var pMeasure: PathMeasure
		private var matrix: Matrix
		private var rect: Rect
		private var pos: FloatArray
		private var tan: FloatArray
		private var length: Float
		private var distance: Float

		init {
			paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
				setStyle(Paint.Style.STROKE)
				setStrokeWidth(3f)
			}
			paintText = Paint(Paint.ANTI_ALIAS_FLAG).apply {
				setTextSize(30f)
			}
			path = Path().apply {
				moveTo(100f, 300f)
				rLineTo(150f, 100f)
				rLineTo(150f, -100f)
				rQuadTo(150f, 200f, 300f, 0f)
				rLineTo(150f, 100f)
				rLineTo(150f, -100f)
			}
			pMeasure = PathMeasure(path, false)
			length = pMeasure.length
			distance = length / 4
			matrix = Matrix()
			pMeasure.getMatrix(distance, matrix, PathMeasure.POSITION_MATRIX_FLAG + PathMeasure.TANGENT_MATRIX_FLAG)
			pos = FloatArray(2)
			tan = FloatArray(2)
			pMeasure.getPosTan(distance, pos, tan)
			rect = Rect(-20, -10, 20, 10)
		}
		override fun onDraw(canvas: Canvas) {
			canvas.drawARGB(80, 102, 204, 255)

			canvas.drawPath(path, paint)
			canvas.drawText("Distance: $distance of $length", 100f, 100f, paintText)
			Log.d(TAG, "Distance: $distance of $length")
			canvas.drawText("Position: ${pos.contentToString()}. Tangent (cos,sin): ${tan.contentToString()}", 100f, 150f, paintText)
			Log.d(TAG, "Position: ${pos.contentToString()}. Tangent (cos,sin): ${tan.contentToString()}")

			canvas.setMatrix(matrix)
			canvas.drawRect(rect, paint)
		}
	}
*/
/*	inner class DrawView(context: Context) : View(context) {
		private var paint: Paint
		private var path: Path
		private var pMeasure: PathMeasure

		init {
			paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
				setStyle(Paint.Style.STROKE)
				setStrokeWidth(3f)
			}
			path = Path().apply {
				moveTo(100f, 300f)
				rLineTo(150f, 150f)
				rLineTo(150f, -100f)
				rMoveTo(0f, 0f)
				rQuadTo(150f, 200f, 300f, 0f)
				close()
				rMoveTo(0f, 0f)
				rLineTo(150f, 100f)
				rLineTo(150f, -150f)
				close()
			}
			pMeasure = PathMeasure(path, false)
			do {
				Log.d(TAG, "Length: ${pMeasure.length}, isClosed: ${pMeasure.isClosed()}")
			} while (pMeasure.nextContour())
		}
		override fun onDraw(canvas: Canvas) {
			canvas.drawARGB(80, 102, 204, 255)
			canvas.drawPath(path, paint)
		}
	}
*/
	inner class DrawView(context: Context) : View(context) {
		private var paint: Paint
		private var path: Path
		private var path1: Path
		private var pMeasure: PathMeasure

		init {
			paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
				setStyle(Paint.Style.STROKE)
				setStrokeWidth(3f)
			}
			path = Path().apply {
				moveTo(100f, 300f)
				rLineTo(150f, 150f)
				rLineTo(150f, -100f)
				rQuadTo(150f, 200f, 300f, 0f)
				rLineTo(150f, 100f)
				rLineTo(150f, -150f)
			}
			pMeasure = PathMeasure(path, false)
			path1 = Path()
			pMeasure.getSegment(150f, 850f, path1, true)
		}
		override fun onDraw(canvas: Canvas) {
			canvas.drawARGB(80, 102, 204, 255)
			canvas.drawPath(path1, paint)
		}
	}
}
