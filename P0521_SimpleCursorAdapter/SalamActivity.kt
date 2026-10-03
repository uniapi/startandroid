/* \uFDFD
 *                     \u0648\u0671\u0630\u0643\u0631 \u0631\u0628\u0651\u0643
 *        \u0643\u062A\u0628\u0647 \u0639\u0628\u062F \u0671\u0644\u0643\u0631\u064A\u0645
 *  \u06F1\u06F4\u06F4\u06F8 \u0631\u0628\u064A\u0639 \u0671\u0644\u0622\u062E\u0631 \u06F2\u06F3
 */
package localhost.idroid.salamun

import android.util.Log
import android.app.Activity
import android.os.Bundle
import android.database.Cursor
import android.view.View
import android.view.ContextMenu
import android.view.ContextMenu.ContextMenuInfo
import android.view.MenuItem
import android.view.ViewGroup.LayoutParams
import android.widget.AdapterView.AdapterContextMenuInfo
import android.widget.ListView
import android.widget.SimpleCursorAdapter
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Button

class SalamActivity : Activity() {
    private companion object {
        val TAG = "localhost.idroid.salamun"
    }
	private val CM_DELETE_ID = 1
	private lateinit var db: DB
	private var cursor: Cursor? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val rootLayout = LinearLayout(this).apply {
            layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
            orientation = LinearLayout.VERTICAL
        }
		val btnAdd = Button(this).apply {
			layoutParams = LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
			text = getString(R.string.add_record)
		//	setText(R.string.add_record)
			setOnClickListener { onButtonClick(it) }
		}
		val lvData = ListView(this).apply {
			layoutParams = LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
		}
		with(rootLayout) {
			addView(btnAdd)
			addView(lvData)
		}
		setContentView(rootLayout)

		db = DB(this)
		db.open()
		cursor = db.getAllData()
		cursor?.let { c -> startManagingCursor(c) }

		val from = arrayOf(DB.COLUMN_IMG, DB.COLUMN_TXT)
		val toIds = intArrayOf(R.id.ivImg, R.id.tvText)
		val scAdapter = SimpleCursorAdapter(this, R.layout.item, cursor, from, toIds)
		lvData.adapter = scAdapter
		registerForContextMenu(lvData)
	}
	fun onButtonClick(view: View?) {
		db.addRec("sometext ${(cursor?.count ?: 0) + 1}", android.R.drawable.sym_def_app_icon)
		cursor?.requery()
	}
	override fun onCreateContextMenu(menu: ContextMenu?, view: View?, menuInfo: ContextMenuInfo?) {
		super.onCreateContextMenu(menu, view, menuInfo)
		menu?.add(0, CM_DELETE_ID, 0, R.string.delete_record)
	}
	override fun onContextItemSelected(item: MenuItem?): Boolean {
		if (item?.itemId == CM_DELETE_ID) {
			val acmi = item.getMenuInfo() as AdapterContextMenuInfo
			db.delRec(acmi.id)
			cursor?.requery()
			return true
		}
		return super.onContextItemSelected(item)
	}
	override fun onDestroy() {
		super.onDestroy()
		db.close()
	}
}
