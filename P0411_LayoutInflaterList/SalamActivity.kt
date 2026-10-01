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
import android.view.Gravity
import android.view.ViewGroup.LayoutParams
import android.widget.ScrollView
import android.widget.LinearLayout
import android.widget.TextView
import android.graphics.Color

class SalamActivity : Activity() {
    private companion object {
        val TAG = "localhost.idroid.salamun"
    }
	private val name = arrayOf("Иван", "Марья", "Петр", "Антон", "Даша", "Борис", "Костя", "Игорь")
	private val position = arrayOf("Программер", "Бухгалтер", "Программер", "Программер", "Бухгалтер", "Директор", "Программер", "Охранник")
	private val salary = arrayOf(13000, 10000, 13000, 13000, 10000, 15000, 13000, 8000)
	private val colors = IntArray(2)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val rootLayout = LinearLayout(this).apply {
            layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
            orientation = LinearLayout.VERTICAL
        }
		rootLayout.addView(TextView(this).apply {
			layoutParams = LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT,	LayoutParams.WRAP_CONTENT).apply {
				gravity = Gravity.CENTER_HORIZONTAL
			}
			text = "Staff list"
		})
		val scroll = ScrollView(this).apply {
            layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
		}
		val linLayout = LinearLayout(this).apply {
            layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
            orientation = LinearLayout.VERTICAL
        }
		scroll.addView(linLayout)
		rootLayout.addView(scroll)
		setContentView(rootLayout)

		colors[0] = Color.parseColor("#559966CC")
		colors[1] = Color.parseColor("#55336699")

		val ltInflater = getLayoutInflater()
		for (i in name.indices) {
			Log.d(TAG, "i = $i")
			val item = ltInflater.inflate(R.layout.item, linLayout, false)
			val tvName = (item.findViewById(R.id.tvName) as TextView).apply { text = name[i] }
			val tvPosition = (item.findViewById(R.id.tvPosition) as TextView).apply { text = "Должность: ${position[i]}" }
			val tvSalary = (item.findViewById(R.id.tvSalary) as TextView).apply { text = "Оклад: ${salary[i]}" }
			item.getLayoutParams().width = LayoutParams.MATCH_PARENT
			item.setBackgroundColor(colors[i % 2])
			linLayout.addView(item)
		}
	}
}
