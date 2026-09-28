/* \uFDFD
 *                     \u0648\u0671\u0630\u0643\u0631 \u0631\u0628\u0651\u0643
 *        \u0643\u062A\u0628\u0647 \u0639\u0628\u062F \u0671\u0644\u0643\u0631\u064A\u0645
 *  \u06F1\u06F4\u06F4\u06F8 \u0631\u0628\u064A\u0639 \u0671\u0644\u0622\u062E\u0631 \u06F1\u06F5
 */
package localhost.idroid.salamun

import android.util.Log
import android.content.Context
import android.content.ContentValues
import android.app.Activity
import android.os.Bundle
import android.view.View
import android.view.Gravity
import android.view.ViewGroup.LayoutParams
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.EditText
import android.widget.Button
import android.database.sqlite.SQLiteOpenHelper
import android.database.sqlite.SQLiteDatabase
import android.database.Cursor

class SalamActivity : Activity() {
	private companion object {
		val TAG = "localhost.idroid.salamun"
	}
	private lateinit var dbHelper: DBHelper
	override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val rootLayout = LinearLayout(this).apply {
            layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
            orientation = LinearLayout.VERTICAL
        }
		val llParams = LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
		val llParamsId = LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT).apply {
			setMargins(0, dpToPx(this@SalamActivity, 5f), 0, 0)
		}
		val tvParams = LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT).apply {
			setMargins(dpToPx(this@SalamActivity, 5f), 0, dpToPx(this@SalamActivity,5f), 0)
		}
		val tvParamsId = LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT).apply {
			setMargins(dpToPx(this@SalamActivity, 5f), 0, dpToPx(this@SalamActivity, 30f), 0)
		}
		val etParams = LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, 1.0f)
		val etParamsId = LinearLayout.LayoutParams(dpToPx(this, 70f), LayoutParams.WRAP_CONTENT, 1.0f).apply {
			setMargins(0, dpToPx(this@SalamActivity, 2f), 0, 0)
		}
		val idLayout = LinearLayout(this).apply {}
		val nameLayout = LinearLayout(this)
		val emailLayout = LinearLayout(this)
		val actionLayout = LinearLayout(this)
		val tvId = TextView(this).apply { text = "ID" }
		val tvName = TextView(this).apply { text = "Name" }
		val tvEmail = TextView(this).apply { text = "Email" }
		val etId = EditText(this).apply {}
		val etName = EditText(this)
		val etEmail = EditText(this)
		val btnAdd = Button(this).apply { id = 1; text = "Add" }
		val btnRead = Button(this).apply { id = 2; text = "Read" }
		val btnClear = Button(this).apply { id = 3; text = "Clear" }
		val btnUpdate = Button(this).apply { id = 4; text = "Update" }
		val btnDelete = Button(this).apply { id = 5; text = "Delete" }
		with(idLayout) {
			addView(tvId, tvParamsId)
			addView(etId, etParamsId)
			addView(btnUpdate)
			addView(btnDelete)
		}
		with(nameLayout) {
			addView(tvName, tvParams)
			addView(etName, etParams)
		}
		with(emailLayout) {
			addView(tvEmail, tvParams)
			addView(etEmail, etParams)
		}
		with(actionLayout) {
			addView(btnAdd)
			addView(btnRead)
			addView(btnClear)
		}
		with(rootLayout) {
			addView(idLayout, llParamsId)
			addView(nameLayout, llParams)
			addView(emailLayout, llParams)
			addView(actionLayout, llParams)
		}
		setContentView(rootLayout)

		dbHelper = DBHelper(this)
		val clickListener = View.OnClickListener { view: View? ->
			val id = etId.text?.toString() ?: ""
			val name = etName.text?.toString() ?: ""
			val email = etEmail.text?.toString() ?: ""
			val db = dbHelper.writableDatabase
			when(view?.id) {
				btnAdd.id -> {
					Log.d(TAG, "--- Insert in mytable: ---")
					val rowId = db.insert("mytable", null, ContentValues().apply {
						put("name", name)
						put("email", email)
					})
					Log.d(TAG, "row inserted ID = $rowId")
				}
				btnRead.id -> {
					Log.d(TAG, "--- Rows in mytable: ---")
					with(db.query("mytable", null, null, null, null, null, null)) { // Cursor c
						if (moveToFirst()) {
							val idColIndex = getColumnIndex("id")
							val nameColIndex = getColumnIndex("name")
							val emailColIndex = getColumnIndex("email")
							do {
								Log.d(TAG, "ID = ${getInt(idColIndex)}, name = ${getString(nameColIndex)}, email = ${getString(emailColIndex)}")
							} while (moveToNext())
						}
						else
							Log.d(TAG, "0 rows")
					}
				}
				btnClear.id -> {
					Log.d(TAG, "--- Clear mytable: ---")
					val count = db.delete("mytable", null, null)
					Log.d(TAG, "deleted rows count = $count")
				}
				btnUpdate.id -> {
					Log.d(TAG, "--- Update mytable: ---")
					if (!id.equals("")) {
						val cv = ContentValues().apply {
							put("name", name)
							put("email", email)
						}
						val count = db.update("mytable", cv, "id = ?", arrayOf(id))
						Log.d(TAG, "updated rows count = $count")
					}
				}
				btnDelete.id -> {
					Log.d(TAG, "--- Delete from mytable: ---")
					if (id.isNotBlank()) {	// needs passing kotlin-stdlib.jar
						val count = db.delete("mytable", "id = $id", null)
						Log.d(TAG, "deleted rows count = $count")
					}
				}
			}
			dbHelper.close()
		}
		btnAdd.setOnClickListener(clickListener)
		btnRead.setOnClickListener(clickListener)
		btnClear.setOnClickListener(clickListener)
		btnUpdate.setOnClickListener(clickListener)
		btnDelete.setOnClickListener(clickListener)
	}
	class DBHelper(context: Context?) : SQLiteOpenHelper(context, "myDB" , null, 1) {
		override fun onCreate(db: SQLiteDatabase?) {
			Log.d(TAG, "--- onCreate database ---")
			db?.execSQL("create table mytable(id integer primary key autoincrement, name text, email text)")
		}
		override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {

		}
	}
}
