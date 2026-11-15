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
import android.graphics.RectF
import android.graphics.Region
import android.graphics.RegionIterator

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
		private var rect1: Rect
		private var rect2: Rect
		private var region: Region
		private var path: Path
		private val op = Region.Op.REPLACE // INTERSECT // REVERSE_DIFFERENCE // DIFFERENCE // XOR // UNION

		init {
			p = Paint().apply {
				setStrokeWidth(3f)
			}
			rect1 = Rect(200, 200, 400, 400)
			rect2 = Rect(300, 300, 500, 500)
			region = Region().apply {
				set(rect1)
				op(rect2, op)
			}
			path = region.boundaryPath
		}
		override fun onDraw(canvas: Canvas) {
			canvas.drawARGB(80, 102, 204, 255)

			p.setStyle(Paint.Style.STROKE)
			p.setColor(Color.BLACK)
			canvas.drawRect(rect1, p)
			canvas.drawRect(rect2, p)

			p.setStyle(Paint.Style.FILL)
			p.setColor(Color.BLUE)
			canvas.drawPath(path, p)
		}
	}
*/
	inner class DrawView(context: Context) : View(context) {
		private var p: Paint
		private var region: Region
		private var clipRegion: Region
		private var path: Path
		private var pathDst: Path
		private var rect: Rect

		init {
			p = Paint().apply {
				setStrokeWidth(3f)
				setStyle(Paint.Style.STROKE)
			}
			path = Path().apply {
				moveTo(100f, 100f)
				lineTo(150f, 150f)
				lineTo(100f, 200f)
				close()
			}
			rect = Rect(100, 100, 150, 150)
			clipRegion = Region(rect)
			region = Region().apply {
				setPath(path, clipRegion)
				pathDst = boundaryPath
			}
		}
		override fun onDraw(canvas: Canvas) {
			canvas.drawARGB(80, 102, 204, 255)
			p.setColor(Color.GREEN)
			canvas.drawPath(path, p)
			canvas.translate(200f, 0f)

			p.setColor(Color.BLUE)
			canvas.drawPath(pathDst, p)
		}
	}
}
