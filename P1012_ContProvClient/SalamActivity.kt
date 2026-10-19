/* \uFDFD
 *                           \u0648\u0671\u0630\u0643\u0631 \u0631\u0628\u0651\u0643
 *              \u0643\u062A\u0628\u0647 \u0639\u0628\u062F \u0671\u0644\u0643\u0631\u064A\u0645
 *  \u06F1\u06F4\u06F4\u06F8 \u062C\u0645\u0627\u062F\u0649 \u0627\u0644\u0623\u0648\u0644\u0649 \u06F8
 */
package localhost.idroid.salamun

import android.util.Log
import android.app.Activity
import android.content.ContentUris
import android.content.ContentValues
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.view.Gravity
import android.view.ViewGroup.LayoutParams
import android.widget.LinearLayout
import android.widget.ListView
import android.widget.Button
import android.widget.SimpleCursorAdapter

class SalamActivity : Activity() {
	private companion object {
		const val TAG = "localhost.idroid.salamun"
	}
	val CONTACT_URI = Uri.parse("content://localhost.idroid.providers.AddressBook/contacts")
	val CONTACT_NAME = "name"
	val CONTACT_EMAIL = "email"

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		val llRoot = LinearLayout(this).apply {
			layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
			orientation = LinearLayout.VERTICAL
		}
		val llMain = LinearLayout(this).apply {
			layoutParams = LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
		}
		val paramsBtn = LinearLayout.LayoutParams(0, LayoutParams.WRAP_CONTENT, 0.25f)
		val btnInsert = Button(this).apply { text = getString(R.string.insert); setOnClickListener(onClickInsert) }
		val btnUpdate = Button(this).apply { text = getString(R.string.update); setOnClickListener(onClickUpdate) }
		val btnDelete = Button(this).apply { text = getString(R.string.delete); setOnClickListener(onClickDelete) }
		val btnError = Button(this).apply { text = getString(R.string.error); setOnClickListener(onClickError) }
		val lvContact = ListView(this).apply {
			layoutParams = LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
		}
		with(llMain) {
			addView(btnInsert, paramsBtn)
			addView(btnUpdate, paramsBtn)
			addView(btnDelete, paramsBtn)
			addView(btnError, paramsBtn)
		}
		with(llRoot) {
			addView(llMain)
			addView(lvContact)
		}
		setContentView(llRoot)

		val cursor = getContentResolver().query(CONTACT_URI, null, null, null, null)
		startManagingCursor(cursor)
		val from = arrayOf("name", "email")
		val to = intArrayOf(android.R.id.text1, android.R.id.text2)
		val adapter = SimpleCursorAdapter(this, android.R.layout.simple_list_item_2, cursor, from, to)
		lvContact.adapter = adapter
	}
	val onClickInsert = View.OnClickListener {
		val cv = ContentValues().apply {
			put(CONTACT_NAME, "name 4")
			put(CONTACT_EMAIL, "email 4")
		}
		val newUri = getContentResolver().insert(CONTACT_URI, cv)
		Log.d(TAG, "insert, result Uri : ${newUri.toString()}")
	}
	val onClickUpdate = View.OnClickListener {
		val cv = ContentValues().apply {
			put(CONTACT_NAME, "name 5")
			put(CONTACT_EMAIL, "email 5")
		}
		val uri = ContentUris.withAppendedId(CONTACT_URI, 2)
		val cnt = getContentResolver().update(uri, cv, null, null)
		Log.d(TAG, "update, count = $cnt")
	}
	val onClickDelete = View.OnClickListener {
		val uri = ContentUris.withAppendedId(CONTACT_URI, 3)
		val cnt = getContentResolver().delete(uri, null, null)
		Log.d(TAG, "delete, count = $cnt")
	}
	val onClickError = View.OnClickListener {
		val uri = Uri.parse("content://localhost.idroid.providers.AddressBook/phones")
		try {
			val cursor = getContentResolver().query(uri, null, null, null, null)
		} catch (e: Exception) {
			Log.d(TAG, "Error: ${e.javaClass}, ${e.message}")
		}
	}
}
