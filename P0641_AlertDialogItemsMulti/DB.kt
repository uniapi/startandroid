/* \uFDFD
 *                     \u0648\u0671\u0630\u0643\u0631 \u0631\u0628\u0651\u0643
 *        \u0643\u062A\u0628\u0647 \u0639\u0628\u062F \u0671\u0644\u0643\u0631\u064A\u0645
 *  \u06F1\u06F4\u06F4\u06F8 \u0631\u0628\u064A\u0639 \u0671\u0644\u0622\u062E\u0631 \u06F2\u06F5
 */
package localhost.idroid.salamun

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteDatabase.CursorFactory
import android.database.sqlite.SQLiteOpenHelper

class DB(private val mCtx: Context) {
	companion object {
		private const val DB_NAME = "mydb"
		private const val DB_VERSION = 1
		private const val DB_TABLE = "mytab"
		const val COLUMN_ID = "_id"
		const val COLUMN_CHK = "checked"
		const val COLUMN_TXT = "txt"
		private const val DB_CREATE = """
			create table $DB_TABLE (
				$COLUMN_ID integer primary key,
				$COLUMN_CHK integer,
				$COLUMN_TXT text
			);
		"""
	}
	private var mDBHelper: DBHelper? = null
	private var mDB: SQLiteDatabase? = null
	fun open() {
		mDBHelper = DBHelper(mCtx, DB_NAME, null, DB_VERSION)
		mDB = mDBHelper?.writableDatabase
	}
	fun close() {
		mDBHelper?.close()
	}
	fun getAllData(): Cursor? {
		return mDB?.query(DB_TABLE, null, null, null, null, null, null)
	}
	fun changeRec(pos: Int, isChecked: Boolean) {
		val cv = ContentValues()
		cv.put(COLUMN_CHK, if (isChecked) 1 else 0)
		mDB?.update(DB_TABLE, cv, "$COLUMN_ID = ${pos + 1}", null)
	}
	private inner class DBHelper(
		context: Context,
		name: String,
		factory: CursorFactory?,
		version: Int
	) : SQLiteOpenHelper(context, name, factory, version) {
		override fun onCreate(db: SQLiteDatabase?) {
			db?.let {
				it.execSQL(DB_CREATE)
				val cv = ContentValues()
				for (i in 1..4) {
					cv.put(COLUMN_ID, i)
					cv.put(COLUMN_TXT, "sometext " + i)
					cv.put(COLUMN_CHK, 0)
					it.insert(DB_TABLE, null, cv)
				}
			}
		}
		override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

		}
	}
}
