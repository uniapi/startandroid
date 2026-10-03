/* \uFDFD
 *                     \u0648\u0671\u0630\u0643\u0631 \u0631\u0628\u0651\u0643
 *        \u0643\u062A\u0628\u0647 \u0639\u0628\u062F \u0671\u0644\u0643\u0631\u064A\u0645
 *  \u06F1\u06F4\u06F4\u06F8 \u0631\u0628\u064A\u0639 \u0671\u0644\u0622\u062E\u0631 \u06F2\u06F3
 */
package localhost.idroid.salamun

import android.util.Log
import android.app.Activity
import android.os.Bundle
import android.view.ContextMenu
import android.view.ContextMenu.ContextMenuInfo
import android.view.MenuItem
import android.view.View
import android.view.Gravity
import android.view.ViewGroup.LayoutParams
import android.widget.AdapterView.AdapterContextMenuInfo
import android.widget.ListView
import android.widget.SimpleAdapter
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Button

class SalamActivity : Activity() {
    private companion object {
        val TAG = "localhost.idroid.salamun"
		private const val CM_DELETE_ID = 1
		const val ATTR_NAME_TEXT = "text"
		const val ATTR_NAME_IMAGE = "image"
    }
	private lateinit var sAdapter: SimpleAdapter
	private lateinit var data: ArrayList<Map<String, Any>>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val rootLayout = LinearLayout(this).apply {
            layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
            orientation = LinearLayout.VERTICAL
        }
		val btnAdd = Button(this).apply {
			layoutParams = LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
			text = "Добавить запись"
		//	setOnClickListener(::onButtonClick)
			setOnClickListener { onButtonClick(it) }
		}
		val lvSimple = ListView(this).apply {
			layoutParams = LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
		}
		with(rootLayout) {
			addView(btnAdd)
			addView(lvSimple)
		}
		setContentView(rootLayout)

		data = ArrayList()
		for (i in 1..4) {
			val m = mutableMapOf<String, Any>(
				ATTR_NAME_TEXT to "sometext $i",
				ATTR_NAME_IMAGE to android.R.drawable.sym_def_app_icon
			)
			data.add(m)
		}
		val from = arrayOf(ATTR_NAME_TEXT, ATTR_NAME_IMAGE)
		val toIds = intArrayOf(R.id.tvText, R.id.ivImg)
		sAdapter = SimpleAdapter(this, data, R.layout.item, from, toIds)
		lvSimple.adapter = sAdapter
		registerForContextMenu(lvSimple)
	}
	fun onButtonClick(view: View?) {
		val m = mutableMapOf<String, Any>(
			ATTR_NAME_TEXT to "sometext ${data.size + 1}",
			ATTR_NAME_IMAGE to android.R.drawable.sym_def_app_icon
		)
		data.add(m)
		sAdapter.notifyDataSetChanged()
	}
	override fun onCreateContextMenu(menu: ContextMenu?, view: View?, menuInfo: ContextMenuInfo?) {
		super.onCreateContextMenu(menu, view, menuInfo)
		menu?.add(0, CM_DELETE_ID, 0, "Удалить запись")
	}
	override fun onContextItemSelected(item: MenuItem?): Boolean {
		if (item?.itemId == CM_DELETE_ID) {
			val acmi = item?.menuInfo as? AdapterContextMenuInfo
			acmi?.let { info ->
				data.removeAt(info.position)
				sAdapter.notifyDataSetChanged()
			}
			return true
		}
		return super.onContextItemSelected(item)
	}
}
