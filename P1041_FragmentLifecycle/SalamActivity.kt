/* \uFDFD
 *                           \u0648\u0671\u0630\u0643\u0631 \u0631\u0628\u0651\u0643
 *              \u0643\u062A\u0628\u0647 \u0639\u0628\u062F \u0671\u0644\u0643\u0631\u064A\u0645
 *  \u06F1\u06F4\u06F4\u06F8 \u062C\u0645\u0627\u062F\u0649 \u0627\u0644\u0623\u0648\u0644\u0649 \u06F9
 */
package localhost.idroid.salamun

import android.util.Log
import android.app.Activity
import android.app.Fragment
import android.os.Bundle
import android.view.View
import android.view.Gravity
import android.view.ViewGroup.LayoutParams
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Button

class SalamActivity : Activity() {
	private companion object {
		const val TAG = "localhost.idroid.salamun"
	}
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.main)
		Log.d(TAG, "MainActivity onCreate")
	}
	override fun onStart() {
		super.onStart()
		Log.d(TAG, "SalamActivity onStart")
	}
	override fun onResume() {
		super.onResume()
		Log.d(TAG, "SalamActivity onResume")
	}
	override fun onPause() {
		super.onPause()
		Log.d(TAG, "SalamActivity onPause")
	}
	override fun onStop() {
		super.onStop()
		Log.d(TAG, "SalamActivity onStop")
	}
	override fun onDestroy() {
		super.onDestroy()
		Log.d(TAG, "SalamActivity onDestroy")
	}
}
