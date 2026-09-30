/* \uFDFD
 *                     \u0648\u0671\u0630\u0643\u0631 \u0631\u0628\u0651\u0643
 *        \u0643\u062A\u0628\u0647 \u0639\u0628\u062F \u0671\u0644\u0643\u0631\u064A\u0645
 *  \u06F1\u06F4\u06F4\u06F8 \u0631\u0628\u064A\u0639 \u0671\u0644\u0622\u062E\u0631 \u06F1\u06F9
 */
package localhost.idroid.salamun

import android.util.Log
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.app.Activity
import android.os.Bundle

class SalamActivity : Activity() {
    private companion object {
        val TAG = "localhost.idroid.salamun"
		val DB_NAME = "staff"
//		val DB_VERSION = 1
		val DB_VERSION = 2
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
		val dbh = DBHelper(this)
		val db = dbh.writableDatabase
		Log.d(TAG, "--- Staff db v.${db.version} ---")
		writeStaff(db)
		dbh.close()
	}
	/* Version 1:
	private fun writeStaff(db: SQLiteDatabase?) {
		val c = db?.rawQuery("select * from people", null)
		logCursor(c, "Table people")
		c?.close()
	}*/
	private fun writeStaff(db: SQLiteDatabase) {
		var c = db.rawQuery("select * from people", null)
		logCursor(c, "Table people")
		c?.close()
		val sqlQuery = "select PL.name as Name, PS.name as Position, salary as Salary " +
			"from people as PL " +
			"inner join position as PS " +
			"on PL.posid = PS.id "
		c = db.rawQuery(sqlQuery, null)
		logCursor(c, "inner join")
		c?.close()
	}
	private fun logCursor(cursor: Cursor?, title:  String) {
		cursor?.let { c ->
			if (c.moveToFirst()) {
				Log.d(TAG, "$title. ${c.getCount()} rows")
				val sb = StringBuilder()
				do {
					sb.setLength(0)
					for (cn in c.getColumnNames()) {
						val columnIndex = c.getColumnIndex(cn)
						if (columnIndex != -1)
							sb.append("$cn = ${c.getString(columnIndex)}; ")
					}
					Log.d(TAG, sb.toString())
				} while (c.moveToNext())
			}
		} ?: Log.d(TAG, "Cursor is null")
	}
	class DBHelper(context: Context?) : SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {
		override fun onCreate(db: SQLiteDatabase?) {
			Log.d(TAG, "--- onCreate database ---")
			/* Version: 1
			val people_name = arrayOf("Иван", "Марья", "Петр", "Антон", "Даша", "Борис", "Костя", "Игорь")
			val people_positions = arrayOf("Программер", "Бухгалтер", "Программер", "Программер", "Бухгалтер", "Директор",
				"Программер", "Охранник")
			val cv = ContentValues()
			db?.let {
				it.execSQL("create table people(id integer primary key autoincrement, name text, [position] text);")
				for (i in people_name.indices) {
					cv.clear()
					cv.put("name", people_name[i])
					cv.put("[position]", people_positions[i])
					it.insert("people", null, cv)
				}
			}*/
			val people_name = arrayOf("Иван", "Марья", "Петр", "Антон", "Даша", "Борис", "Костя", "Игорь")
			val people_posid = arrayOf(2, 3, 2, 2, 3, 1, 2, 4)
			val position_id = arrayOf(1, 2, 3, 4)
			val position_name = arrayOf("Директор", "Программер", "Бухгалтер", "Охранник")
			val position_salary = arrayOf(15000, 13000, 10000, 8000)
			val cv = ContentValues()
			db?.let {
				it.execSQL("create table [position](id integer primary key, name text, salary integer);")
				for (i in position_id.indices) {
					cv.clear()
					cv.put("id", position_id[i])
					cv.put("name", position_name[i])
					cv.put("salary", position_salary[i])
					it.insert("[position]", null, cv)
				}
				it.execSQL("create table people(id integer primary key autoincrement, name text, posid integer);")
				for (i in people_name.indices) {
					cv.clear()
					cv.put("name", people_name[i])
					cv.put("posid", people_posid[i])
					it.insert("people", null, cv)
				}
			}
		}
		override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
			Log.d(TAG, "--- onUpgrade database from $oldVersion to $newVersion version ---")
			if (oldVersion == 1 && newVersion == 2) {
				val cv = ContentValues()
				val position_id = arrayOf(1, 2, 3, 4)
				val position_name = arrayOf("Директор", "Программер", "Бухгалтер", "Охранник")
				val position_salary = arrayOf(15000, 13000, 10000, 8000)
				db?.let {
					it.beginTransaction()
					try {
						it.execSQL("create table position(id integer primary key, name text, salary integer);")
						for (i in position_id.indices) {
							cv.clear()
							cv.put("id", position_id[i])
							cv.put("name", position_name[i])
							cv.put("salary", position_salary[i])
							it.insert("[position]", null, cv)
						}
						it.execSQL("alter table people add column posid integer;")

						for (i in position_id.indices) {
							cv.clear()
							cv.put("posid", position_id[i])
							it.update("people", cv, "[position] = ?", arrayOf(position_name[i]))
						}
						it.execSQL("create temporary table people_tmp(" +
							"id integer, name text, [position] text, posid integer);")
						it.execSQL("insert into people_tmp select id, name, [position], posid from people;");
						it.execSQL("drop table people;")

						it.execSQL("create table people(id integer primary key autoincrement, name text, posid integer);")

						it.execSQL("insert into people select id, name, posid from people_tmp;")
						it.execSQL("drop table people_tmp;")

						it.setTransactionSuccessful()
					}
					finally {
						it.endTransaction()
					}
				}
			}
		}
	}
}
