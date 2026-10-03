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
import android.widget.SimpleAdapter
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.ImageView
import android.graphics.Color

class SalamActivity : Activity() {
    private companion object {
        const val TAG = "localhost.idroid.salamun"
		const val ATTR_NAME_TEXT = "text"
		const val ATTR_NAME_VALUE = "value"
		const val ATTR_NAME_IMAGE = "image"
	val positive = android.R.drawable.arrow_up_float
	val negative = android.R.drawable.arrow_down_float
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

		val values = arrayOf(8, 4, -3, 2, -5, 0, 3, -6, 1, -1)
		val data = values.mapIndexed { i, value ->
			val img = when {
				value == 0 -> 0
				value > 0 -> positive
				else -> negative
			}
			mapOf(ATTR_NAME_TEXT to "Day ${i + 1}", ATTR_NAME_VALUE to value, ATTR_NAME_IMAGE to img)
		}
		val from = arrayOf(ATTR_NAME_TEXT, ATTR_NAME_VALUE, ATTR_NAME_IMAGE)
		val toIds = intArrayOf(R.id.tvText, R.id.tvValue, R.id.ivImg)

		val sAdapter = MySimpleAdapter(this, data, R.layout.item, from, toIds)
		lvSimple.setAdapter(sAdapter)
	}
	class MySimpleAdapter(
		context: Context?,
		data: List<Map<String, *>>,
		resource: Int,
		from: Array<String>,
		to: IntArray
	) : SimpleAdapter(context, data, resource, from, to) {
		override fun setViewText(view: TextView, text: String) {
			super.setViewText(view, text)
			if (view.id == R.id.tvValue) {
				val i = text.toIntOrNull() ?: 0
				when {
					i < 0 -> view.setTextColor(Color.RED)
					i > 0 -> view.setTextColor(Color.GREEN)
				}
			}
		}
		override fun setViewImage(view: ImageView, value: Int) {
			super.setViewImage(view, value)
			when (value) {
				positive -> view.setBackgroundColor(Color.RED)
				negative -> view.setBackgroundColor(Color.GREEN)
			}
		}
	}
}
