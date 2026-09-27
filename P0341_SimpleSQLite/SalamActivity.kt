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

class SalamActivity : Activity(), View.OnClickListener {
	private companion object {
		val TAG = "localhost.idroid.salamun"
	}
	private lateinit var etName: EditText
	private lateinit var etEmail: EditText
	private lateinit var dbHelper: DBHelper
	private lateinit var btnAdd: Button
	private lateinit var btnRead: Button
	private lateinit var btnClear: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val rootLayout = LinearLayout(this).apply {
            layoutParams = LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT
            )
            orientation = LinearLayout.VERTICAL
        }
		val nameLayout = LinearLayout(this).apply {
			layoutParams = LinearLayout.LayoutParams(
				LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT
			)
		}
		val tvName = TextView(this).apply {
			layoutParams = LinearLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT
			).apply { leftMargin = dpToPx(this@SalamActivity, 5f); rightMargin = dpToPx(this@SalamActivity, 5f) }
			text = "Name"
		}
		etName = EditText(this).apply {
			layoutParams = LinearLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT,
				1.0f
			)
			requestFocus()
		}
		with(nameLayout) {
			addView(tvName)
			addView(etName)
		}
		val emailLayout = LinearLayout(this).apply {
			layoutParams = LinearLayout.LayoutParams(
				LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT
			)
		}
		val tvEmail = TextView(this).apply {
			layoutParams = LinearLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT
			).apply { leftMargin = dpToPx(this@SalamActivity, 5f); rightMargin = dpToPx(this@SalamActivity, 5f) }
			text = "Email"
		}
		etEmail = EditText(this).apply {
			layoutParams = LinearLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT,
				1.0f
			)
			requestFocus()
		}
		with(emailLayout) {
			addView(tvEmail)
			addView(etEmail)
		}
		val actionLayout = LinearLayout(this).apply {
			layoutParams = LinearLayout.LayoutParams(
				LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT
			)
		}
		btnAdd = Button(this).apply {
			id = 1
			layoutParams = LinearLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT
			)
			text = "Add"
		}
		btnRead = Button(this).apply {
			id = 2
			layoutParams = LinearLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT
			)
			text = "Read"
		}
		btnClear = Button(this).apply {
			id = 3
			layoutParams = LinearLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT
			)
			text = "Clear"
		}
		with(actionLayout) {
			addView(btnAdd)
			addView(btnRead)
			addView(btnClear)
		}
		with(rootLayout) {
			addView(nameLayout)
			addView(emailLayout)
			addView(actionLayout)
		}
		btnAdd.setOnClickListener(this)
		btnRead.setOnClickListener(this)
		btnClear.setOnClickListener(this)
		setContentView(rootLayout)
		dbHelper = DBHelper(this)
	}
	override fun onClick(view: View?) {
		val name = etName.text?.toString() ?: ""
		val email = etEmail.text?.toString() ?: ""
		val db = dbHelper.writableDatabase
		when (view?.id) {
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
				with(db.query("mytable", null, null, null, null, null, null)) {	// Cursor
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
		}
		dbHelper.close()
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
