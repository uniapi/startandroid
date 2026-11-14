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
import android.view.SurfaceHolder
import android.view.SurfaceView
import android.graphics.Canvas
import android.graphics.Color

class SalamActivity : Activity() {
	companion object {
		private const val TAG = "localhost.idroid.salamun"
	}
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(DrawView(this))
	}
/*
	inner class DrawView(context: Context) : View(context) {
		override fun onDraw(canvas: Canvas) {
			canvas.drawColor(Color.GREEN)
		}
	}
*/
	inner class DrawView(context: Context) : SurfaceView(context), SurfaceHolder.Callback {
		private var drawThread: DrawThread? = null
		init {
			holder.addCallback(this)
		}
		override fun surfaceChanged(holder: SurfaceHolder, format: Int, widht: Int, height: Int) {
		}
		override fun surfaceCreated(holder: SurfaceHolder) {
			drawThread = DrawThread(holder).apply {
				setRunning(true)
				start()
			}
		}
		override fun surfaceDestroyed(holder: SurfaceHolder) {
			var retry = true
			drawThread?.setRunning(false)
			while (retry) {
				try {
					drawThread?.join()
					retry = false
				} catch (e: InterruptedException) {
				}
			}
		}
		inner class DrawThread(private val surfaceHolder: SurfaceHolder): Thread() {
			@Volatile private var running = false

			fun setRunning(running: Boolean) {
				this.running = running
			}
			override fun run() {
				var canvas: Canvas?
				while (running) {
					canvas = null
					try {
						canvas = surfaceHolder.lockCanvas(null)
						if (canvas == null) continue
						canvas.drawColor(Color.GREEN)
					} finally {
						if (canvas != null) {
							surfaceHolder.unlockCanvasAndPost(canvas)
						}
					}
				}
			}
		}
	}
}
