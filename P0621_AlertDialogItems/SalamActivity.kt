/* \uFDFD
 *                     \u0648\u0671\u0630\u0643\u0631 \u0631\u0628\u0651\u0643
 *        \u0643\u062A\u0628\u0647 \u0639\u0628\u062F \u0671\u0644\u0643\u0631\u064A\u0645
 *  \u06F1\u06F4\u06F4\u06F8 \u0631\u0628\u064A\u0639 \u0671\u0644\u0622\u062E\u0631 \u06F2\u06F5
 */
package localhost.idroid.salamun

import android.util.Log
import android.app.Activity
import android.app.Dialog
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.DialogInterface.OnClickListener
import android.database.Cursor
import android.os.Bundle
import android.view.View
import android.view.ViewGroup.LayoutParams
import android.widget.ArrayAdapter
import android.widget.BaseAdapter
import android.widget.CursorAdapter
import android.widget.ListAdapter
import android.widget.LinearLayout
import android.widget.Button
import android.widget.Toast

class SalamActivity : Activity() {
    private companion object {
    	const val TAG = "localhost.idroid.salamun"
		const val DIALOG_ITEMS = 1
		const val DIALOG_ADAPTER = 2
		const val DIALOG_CURSOR = 3
    }
	private var cnt = 0
	private lateinit var db: DB
	private var cursor: Cursor? = null
	private val data = arrayOf("one", "two", "three", "four")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val rootLayout = LinearLayout(this).apply {
            layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
            orientation = LinearLayout.VERTICAL
        }
		val btnItems = Button(this).apply {
			id = 1
			layoutParams = LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
			text = getString(R.string.items)
		}
		val btnAdapter = Button(this).apply {
			id = 2
			layoutParams = LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
			text = getString(R.string.adapter)
		}
		val btnCursor = Button(this).apply {
			id = 3
			layoutParams = LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
			text = getString(R.string.cursor)
		}
		with(rootLayout) {
			addView(btnItems)
			addView(btnAdapter)
			addView(btnCursor)
		}
		setContentView(rootLayout)

		val onClick = View.OnClickListener { view: View? ->
			changeCount()
			when (view?.id) {
				btnItems.id -> showDialog(DIALOG_ITEMS)
				btnAdapter.id -> showDialog(DIALOG_ADAPTER)
				btnCursor.id -> showDialog(DIALOG_CURSOR)
			}
		}
		btnItems.setOnClickListener(onClick)
		btnAdapter.setOnClickListener(onClick)
		btnCursor.setOnClickListener(onClick)

		db = DB(this)
		db.open()
		cursor = db.getAllData()
		startManagingCursor(cursor)
	}
	override fun onCreateDialog(id: Int): Dialog? {
		val adb = AlertDialog.Builder(this)
		when (id) {
			DIALOG_ITEMS -> {
				adb.setTitle(R.string.items)
				adb.setItems(data, myClickListener)
			}
			DIALOG_ADAPTER -> {
				adb.setTitle(R.string.adapter)
				val adapter = ArrayAdapter<String>(this, android.R.layout.select_dialog_item, data)
				adb.setAdapter(adapter, myClickListener)
			}
			DIALOG_CURSOR -> {
				adb.setTitle(R.string.cursor)
				adb.setCursor(cursor, myClickListener, DB.COLUMN_TXT)
			}
			else -> return super.onCreateDialog(id)
		}
		return adb.create()
	}
	override fun onPrepareDialog(id: Int, dialog: Dialog) {
		val aDialog = dialog as AlertDialog
		val lAdapter = aDialog.getListView().getAdapter()
		when (id) {
			DIALOG_ITEMS, DIALOG_ADAPTER -> {
				if (lAdapter is BaseAdapter)
					lAdapter.notifyDataSetChanged()
			}
		}
	}
	private val myClickListener = OnClickListener { _, which ->
		Log.d(TAG, "which = $which")
	}
	private fun changeCount() {
		cnt++
		data[3] = cnt.toString()
		db.changeRec(4, cnt.toString())
		cursor?.requery()
	}
	override fun onDestroy() {
		super.onDestroy()
		db.close()
	}
}
