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
import android.graphics.CornerPathEffect
import android.graphics.DiscretePathEffect
import android.graphics.DashPathEffect
import android.graphics.PathDashPathEffect
import android.graphics.ComposePathEffect
import android.graphics.SumPathEffect
import android.graphics.Paint
import android.graphics.Path

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
		private var path: Path
		private var p1: Paint
		private var p2: Paint
		private var p3: Paint
		private var p4: Paint
		private var p5: Paint
		private var p6: Paint
		private var p7: Paint
		private var p8: Paint

		init {
			path = Path().apply {
				rLineTo(100f, 300f)
				rLineTo(100f, -100f)
				rLineTo(100f, 300f)
			}
			p1 = Paint(Paint.ANTI_ALIAS_FLAG).apply {
				setStyle(Paint.Style.STROKE)
				setStrokeWidth(3f)
			}
			p2 = Paint(p1).apply {
				setColor(Color.GREEN)
				setPathEffect(CornerPathEffect(25f))
			}
			p3 = Paint(p1).apply {
				setColor(Color.BLUE)
				setPathEffect(CornerPathEffect(50f))
			}
			p4 = Paint(p1).apply {
				setColor(Color.GREEN)
				setPathEffect(DiscretePathEffect(10f, 5f))
			}
			p5 = Paint(p1).apply {
				setColor(Color.BLUE)
				setPathEffect(DiscretePathEffect(10f, 15f))
			}
			p6 = Paint(Paint.ANTI_ALIAS_FLAG).apply {
				setStyle(Paint.Style.STROKE)
				setStrokeWidth(7f)
			}
			p7 = Paint(p1).apply {
				setColor(Color.GREEN)
				setPathEffect(DashPathEffect(floatArrayOf(30f, 10f), 0f))
			}
			p8 = Paint(p1).apply {
				setColor(Color.BLUE)
				setPathEffect(DashPathEffect(floatArrayOf(50f, 10f, 5f, 10f), 25f))
			}
		}
		override fun onDraw(canvas: Canvas) {
			with(canvas) {
				drawARGB(80, 102, 204, 255)
				save()

				translate(100f, 100f)
				drawPath(path, p1)
				translate(250f, 0f)
				drawPath(path, p2)
				translate(250f, 0f)
				drawPath(path, p3)
				restore()

				translate(100f, 700f)
				drawPath(path, p1)
				translate(250f, 0f)
				drawPath(path, p4)
				translate(250f, 0f)
				drawPath(path, p5)
				restore()

				translate(100f, 1300f)
				drawPath(path, p6)
				translate(250f, 0f)
				drawPath(path, p7)
				translate(250f, 0f)
				drawPath(path, p8)
			}
		}
	}
*/
/*	inner class DrawView(context: Context) : View(context) {
		private var path: Path
		private var pathStamp: Path
		private var p1: Paint
		private var p2: Paint
		private var p3: Paint
		private var p4: Paint

		init {
			path = Path().apply {
				addRect(-100f, 0f, 100f, 500f, Path.Direction.CW)
			}
			pathStamp = Path().apply {
				lineTo(-10f, -10f)
				lineTo(10f, 0f)
				lineTo(-10f, 10f)
				close()
			}
			p1 = Paint(Paint.ANTI_ALIAS_FLAG).apply {
				setStyle(Paint.Style.STROKE)
				setStrokeWidth(20f)
			}
			p2 = Paint(p1).apply {
				setColor(Color.GREEN)
				setPathEffect(PathDashPathEffect(pathStamp, 20f, 0f, PathDashPathEffect.Style.MORPH))
			}
			p3 = Paint(p1).apply {
				setColor(Color.BLUE)
				setPathEffect(PathDashPathEffect(pathStamp, 20f, 0f, PathDashPathEffect.Style.ROTATE))
			}
			p4 = Paint(p1).apply {
				setColor(Color.RED)
				setPathEffect(PathDashPathEffect(pathStamp, 20f, 10f, PathDashPathEffect.Style.TRANSLATE))
			}
		}
		override fun onDraw(canvas: Canvas) {
			with(canvas) {
				drawARGB(80, 102, 204, 255)

				translate(120f, 100f)
				drawPath(path, p1)
				translate(250f, 0f)
				drawPath(path, p2)
				translate(250f, 0f)
				drawPath(path, p3)
				translate(250f, 0f)
				drawPath(path, p4)
			}
		}
	}
*/
	inner class DrawView(context: Context) : View(context) {
		private var path: Path
		private var p1: Paint
		private var p2: Paint
		private var p3: Paint
		private var p4: Paint
		private var p5: Paint
		private var p6: Paint
		private var p7: Paint
		private var p8: Paint
		private var p9: Paint
		private var p10: Paint

		init {
			path = Path().apply {
				addRect(-100f, 0f, 100f, 500f, Path.Direction.CW)
			}
			val pe1 = CornerPathEffect(100f)
			val pe2 = DashPathEffect(floatArrayOf(20f, 5f), 0f)
			val pe3 = ComposePathEffect(pe2, pe1)
			val pe4 = SumPathEffect(pe1, pe2)
			val pe5 = DiscretePathEffect(15f, 10f)
			val pe6 = ComposePathEffect(pe1, pe5)
			val pe7 = ComposePathEffect(pe5, pe1)

			p1 = Paint(Paint.ANTI_ALIAS_FLAG).apply {
				setStyle(Paint.Style.STROKE)
				setStrokeWidth(3f)
			}
			p2 = Paint(p1).apply {
				setColor(Color.GREEN)
				setPathEffect(pe1)
			}
			p3 = Paint(p1).apply {
				setColor(Color.BLUE)
				setPathEffect(pe2)
			}
			p4 = Paint(p1).apply {
				setColor(Color.RED)
				setPathEffect(pe3)
			}
			p5 = Paint(p1).apply {
				setColor(Color.YELLOW)
				setPathEffect(pe4)
			}

			p6 = Paint(Paint.ANTI_ALIAS_FLAG).apply {
				setStyle(Paint.Style.STROKE)
				setStrokeWidth(7f)
			}
			p7 = Paint(p6).apply {
				setColor(Color.GREEN)
				setPathEffect(pe1)
			}
			p8 = Paint(p6).apply {
				setColor(Color.BLUE)
				setPathEffect(pe5)
			}
			p9 = Paint(p6).apply {
				setColor(Color.RED)
				setPathEffect(pe6)
			}
			p10 = Paint(p6).apply {
				setColor(Color.YELLOW)
				setPathEffect(pe7)
			}
		}
		override fun onDraw(canvas: Canvas) {
			with(canvas) {
				drawARGB(80, 102, 204, 255)
				save()

				translate(120f, 100f)
				drawPath(path, p1)
				translate(250f, 0f)
				drawPath(path, p2)
				translate(250f, 0f)
				drawPath(path, p3)
				translate(250f, 0f)
				drawPath(path, p4)
				translate(250f, 0f)
				drawPath(path, p5)
				restore()

				translate(120f, 700f)
				drawPath(path, p6)
				translate(250f, 0f)
				drawPath(path, p7)
				translate(250f, 0f)
				drawPath(path, p8)
				translate(250f, 0f)
				drawPath(path, p9)
				translate(250f, 0f)
				drawPath(path, p10)
			}
		}
	}
}
