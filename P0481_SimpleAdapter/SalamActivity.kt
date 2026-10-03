/* \uFDFD
 *                     \u0648\u0671\u0630\u0643\u0631 \u0631\u0628\u0651\u0643
 *        \u0643\u062A\u0628\u0647 \u0639\u0628\u062F \u0671\u0644\u0643\u0631\u064A\u0645
 *  \u06F1\u06F4\u06F4\u06F8 \u0631\u0628\u064A\u0639 \u0671\u0644\u0622\u062E\u0631 \u06F2\u06F2
 */
package localhost.idroid.salamun

import android.util.Log
import android.app.Activity
import android.os.Bundle
import android.view.View
import android.view.Gravity
import android.view.ViewGroup.LayoutParams
import android.widget.SimpleAdapter
import android.widget.ListView
import android.widget.LinearLayout
import android.widget.TextView

class SalamActivity : Activity() {
    private companion object {
        const val TAG = "localhost.idroid.salamun"
		const val ATTR_NAME_TEXT = "text"
		const val ATTR_NAME_CHECKED = "checked"
		const val ATTR_NAME_IMAGE = "image"

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

		val texts = arrayOf("sometext 1", "sometext 2", "sometext 3", "sometext 4", "sometext 5")
		val checked = arrayOf(true, false, false, true, false)
		val img = android.R.drawable.sym_def_app_icon
/*		val data = ArrayList<Map<String, Object>>(texts.size)
		for (i in texts.indices) {
			val m = HashMap<String, Object>()
			m[ATTR_NAME_TEXT] = texts[i]
			m[ATTR_NAME_CHECKED] = checked[i]
			m[ATTR_NAME_IMAGE] = img
			data.add(m)
		}*/
		val data = texts.mapIndexed { i, text ->
			mapOf(ATTR_NAME_TEXT to text, ATTR_NAME_CHECKED to checked[i], ATTR_NAME_IMAGE to img)
		}
		val from = arrayOf(ATTR_NAME_TEXT, ATTR_NAME_CHECKED, ATTR_NAME_IMAGE, ATTR_NAME_TEXT)
		val to = intArrayOf( R.id.tvText, R.id.cbChecked, R.id.ivImg, R.id.cbChecked)
		val sAdapter = SimpleAdapter(this, data, R.layout.item, from, to)
		lvSimple.setAdapter(sAdapter)
	}
}
