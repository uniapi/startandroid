/* \uFDFD
 *                           \u0648\u0671\u0630\u0643\u0631 \u0631\u0628\u0651\u0643
 *              \u0643\u062A\u0628\u0647 \u0639\u0628\u062F \u0671\u0644\u0643\u0631\u064A\u0645
 *  \u06F1\u06F4\u06F4\u06F8 \u062C\u0645\u0627\u062F\u0649 \u0627\u0644\u0622\u062E\u0631\u0629 \u06F1\u06F5
 */
package localhost.idroid.salamun

import android.util.Log
import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.ViewGroup.LayoutParams
import android.widget.LinearLayout
import android.graphics.Color
import android.graphics.Rect
import android.graphics.RectF
import android.graphics.Shader
import android.graphics.Matrix
import android.graphics.Paint
import android.graphics.PorterDuff
import android.graphics.PorterDuffXfermode
import android.graphics.RadialGradient
import android.graphics.Canvas
import android.graphics.ColorFilter
import android.graphics.Bitmap
import android.graphics.BitmapFactory

class SalamActivity : Activity() {
	companion object {
		private const val TAG = "localhost.idroid.salamun"
	}

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(DrawView(this))
	}
	inner class DrawView(context: Context) : View(context) {
		private lateinit var mShaderPaint: Paint
		private var mBlackPaint: Paint? = null
		private var mPaint: Paint? = null
		private var mBitmap: Bitmap? = null
		private var mRect = Rect(0, 40, 750, 370)
		private val mRectF = RectF(mRect)

		init {
			setLayerType(LAYER_TYPE_SOFTWARE, null)
			initView()
		}
		private fun initView() {
			mPaint = Paint(Paint.ANTI_ALIAS_FLAG)
			mBitmap = BitmapFactory.decodeResource(resources, R.drawable.image)
			mBitmap = Bitmap.createScaledBitmap(mBitmap, mRect.width(), mRect.height(), true)
			mShaderPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
				setShader(createShader())
				setXfermode(PorterDuffXfermode(PorterDuff.Mode.DST_OUT))
			}
			mBlackPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
				setColor(Color.BLACK)
				setAlpha(100)
			}
		}
		override fun onDraw(canvas: Canvas) {
			canvas.drawBitmap(mBitmap, 0f, 0f, mPaint)
			canvas.saveLayer(mRectF, mPaint, Canvas.ALL_SAVE_FLAG)
			canvas.drawRect(mRect, mBlackPaint)
			canvas.drawOval(mRectF, mShaderPaint)
			canvas.restore()
		}
		private fun createShader(): Shader {
			val colors = intArrayOf(0xFF000000.toInt(), 0xFF000000.toInt(), 0)
			val anchors = floatArrayOf(0f, 0.5f, 1f)
			val shader = RadialGradient(0f, 0f, 1f, colors, anchors, Shader.TileMode.CLAMP)
			val matrix = Matrix().apply {
				postTranslate(mRect.centerX().toFloat(), mRect.centerY().toFloat())
				postScale(
					(mRect.width() / 2).toFloat(),
					(mRect.height() / 2).toFloat(),
					mRect.centerX().toFloat(),
					mRect.centerY().toFloat()
				)
			}
			shader.setLocalMatrix(matrix)
			return shader
		}
	}
}
