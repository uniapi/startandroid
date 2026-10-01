/* \uFDFD
 *                     \u0648\u0671\u0630\u0643\u0631 \u0631\u0628\u0651\u0643
 *        \u0643\u062A\u0628\u0647 \u0639\u0628\u062F \u0671\u0644\u0643\u0631\u064A\u0645
 *  \u06F1\u06F4\u06F4\u06F8 \u0631\u0628\u064A\u0639 \u0671\u0644\u0622\u062E\u0631 \u06F2\u06F0
 */
package localhost.idroid.salamun

import android.util.Log
import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup.LayoutParams
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView

class SalamActivity : Activity() {
    private companion object {
        val TAG = "localhost.idroid.salamun"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
		val rootLayout = LinearLayout(this).apply {
			layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
			orientation = LinearLayout.VERTICAL
		}
		val pxOfDp20 = dpToPx(this, 20f)
		val loParams = LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT).apply {
			setMargins(pxOfDp20, pxOfDp20, pxOfDp20, pxOfDp20)
		}
		val linLayout = LinearLayout(this).also { it.addView(TextView(this).apply { text = "LinearLayout: " }) }
		val relLayout = RelativeLayout(this).also { it.addView(TextView(this).apply { text = "RelativeLayout: " }) }
		with(rootLayout) {
			addView(linLayout, loParams)
			addView(relLayout, loParams)
		}
		setContentView(rootLayout)
/*
		setContentView(R.layout.main)

		val ltInflater = getLayoutInflater()
		val view = ltInflater.inflate(R.layout.text, null, false)
		val lp = view.getLayoutParams()

		val linLayout = findViewById(R.id.linLayout) as LinearLayout
		linLayout.addView(view)

		Log.d(TAG, "Class of view: ${view::class.java}")
		Log.d(TAG, "LayoutParams of view is null: ${lp == null}")
		Log.d(TAG, "Text of view: ${(view as TextView).text}")
*/
		val ltInflater = getLayoutInflater()

//		val linLayout = findViewById(R.id.linLayout) as LinearLayout
		val view1 = ltInflater.inflate(R.layout.text, linLayout, true)
		val lp1 = view1.getLayoutParams()
		Log.d(TAG, "Class of view1: ${view1::class.java}")
		Log.d(TAG, "Class of layoutParams of view1: ${lp1::class.java}")
//		Log.d(TAG, "Text of view1: ${(view1 as TextView).text}")

//		val relLayout = findViewById(R.id.relLayout) as RelativeLayout
		val view2 = ltInflater.inflate(R.layout.text, relLayout, true)
		val lp2 = view2.getLayoutParams()
		Log.d(TAG, "Class of view2: ${view2::class.java}")
		Log.d(TAG, "Class of layoutParams of view2: ${lp2::class.java}")
//		Log.d(TAG, "Text of view2: ${(view2 as TextView).text}")
	}
}
