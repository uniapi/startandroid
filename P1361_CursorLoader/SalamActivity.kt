/* \uFDFD
 *                           \u0648\u0671\u0630\u0643\u0631 \u0631\u0628\u0651\u0643
 *              \u0643\u062A\u0628\u0647 \u0639\u0628\u062F \u0671\u0644\u0643\u0631\u064A\u0645
 *  \u06F1\u06F4\u06F4\u06F8 \u062C\u0645\u0627\u062F\u0649 \u0627\u0644\u0622\u062E\u0631\u0629 \u06F3
 */
package localhost.idroid.salamun

import java.util.concurrent.TimeUnit

import android.util.Log
import android.app.Activity
import android.content.Context
import android.database.Cursor
import android.os.Bundle
import android.support.v4.app.FragmentActivity
import android.support.v4.app.LoaderManager.LoaderCallbacks
import android.support.v4.content.CursorLoader
import android.support.v4.content.Loader
import android.support.v4.widget.SimpleCursorAdapter
import android.view.ContextMenu
import android.view.ContextMenu.ContextMenuInfo
import android.view.MenuItem
import android.view.View
import android.view.Gravity
import android.view.ViewGroup.LayoutParams
import android.widget.LinearLayout
import android.widget.AdapterView.AdapterContextMenuInfo
import android.widget.ListView
import android.widget.Button

class SalamActivity : FragmentActivity(), LoaderCallbacks<Cursor> {
	companion object {
		private const val TAG = "localhost.idroid.salamun"
		private const val CM_DELETE_ID = 1
	}
	private lateinit var lvData: ListView
	private lateinit var db: DB
	private lateinit var scAdapter: SimpleCursorAdapter

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		val llRoot = LinearLayout(this).apply {
			layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
			orientation = LinearLayout.VERTICAL
		}
		val btnClick = Button(this).apply {
			layoutParams = LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
			text = getString(R.string.add_record)
			setOnClickListener { onButtonClick(it) }
		}
		lvData = ListView(this).apply {
			layoutParams = LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
		}
		with(llRoot) {
			addView(btnClick)
			addView(lvData)
		}
		setContentView(llRoot)

		db = DB(this)
		db.open()
		val from = arrayOf(DB.COLUMN_IMG, DB.COLUMN_TXT)
		val to = intArrayOf(R.id.ivImg, R.id.tvText)

		scAdapter = SimpleCursorAdapter(this, R.layout.item, null, from, to, 0)
		lvData.adapter = scAdapter

		registerForContextMenu(lvData)
		supportLoaderManager.initLoader(0, null, this)
	}
	fun onButtonClick(view: View?) {
		db.addRec("sometext ${scAdapter.count + 1}", android.R.drawable.sym_def_app_icon)
		supportLoaderManager.getLoader<Cursor>(0)?.forceLoad()
	}
	override fun onCreateContextMenu(menu: ContextMenu, view: View, menuInfo: ContextMenuInfo) {
		super.onCreateContextMenu(menu, view, menuInfo)
		menu.add(0, CM_DELETE_ID, 0, R.string.delete_record)
	}
	override fun onContextItemSelected(item: MenuItem): Boolean {
		if (item.itemId == CM_DELETE_ID) {
			val acmi = item.menuInfo as AdapterContextMenuInfo
			db.delRec(acmi.id)
			supportLoaderManager.getLoader<Cursor>(0)?.forceLoad()
			return true
		}
		return super.onContextItemSelected(item)
	}
	override fun onDestroy() {
		super.onDestroy()
		db.close()
	}
	override fun onCreateLoader(id: Int, bndl: Bundle?): Loader<Cursor> {
		return MyCursorLoader(this, db)
	}
	override fun onLoadFinished(loader: Loader<Cursor>, cursor: Cursor?) {
		scAdapter.swapCursor(cursor)
	}
	override fun onLoaderReset(loader: Loader<Cursor>) {}

	class MyCursorLoader(context: Context, private val db: DB) : CursorLoader(context) {
		override fun loadInBackground(): Cursor? {
			val cursor = db.getAllData()
			try {
				TimeUnit.SECONDS.sleep(3)
			} catch (e: InterruptedException) {
				e.printStackTrace()
			}
			return cursor
		}
	}
}
