/* \uFDFD
 *                     \u0648\u0671\u0630\u0643\u0631 \u0631\u0628\u0651\u0643
 *        \u0643\u062A\u0628\u0647 \u0639\u0628\u062F \u0671\u0644\u0643\u0631\u064A\u0645
 *  \u06F1\u06F4\u06F4\u06F8 \u0631\u0628\u064A\u0639 \u0671\u0644\u0622\u062E\u0631 \u06F2\u06F4
 */
package localhost.idroid.salamun

import android.util.Log
import android.app.Activity
import android.os.Bundle
import android.view.View
import android.view.Gravity
import android.view.ViewGroup.LayoutParams
import android.widget.HeaderViewListAdapter
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Button

class SalamActivity : Activity() {
    private companion object {
        val TAG = "localhost.idroid.salamun"
    }
	private val data = arrayOf("one", "two", "three", "four", "five")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val rootLayout = LinearLayout(this).apply {
            layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
            orientation = LinearLayout.VERTICAL
        }
		val btnClick = Button(this).apply {
			layoutParams = LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
			text = getString(R.string.button_text)
		}
		val lvMain = ListView(this).apply {
			layoutParams = LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
		}
		with(rootLayout) {
			addView(btnClick)
			addView(lvMain)
		}
		setContentView(rootLayout)

		val adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, data)
		val header1 = createHeader("header 1")
		val header2 = createHeader("header 2")
		val footer1 = createFooter("footer 1")
		val footer2 = createFooter("footer 2")

/*		btnClick.setOnClickListener { _: View? ->
			lvMain.removeHeaderView(header2)
			lvMain.removeFooterView(footer2)
		}*/
		btnClick.setOnClickListener { _: View? ->
			val hvlAdapter = lvMain.adapter as HeaderViewListAdapter
			var obj: Any? = hvlAdapter.getItem(1)
			Log.d(TAG, "hvlAdapter.getItem(1) = $obj")
			obj = hvlAdapter.getItem(4)
			Log.d(TAG, "hvlAdapter.getItem(4) = $obj")

			val alAdapter = hvlAdapter.wrappedAdapter as ArrayAdapter<String>
			obj = alAdapter.getItem(1)
			Log.d(TAG, "alAdapter.getItem(1) = $obj")
			obj = alAdapter.getItem(4)
			Log.d(TAG, "alAdapter.getItem(4) = $obj")
		}
		fun fillList() {
			lvMain.addHeaderView(header1)
			lvMain.addHeaderView(header2, "some text for header 2", false)
			lvMain.addFooterView(footer1)
			lvMain.addFooterView(footer2, "some text for footer 2", false)
			lvMain.setAdapter(adapter)
		}
		fillList()
	}
//	private fun fillList() {

//	}
	private fun createHeader(text: String?): View {
		val view = getLayoutInflater().inflate(R.layout.header, null)
		(view.findViewById(R.id.tvText) as TextView).setText(text ?: "")
		return view
	}
	private fun createFooter(text: String?): View {
		val view = getLayoutInflater().inflate(R.layout.footer, null)
		(view.findViewById(R.id.tvText) as TextView).setText(text ?: "")
		return view
	}
}
