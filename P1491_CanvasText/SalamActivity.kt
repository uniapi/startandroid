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
import android.graphics.Typeface

class SalamActivity : Activity() {
	companion object {
		private const val TAG = "localhost.idroid.salamun"
	}
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(DrawView(this))
	}
/*	inner class DrawView(context: Context) : View(context) {
		private var fontPaint: Paint
		private var redPaint: Paint
		private var text = "Test width text"
		private var fontSize = 100
		private var widths: FloatArray
		private var width: Float

		init {
			redPaint = Paint().apply {
				setColor(Color.RED)
			}
			fontPaint = Paint().apply {
				setTextSize(fontSize.toFloat())
				setStyle(Paint.Style.STROKE)
			}
			width = fontPaint.measureText(text)
			widths = FloatArray(text.length)
			fontPaint.getTextWidths(text, widths)
		}
		override fun onDraw(canvas: Canvas) {
			canvas.drawARGB(80, 102, 204, 255)

			canvas.translate(50f, 250f)
			canvas.drawText(text, 0f, 0f, fontPaint)
			canvas.drawLine(0f, 0f, width, 0f, fontPaint)

			canvas.drawCircle(0f, 0f, 3f, redPaint)
			for (w in widths) {
				canvas.translate(w, 0f)
				canvas.drawCircle(0f, 0f, 3f, redPaint)
			}
		}
	}
*/
/*	inner class DrawView(context: Context) : View(context) {
		private var p: Paint
		private var text = "Test width text"
		private var fontSize = 80
		private var maxWidth = 350
		private var realWidth: Float
		private var cnt = 0
		private var info = ""

		init {
			p = Paint(Paint.ANTI_ALIAS_FLAG).apply {
				setTextSize(fontSize.toFloat())
			}
			val measuredWidth = FloatArray(1)
			cnt = p.breakText(text, true, maxWidth.toFloat(), measuredWidth)
			realWidth = measuredWidth[0]
			info = "cnt = $cnt, realWidth = $realWidth, maxWidth = $maxWidth"
		}
		override fun onDraw(canvas: Canvas) {
			canvas.drawARGB(80, 102, 204, 255)

			p.setTextSize(24f)
			canvas.drawText(info, 50f, 50f, p)
			p.setTextSize(fontSize.toFloat())
			canvas.drawText(text, 50f, 250f, p)

			p.setStrokeWidth(10f)
			p.setColor(Color.BLUE)
			canvas.drawLine(50f, 260f, 50f + realWidth, 260f, p)

			p.setColor(Color.GREEN)
			canvas.drawLine(50f, 270f, 50f + maxWidth, 270f, p)
		}
	}
*/
	inner class DrawView(context: Context) : View(context) {
		private var p: Paint
		private var text = "Test width text"
		private var fontSize = 100
		private var y: Float = 120f
		private var pos: FloatArray

		init {
			p = Paint(Paint.ANTI_ALIAS_FLAG).apply {
				setTextSize(fontSize.toFloat())
				setStyle(Paint.Style.STROKE)
			}
			pos = floatArrayOf(100f, 300f, 200f, 150f, 300f, 500f, 400f, 300f, 500f,
				250f, 600f, 350f, 700f, 400f, 800f, 200f, 900f, 500f
			)
		}
		override fun onDraw(canvas: Canvas) {
			canvas.drawARGB(80, 102, 204, 255)
			p.setColor(Color.GREEN)

			canvas.translate(50f, y)
			canvas.drawText(text, 0f, 0f, p)

			canvas.translate(0f, y)
			p.setTypeface(Typeface.create(Typeface.MONOSPACE, Typeface.NORMAL))
			canvas.drawText(text, 0f, 0f, p)

			canvas.translate(0f, y)
			p.setTypeface(Typeface.create(Typeface.SERIF, Typeface.NORMAL))
			canvas.drawText(text, 0f, 0f, p)

			canvas.translate(0f, y)
			p.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD))
			canvas.drawText(text, 0f, 0f, p)

			canvas.translate(0f, y)
			p.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD_ITALIC))
			canvas.drawText(text, 0f, 0f, p)

			canvas.translate(0f, y)
			p.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.ITALIC))
			canvas.drawText(text, 0f, 0f, p)

			canvas.translate(0f, y)
			p.setTextScaleX(1.5f)
			canvas.drawText(text, 0f, 0f, p)
			p.setTextScaleX(1f)

			canvas.translate(0f, y)
			p.setTextSkewX(0.5f)
			canvas.drawText(text, 0f, 0f, p)
			p.setTextSkewX(0f)

			canvas.translate(0f, y)
			p.setUnderlineText(true)
			canvas.drawText(text, 0f, 0f, p)
			p.setUnderlineText(false)

			canvas.translate(0f, y)
			p.setStrikeThruText(true)
			canvas.drawText(text, 0f, 0f, p)
			p.setStrikeThruText(false)

			canvas.translate(0f, y)
			canvas.drawPosText("Test text", pos, p)
		}
	}
}
