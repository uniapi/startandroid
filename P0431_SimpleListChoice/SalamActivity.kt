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
import android.view.ViewGroup.LayoutParams
import android.widget.ListView
import android.widget.ArrayAdapter
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Button

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
		val btnChecked = Button(this).apply {
			layoutParams = LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
			text = "Get checked items"
		}
		val lvMain = ListView(this).apply {
			layoutParams = LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
		}
		with(rootLayout) {
			addView(btnChecked)
			addView(lvMain)
		}
		setContentView(rootLayout)

//		lvMain.setChoiceMode(ListView.CHOICE_MODE_SINGLE)
		lvMain.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE)
		val adapter: ArrayAdapter<CharSequence> = ArrayAdapter.createFromResource(
			this,
			R.array.names,
			android.R.layout.simple_list_item_multiple_choice /*android.R.layout.simple_list_item_single_choice*/
		)
		lvMain.setAdapter(adapter)
		val names = getResources().getStringArray(R.array.names)
		btnChecked.setOnClickListener { _: View? ->
//			Log.d(TAG, "checked: ${names[lvMain.checkedItemPosition]}")

			Log.d(TAG, "checked: ")
			val sbArray = lvMain.checkedItemPositions
			for (i in 0 until sbArray.size()) {
				val key = sbArray.keyAt(i)
				if (sbArray.get(key))
					Log.d(TAG, names[key])
			}
		}
	}
}
