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
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.graphics.Picture
import android.graphics.Rect

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
		private var path: Path
		private var picture: Picture
		private var rect: Rect
		private var rect1: Rect

		init {
			rect = Rect(0, 0, 100, 100)
			rect1 = Rect(0, 0, 500, 200)
			p = Paint(Paint.ANTI_ALIAS_FLAG)
			picture = Picture()
			val canvas = picture.beginRecording(300, 300)

			p.setColor(Color.GREEN)
			canvas.drawCircle(150f, 100f, 80f, p)

			p.setColor(Color.BLUE)
			canvas.drawRect(20f, 70f, 150f, 200f, p)

			p.setColor(Color.RED)
			path = Path().apply {
				moveTo(170f, 80f)
				lineTo(240f, 210f)
				lineTo(100f, 210f)
				close()
			}
			canvas.drawPath(path, p)
			picture.endRecording()
		}
		override fun onDraw(canvas: Canvas) {
			with(canvas) {
				drawARGB(80, 102, 204, 255)
				drawPicture(picture)

				translate(300f, 0f)
				drawPicture(picture, rect)
				translate(0f, 300f)
				drawPicture(picture, rect1)
			}
		}
	}
}
