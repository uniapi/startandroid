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
import android.content.DialogInterface.OnMultiChoiceClickListener
import android.database.Cursor
import android.os.Bundle
import android.view.View
import android.view.ViewGroup.LayoutParams
import android.widget.CursorAdapter
import android.widget.LinearLayout
import android.widget.Button
import android.widget.ListView

class SalamActivity : Activity() {
    private companion object {
    	const val TAG = "localhost.idroid.salamun"
		const val DIALOG_ITEMS = 1
		const val DIALOG_CURSOR = 3
    }
	private var cnt = 0
	private lateinit var db: DB
	private var cursor: Cursor? = null
	private val data = arrayOf("one", "two", "three", "four")
	private val chkd = booleanArrayOf(false, true, true, false)

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
		val btnCursor = Button(this).apply {
			id = 3
			layoutParams = LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
			text = getString(R.string.cursor)
		}
		with(rootLayout) {
			addView(btnItems)
			addView(btnCursor)
		}
		setContentView(rootLayout)

		val onClick = View.OnClickListener { view: View? ->
			when (view?.id) {
				btnItems.id -> showDialog(DIALOG_ITEMS)
				btnCursor.id -> showDialog(DIALOG_CURSOR)
			}
		}
		btnItems.setOnClickListener(onClick)
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
				val charSequenceData = data.map { it as CharSequence }.toTypedArray()
				adb.setMultiChoiceItems(charSequenceData, chkd, myItemsMultiClickListener)
			}
			DIALOG_CURSOR -> {
				adb.setTitle(R.string.cursor)
				adb.setMultiChoiceItems(cursor, DB.COLUMN_CHK, DB.COLUMN_TXT, myCursorMultiClickListener)
			}
			else -> return super.onCreateDialog(id)
		}
		adb.setPositiveButton(R.string.ok, myBtnClickListener)
		return adb.create()
	}
	private val myItemsMultiClickListener = OnMultiChoiceClickListener { dialog: DialogInterface, which: Int, isChecked: Boolean ->
		val lv = (dialog as AlertDialog).getListView()
		Log.d(TAG, "which = $which, isChecked = $isChecked")
	}
	private val myCursorMultiClickListener = OnMultiChoiceClickListener { dialog: DialogInterface, which: Int, isChecked: Boolean ->
		val lv = (dialog as AlertDialog).getListView()
		Log.d(TAG, "which = $which, isChecked = $isChecked")
		db.changeRec(which, isChecked)
		cursor?.requery()
	}
	private val myBtnClickListener = OnClickListener { dialog: DialogInterface, which: Int ->
		val sbArray = (dialog as AlertDialog).getListView().getCheckedItemPositions()
		for (i in 0 until sbArray.size()) {
			val key = sbArray.keyAt(i)
			if (sbArray.get(key))
				Log.d(TAG, "checked: $key")
		}
	}
	override fun onDestroy() {
		super.onDestroy()
		db.close()
	}
}
