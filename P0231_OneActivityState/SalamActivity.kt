/* \uFDFD
 *                     \u0648\u0671\u0630\u0643\u0631 \u0631\u0628\u0651\u0643
 *        \u0643\u062A\u0628\u0647 \u0639\u0628\u062F \u0671\u0644\u0643\u0631\u064A\u0645
 *  \u06F1\u06F4\u06F4\u06F8 \u0631\u0628\u064A\u0639 \u0671\u0644\u0622\u062E\u0631 \u06F1\u06F4
 */
package localhost.idroid.salamun

import android.util.Log
import android.app.Activity
import android.os.Bundle
import android.view.ViewGroup.LayoutParams
import android.view.Gravity
import android.widget.LinearLayout

class SalamActivity : Activity() {
	private companion object {
		val TAG = "localhost.idroid.salamun"
	}
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		val rootLayout = LinearLayout(this).apply {
			layoutParams = LayoutParams(
				LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT
			)
			orientation = LinearLayout.VERTICAL
		}
		setContentView(rootLayout)
		Log.d(TAG, "SalamActivity: onCreate()")
	}
	override fun onStart() {
		super.onStart()
		Log.d(TAG, "SalamActivity: onStart()")
	}
	override fun onResume() {
		super.onResume()
		Log.d(TAG, "SalamActivity: onResume()")
	}
	override fun onPause() {
		super.onPause()
		Log.d(TAG, "SalamActivity: onPause()")
	}
	override fun onStop() {
		super.onStop()
		Log.d(TAG, "SalamActivity: onStop()")
	}
	override fun onDestroy() {
		super.onDestroy()
		Log.d(TAG, "SalamActivity: onDestroy()")
	}
}
