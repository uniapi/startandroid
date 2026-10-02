/* \uFDFD
 *                     \u0648\u0671\u0630\u0643\u0631 \u0631\u0628\u0651\u0643
 *        \u0643\u062A\u0628\u0647 \u0639\u0628\u062F \u0671\u0644\u0643\u0631\u064A\u0645
 *  \u06F1\u06F4\u06F4\u06F8 \u0631\u0628\u064A\u0639 \u0671\u0644\u0622\u062E\u0631 \u06F2\u06F1
 */
package localhost.idroid.salamun

import android.util.Log
import android.app.Activity
import android.os.Bundle
import android.view.View
import android.view.Gravity
import android.view.ViewGroup.LayoutParams
import android.widget.LinearLayout
import android.widget.AbsListView
import android.widget.ListView
import android.widget.AdapterView
import android.widget.ArrayAdapter

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
		val lvMain = ListView(this).apply {
			layoutParams = LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
		}
		with(rootLayout) {
			addView(lvMain)
		}
		setContentView(rootLayout)

		val adapter: ArrayAdapter<CharSequence> = ArrayAdapter.createFromResource(
			this, R.array.names, android.R.layout.simple_list_item_1
		)
		lvMain.adapter = adapter
		lvMain.setOnItemClickListener { _: AdapterView<*>, _: View?, position: Int, id: Long ->
			Log.d(TAG, "itemClick: position = $position, id = $id")
		}
		lvMain.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
			override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
				Log.d(TAG, "itemSelected: position = $position, id = $id")
			}
			override fun onNothingSelected(parent: AdapterView<*>) {
				Log.d(TAG, "itemSelected: nothing")
			}
		})
		lvMain.setOnScrollListener(object : AbsListView.OnScrollListener {
			override fun onScrollStateChanged(view: AbsListView?, scrollState: Int) {
				Log.d(TAG, "scrollState = $scrollState")
			}
			override fun onScroll(view: AbsListView?, firstVisibleItem: Int, visibleItemCount: Int, totalItemCount: Int) {
				Log.d(TAG, "scroll: firstVisibleItem = $firstVisibleItem, visibleItemCount = $visibleItemCount" +
					", totalItemCount = $totalItemCount")
			}
		})
	}
}
