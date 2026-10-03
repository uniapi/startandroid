/* \uFDFD
 *                     \u0648\u0671\u0630\u0643\u0631 \u0631\u0628\u0651\u0643
 *        \u0643\u062A\u0628\u0647 \u0639\u0628\u062F \u0671\u0644\u0643\u0631\u064A\u0645
 *  \u06F1\u06F4\u06F4\u06F8 \u0631\u0628\u064A\u0639 \u0671\u0644\u0622\u062E\u0631 \u06F2\u06F2
 */
package localhost.idroid.salamun

import android.util.Log
import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.ViewGroup.LayoutParams
import android.widget.ListView
import android.widget.ProgressBar
import android.widget.SimpleAdapter
import android.widget.LinearLayout
import android.widget.TextView

class SalamActivity : Activity() {
    private companion object {
        val TAG = "localhost.idroid.salamun"
		const val ATTR_NAME_TEXT = "text"
		const val ATTR_NAME_PB = "pb"
		const val ATTR_NAME_LL = "ll"

    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val rootLayout = LinearLayout(this).apply {
            layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
            orientation = LinearLayout.VERTICAL
        }
		val lvSimple = ListView(this).apply {
			layoutParams = LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
		}
		with(rootLayout) {
			addView(lvSimple)
		}
		setContentView(rootLayout)

		val load = arrayOf(41, 48, 22, 35, 30, 67, 51, 88)
		val data = load.mapIndexed { i, value ->
			mapOf(ATTR_NAME_TEXT to "Day ${i + 1}. Load: $value%", ATTR_NAME_PB to value, ATTR_NAME_LL to value)
		}
		val from = arrayOf(ATTR_NAME_TEXT, ATTR_NAME_PB, ATTR_NAME_LL)
		val toIds = intArrayOf(R.id.tvLoad, R.id.pbLoad, R.id.llLoad)
		val sAdapter = SimpleAdapter(this, data, R.layout.item, from, toIds).apply {
			setViewBinder(MyViewBinder(this@SalamActivity))
		}
		lvSimple.adapter = sAdapter
	}
	class MyViewBinder(context: Context) : SimpleAdapter.ViewBinder {
		val red = context.getResources().getColor(R.color.Red)
		val orange = context.getResources().getColor(R.color.Orange)
		val green = context.getResources().getColor(R.color.Green)
		override fun setViewValue(view: View, data: Any, textRepresentation: String): Boolean {
			return when (view.id) {
				R.id.llLoad -> {
					val i = data as Int
					view.setBackgroundColor(
						when {
							i < 40 -> green
							i < 70 -> orange
							else -> red
						}
					)
					true
				}
				R.id.pbLoad -> {
					(view as ProgressBar).progress = data as Int
					true
				}
				else -> false
			}
		}
	}
}
