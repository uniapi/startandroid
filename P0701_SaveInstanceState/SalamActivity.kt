/* \uFDFD
 *					 \u0648\u0671\u0630\u0643\u0631 \u0631\u0628\u0651\u0643
 *		\u0643\u062A\u0628\u0647 \u0639\u0628\u062F \u0671\u0644\u0643\u0631\u064A\u0645
 *  \u06F1\u06F4\u06F4\u06F8 \u0631\u0628\u064A\u0639 \u0671\u0644\u0622\u062E\u0631 \u06F2\u06F7
 */
package localhost.idroid.salamun

import android.util.Log
import android.app.Activity
import android.os.Bundle
import android.view.View
import android.view.Gravity
import android.view.ViewGroup.LayoutParams
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.EditText
import android.widget.Button
import android.widget.Toast

class SalamActivity : Activity() {
	private companion object {
		const val TAG = "localhost.idroid.salamun"
	}
	private var cnt = 0
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		val rootLayout = LinearLayout(this).apply {
			layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
			orientation = LinearLayout.VERTICAL
		}
		val btnClick = Button(this).apply {
			layoutParams = LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
			text = getString(R.string.count)
			setOnClickListener { _: View? ->
				Toast.makeText(this@SalamActivity, "Count = ${++cnt}", Toast.LENGTH_SHORT).show()
			}
		}
		val etText1 = EditText(this).apply {
			id = 1
			layoutParams = LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
			setEms(10)
			requestFocus()
		}
		val etText2 = EditText(this).apply {
			layoutParams = LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
			setEms(10)
		}
		with(rootLayout) {
			addView(btnClick)
			addView(etText1)
			addView(etText2)
		}
		setContentView(rootLayout)
		Log.d(TAG, "onCreate")
		// myObj = getLastNonConfigurationInstance() as? MyObject
	}
	override fun onRetainNonConfigurationInstance() {
		Log.d(TAG, "onRetainNonConfigurationInstance")
		// return myObj
	}
	override fun onDestroy() {
		super.onDestroy()
		Log.d(TAG, "onDestroy")
	}
	override fun onPause() {
		super.onPause()
		Log.d(TAG, "onPause")
	}
	override fun onRestart() {
		super.onRestart()
		Log.d(TAG, "onRestart")
	}
	override fun onRestoreInstanceState(savedInstanceState: Bundle) {
		super.onRestoreInstanceState(savedInstanceState)
		cnt = savedInstanceState.getInt("count")
		Log.d(TAG, "onRestoreInstanceState")
	}
	override fun onResume() {
		super.onResume()
		Log.d(TAG, "onResume")
	}
	override fun onSaveInstanceState(outState: Bundle) {
		super.onSaveInstanceState(outState)
		outState.putInt("count", cnt)
		Log.d(TAG, "onSaveInstanceState")
	}
	override fun onStart() {
		super.onStart()
		Log.d(TAG, "onStart")
	}
	override fun onStop() {
		super.onStop()
		Log.d(TAG, "onStop")
	}
}
