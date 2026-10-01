/* \uFDFD
 *                     \u0648\u0671\u0630\u0643\u0631 \u0631\u0628\u0651\u0643
 *        \u0643\u062A\u0628\u0647 \u0639\u0628\u062F \u0671\u0644\u0643\u0631\u064A\u0645
 *  \u06F1\u06F4\u06F4\u06F8 \u0631\u0628\u064A\u0639 \u0671\u0644\u0622\u062E\u0631 \u06F2\u06F0
 */
package localhost.idroid.salamun

import android.util.Log
import android.app.Activity
import android.os.Bundle
import android.view.View
import android.view.Gravity
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams
import android.widget.BaseAdapter
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.AbsListView
import android.widget.LinearLayout
import android.widget.TextView
import android.graphics.Color

class SalamActivity : Activity() {
    private companion object {
        val TAG = "localhost.idroid.salamun"
    }
	private val names = arrayOf("Иван", "Марья", "Петр", "Антон", "Даша", "Борис", "Костя", "Игорь", "Анна", "Денис", "Андрей")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val rootLayout = LinearLayout(this).apply {
            layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
            orientation = LinearLayout.VERTICAL
        }
		val tvText = TextView(this).apply {
			layoutParams = LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
			text = "hello"
		}
		val lvMain = ListView(this).apply {
			layoutParams = LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
		}
		with(rootLayout) {
			addView(tvText)
			addView(lvMain)
		}
		setContentView(rootLayout)

		val pxOfDp5 = dpToPx(this, 5f)
		// val adapter: ArrayAdapter<String> = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, names)	// system item
		// val adapter = ArrayAdapter<String>(this, R.layout.my_list_item, names)										// res/layout/my_list_item.xml
		val adapter = object : ArrayAdapter<String>(this, 0 /*android.R.layout.simple_list_item_1*/, names) {
			override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
				val textView = (convertView as? TextView) ?: TextView(this@SalamActivity).apply {
					layoutParams = AbsListView.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
					gravity = Gravity.CENTER_HORIZONTAL
					text = getItem(position)
					textSize = 24f
					setTextColor(Color.parseColor("#00FF00"))
					setPadding(pxOfDp5, pxOfDp5, pxOfDp5, pxOfDp5)
				}
				return textView
			}
		}
		/*val adapter = object : BaseAdapter() {
			override fun getCount(): Int = names.size
			override fun getItem(position: Int): String = names[position]
			override fun getItemId(position: Int): Long = position.toLong()
			override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
				val textView = (convertView as? TextView) ?: TextView(this@SalamActivity).apply {
					layoutParams = AbsListView.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
					gravity = Gravity.CENTER_HORIZONTAL
					text = getItem(position)
					textSize = 24f
					setTextColor(Color.parseColor("#00FF00"))
					setPadding(pxOfDp5, pxOfDp5, pxOfDp5, pxOfDp5)
				}
				return textView
			}
		}*/
		lvMain.setAdapter(adapter)
	}
}
