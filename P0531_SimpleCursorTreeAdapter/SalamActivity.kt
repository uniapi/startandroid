
/* \uFDFD
 *                     \u0648\u0671\u0630\u0643\u0631 \u0631\u0628\u0651\u0643
 *        \u0643\u062A\u0628\u0647 \u0639\u0628\u062F \u0671\u0644\u0643\u0631\u064A\u0645
 *  \u06F1\u06F4\u06F4\u06F8 \u0631\u0628\u064A\u0639 \u0671\u0644\u0622\u062E\u0631 \u06F2\u06F3
 */
package localhost.idroid.salamun

import android.util.Log
import android.content.Context
import android.database.Cursor
import android.app.Activity
import android.os.Bundle
import android.view.View
import android.view.Gravity
import android.view.ViewGroup.LayoutParams
import android.widget.LinearLayout
import android.widget.ExpandableListView
import android.widget.SimpleCursorTreeAdapter
import android.widget.TextView
import android.widget.Button

class SalamActivity : Activity() {
    private companion object {
        val TAG = "localhost.idroid.salamun"
    }
	private lateinit var db: DB
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val rootLayout = LinearLayout(this).apply {
            layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
            orientation = LinearLayout.VERTICAL
        }
		val elvMain = ExpandableListView(this).apply {
			layoutParams = LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
		}
		with(rootLayout) {
			addView(elvMain)
		}
		setContentView(rootLayout)

		db = DB(this)
		db.open()
		val cursor = db.getCompanyData()
		startManagingCursor(cursor)
		val groupFrom = arrayOf(DB.COMPANY_COLUMN_NAME)
		val groupTo = intArrayOf(android.R.id.text1)
		val childFrom = arrayOf(DB.PHONE_COLUMN_NAME)
		val childTo = intArrayOf(android.R.id.text1)
		val sctAdapter = MyAdapter(
			this,
			cursor,
			android.R.layout.simple_expandable_list_item_1,
			groupFrom,
			groupTo,
			android.R.layout.simple_list_item_1,
			childFrom,
			childTo
		)
		elvMain.setAdapter(sctAdapter)
	}
	override fun onDestroy() {
		super.onDestroy()
		db.close()
	}
	inner class MyAdapter(
		context: Context,
		cursor: Cursor?,
		groupLayout: Int,
		groupFrom: Array<String>,
		groupTo: IntArray,
		childLayout: Int,
		childFrom: Array<String>,
		childTo: IntArray
	) : SimpleCursorTreeAdapter(
		context, cursor, groupLayout, groupFrom, groupTo, childLayout, childFrom, childTo
	) {
		override fun getChildrenCursor(groupCursor: Cursor): Cursor? {
			val idColumn = groupCursor.getColumnIndex(DB.COMPANY_COLUMN_ID)
			val groupId = groupCursor.getInt(idColumn).toLong()
			return db.getPhoneData(groupId)
		}
	}
}
