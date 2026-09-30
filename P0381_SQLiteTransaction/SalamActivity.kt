/* \uFDFD
 *                     \u0648\u0671\u0630\u0643\u0631 \u0631\u0628\u0651\u0643
 *        \u0643\u062A\u0628\u0647 \u0639\u0628\u062F \u0671\u0644\u0643\u0631\u064A\u0645
 *  \u06F1\u06F4\u06F4\u06F8 \u0631\u0628\u064A\u0639 \u0671\u0644\u0622\u062E\u0631 \u06F1\u06F5
 */
package localhost.idroid.salamun

import android.util.Log
import android.content.ContentValues
import android.content.Context
import android.app.Activity
import android.os.Bundle
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class SalamActivity : Activity() {
    private companion object {
        val TAG = "localhost.idroid.salamun"
    }
	private lateinit var dbh: DBHelper
	private lateinit var db: SQLiteDatabase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
		Log.d(TAG, "--- onCreate Activity ---")
		dbh = DBHelper(this)
		myActions()
	}
	private fun myActions() {
	  try {
		db = dbh.writableDatabase
		val db2 = dbh.writableDatabase
		Log.d(TAG, "db = db2 - ${db.equals(db2)}")
		Log.d(TAG, "db open - ${db.isOpen()}, db2 open - ${db2.isOpen()}")
		db2.close()
		Log.d(TAG, "db open - ${db.isOpen()}, db2 open - ${db2.isOpen()}")
	  } catch (e: Throwable) {
		Log.d(TAG, "${Log.getStackTraceString(e)}")
	  }
		/* Recommendation for Transaction:
		db.beginTransaction()
		try {
			//
			db.setTransactionSuccessful()
		} finally {
			db.endTransaction()
		}*/
	}
	private fun insert(db: SQLiteDatabase?, table: String, value: String) {
		Log.d(TAG, "Insert in table $table value = $value")
		val cv = ContentValues()
		cv.put("val_text", value)
		db?.insert(table, null, cv)
	}
	private fun read(db: SQLiteDatabase?, table: String) {
		Log.d(TAG, "Read table $table")
		val cursor = db?.query(table, null, null, null, null, null, null)
		cursor?.let { c ->
			Log.d(TAG, "Records count = ${c.getCount()}")
			if (c.moveToFirst()) {
				do {
					Log.d(TAG, c.getString(c.getColumnIndex("val_text")))
				} while (c.moveToNext())
			}
			c.close()
		}
	}
	private fun delete(db: SQLiteDatabase?, table: String) {
		Log.d(TAG, "Delete all from table $table")
		db?.delete(table, null, null)
	}
	class DBHelper(context: Context?) : SQLiteOpenHelper(context, "myDB", null, 1) {
		override fun onCreate(db: SQLiteDatabase?) {
			Log.d(TAG, "--- onCreate database ---")
			db?.execSQL("create table mytable(id integer primary key autoincrement, val_text text);")
		}
		override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

		}
	}
}
