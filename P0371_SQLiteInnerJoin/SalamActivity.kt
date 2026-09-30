/* \uFDFD
 *                     \u0648\u0671\u0630\u0643\u0631 \u0631\u0628\u0651\u0643
 *        \u0643\u062A\u0628\u0647 \u0639\u0628\u062F \u0671\u0644\u0643\u0631\u064A\u0645
 *  \u06F1\u06F4\u06F4\u06F8 \u0631\u0628\u064A\u0639 \u0671\u0644\u0622\u062E\u0631 \u06F1\u06F5
 */
package localhost.idroid.salamun

import android.util.Log
import android.content.Context
import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.database.Cursor
import android.app.Activity
import android.os.Bundle

class SalamActivity : Activity() {
	private companion object {
		const val TAG = "localhost.idroid.salamun"
	}

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		val dbh = DBHelper(this)
		val db = dbh.writableDatabase

		Log.d(TAG, "--- Table position ---")
		val cPosition = db.query("[position]", null, null, null, null, null, null)
		logCursor(cPosition)
		cPosition.close()
		Log.d(TAG, "--- ---")

		Log.d(TAG, "--- Table people ---")
		val cPeople = db.query("people", null, null, null, null, null, null)
		logCursor(cPeople)
		cPeople.close()
		Log.d(TAG, "--- ---")

		Log.d(TAG, "--- INNER JOIN with rawQuery ---")
		val sqlQuery = "select PL.name as Name, PS.name as Position, salary as Salary " +
						"from people as PL " +
						"inner join position as PS " +
						"on PL.posid = PS.id " +
						"where salary > ?"
		val cRaw = db.rawQuery(sqlQuery, arrayOf("12000"))
		logCursor(cRaw)
		cRaw.close()
		Log.d(TAG, "--- ---")

		Log.d(TAG, "--- INNER JOIN with query ---")
		val table = "people as PL inner join position as PS on PL.posid = PS.id"
		val columns = arrayOf("PL.name as Name", "PS.name as Position", "salary as Salary")
		val selection = "salary < ?"
		val selectionArgs = arrayOf("12000")
		val cQuery = db.query(table, columns, selection, selectionArgs, null, null, null)
		logCursor(cQuery)
		cQuery.close()
		Log.d(TAG, "--- ---")

		dbh.close()
	}

	private fun logCursor(cursor: Cursor?) {
		cursor?.let { c ->
			if (c.moveToFirst()) {
				val columns = c.columnNames
				do {
					val sb = StringBuilder()
					for (cn in columns) {
						val columnIndex = c.getColumnIndex(cn)
						if (columnIndex != -1) {
							val value = c.getString(columnIndex)
							sb.append("$cn = $value; ")
						}
					}
					Log.d(TAG, sb.toString())
				} while (c.moveToNext())
			}
		} ?: Log.d(TAG, "Cursor is null")
	}

	class DBHelper(context: Context?) : SQLiteOpenHelper(context, "myDB", null, 1) {
		override fun onCreate(db: SQLiteDatabase?) {
			Log.d(TAG, "--- onCreate database ---")
			val posId = arrayOf(1, 2, 3, 4)
			val posName = arrayOf("Директор", "Программер", "Бухгалтер", "Охранник")
			val posSalary = arrayOf(15000, 13000, 10000, 8000)
			val peopName = arrayOf("Иван", "Марья", "Петр", "Антон", "Даша", "Борис", "Костя", "Игорь")
			val peopPosId = arrayOf(2, 3, 2, 2, 3, 1, 2, 4)
			db?.let {
				it.execSQL("create table [position](id integer primary key, name text, salary integer);")
				it.execSQL("create table people(id integer primary key autoincrement, name text, posid integer);")
				val cv = ContentValues()
				for (i in posId.indices) {
					cv.clear()
					cv.put("id", posId[i])
					cv.put("name", posName[i])
					cv.put("salary", posSalary[i])
					it.insert("[position]", null, cv)
				}
				for (i in peopName.indices) {
					cv.clear()
					cv.put("name", peopName[i])
					cv.put("posid", peopPosId[i])
					it.insert("people", null, cv)
				}
			}
			Log.d(TAG, "--- ---")
		}

		override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {}
	}
}
