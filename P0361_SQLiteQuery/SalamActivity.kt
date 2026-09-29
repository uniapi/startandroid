/* \uFDFD
 *                     \u0648\u0671\u0630\u0643\u0631 \u0631\u0628\u0651\u0643
 *        \u0643\u062A\u0628\u0647 \u0639\u0628\u062F \u0671\u0644\u0643\u0631\u064A\u0645
 *  \u06F1\u06F4\u06F4\u06F8 \u0631\u0628\u064A\u0639 \u0671\u0644\u0622\u062E\u0631 \u06F1\u06F6
 */
package localhost.idroid.salamun

import android.util.Log
import android.content.Context
import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.app.Activity
import android.os.Bundle
import android.view.View
import android.view.Gravity
import android.view.ViewGroup.LayoutParams
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.EditText
import android.widget.Button
import android.widget.RadioGroup
import android.widget.RadioButton
import android.text.InputType

class SalamActivity : Activity() {
    private companion object {
        val TAG = "localhost.idroid.salamun"
    }
	val name = arrayOf("Китай", "США", "Бразилия", "Россия", "Япония",
		"Германия", "Египет", "Италия", "Франция", "Канада")
	val people = arrayOf(1400, 311, 195, 142, 128, 82, 80, 60, 66, 35)
	val region = arrayOf("Азия", "Америка", "Америка", "Европа", "Азия",
		"Европа", "Африка", "Европа", "Европа", "Америка")
	private lateinit var dbHelper: DBHelper
	private lateinit var db: SQLiteDatabase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val rootLayout = LinearLayout(this).apply {
            layoutParams = LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT
            )
            orientation = LinearLayout.VERTICAL
        }
		val llParams = LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT).apply {
			setMargins(0, dpToPx(this@SalamActivity, 5f), 0, 0)
		}
		val etParams = LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, 1.0f)
		val btnParams = LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT).apply {
			setMargins(0, dpToPx(this@SalamActivity, 5f), 0, 0)
		}
		val tvCountries = TextView(this).apply {
			layoutParams = LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT).apply {
				gravity = Gravity.CENTER_HORIZONTAL
				setMargins(0, dpToPx(this@SalamActivity, 5f), 0, dpToPx(this@SalamActivity, 5f))
			}
			text = "Справочник стран"
			textSize = 14f
		}
		val btnAll = Button(this).apply { id = 1; text = "Все записи" }
		val funcLayout = LinearLayout(this)
		val btnFunc = Button(this).apply { id = 2; text = "Функция" }
		val etFunc = EditText(this).apply { requestFocus() }
		with(funcLayout) {
			addView(btnFunc)
			addView(etFunc, etParams)
		}
		val peopleLayout = LinearLayout(this)
		val btnPeople = Button(this).apply { id = 3; text = "Население >" }
		val etPeople = EditText(this).apply { inputType = InputType.TYPE_CLASS_NUMBER }
		with(peopleLayout) {
			addView(btnPeople)
			addView(etPeople, etParams)
		}
		val btnGroup = Button(this).apply { id = 4; text = "Население по региону" }
		val regionPeopleLayout = LinearLayout(this)
		val btnHaving = Button(this).apply { id = 5; text = "Население по региону >" }
		val etRegionPeople = EditText(this).apply { inputType = InputType.TYPE_CLASS_NUMBER }
		with(regionPeopleLayout) {
			addView(btnHaving)
			addView(etRegionPeople, etParams)
		}
		val sortLayout = LinearLayout(this)
		val btnSort = Button(this).apply { id = 6; text ="Сортировка" }
		val rgSort = RadioGroup(this)
		val rbName = RadioButton(this).apply { id = 1; text = "Наименование"; isChecked = true }
		val rbPeople = RadioButton(this).apply { id = 2; text = "Население" }
		val rbRegion = RadioButton(this).apply { id = 3; text = "Регион" }
		val rbParams = RadioGroup.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
		with(rgSort) {
			addView(rbName, rbParams)
			addView(rbPeople, rbParams)
			addView(rbRegion, rbParams)
		}
		with(sortLayout) {
			addView(btnSort)
			addView(rgSort)
		}
		with(rootLayout) {
			addView(tvCountries)
			addView(btnAll, btnParams)
			addView(funcLayout, llParams)
			addView(peopleLayout, llParams)
			addView(btnGroup, btnParams)
			addView(regionPeopleLayout, llParams)
			addView(sortLayout, llParams)
		}
		setContentView(rootLayout)

		val clickListener = View.OnClickListener{ view: View? ->
			val sFunc = etFunc.text?.toString() ?: ""
			val sPeople = etPeople.text?.toString() ?: ""
			val sRegionPeople = etRegionPeople.text?.toString() ?: ""
			var columns: Array<String>? = null
			var selection: String? = null
			var selectionArgs: Array<String>? = null
			var groupBy: String? = null
			var having: String? = null
			var orderBy: String? = null
			var cursor: Cursor? = null
			db = dbHelper.writableDatabase
			when(view?.id) {
				btnAll.id -> {
					Log.d(TAG, "--- Все записи ---")
					cursor = db.query("mytable", null, null, null, null, null, null)
				}
				btnFunc.id -> {
					Log.d(TAG, "--- Функция $sFunc ---")
					columns = arrayOf(sFunc)
					cursor = db.query("mytable", columns, null, null, null, null, null)
				}
				btnPeople.id -> {
					Log.d(TAG, "--- Население больше $sPeople ---")
					selection = "people > ?"
					selectionArgs = arrayOf(sPeople)
					cursor = db.query("mytable", null, selection, selectionArgs, null, null, null)
				}
				btnGroup.id -> {
					Log.d(TAG, "--- Население по региону ---")
					columns = arrayOf("region", "sum(people) as people")
					groupBy = "region"
					cursor = db.query("mytable", columns, null, null, groupBy, null, null)
				}
				btnHaving.id -> {
					Log.d(TAG, "--- Регионы с населением больше $sRegionPeople ---")
					columns = arrayOf("region", "sum(people) as people")
					groupBy = "region"
					having = "sum(people) > $sRegionPeople"
					cursor = db.query("mytable", columns, null, null, groupBy, having, null)
				}
				btnSort.id -> {
					when(rgSort.getCheckedRadioButtonId()) {
						rbName.id -> {
							Log.d(TAG, "--- Сортировка по наименованию ---")
							orderBy = "name"
						}
						rbPeople.id -> {
							Log.d(TAG, "--- Сортировка по населению ---")
							orderBy = "people"
						}
						rbRegion.id -> {
							Log.d(TAG, "--- Сортировка по региону ---")
							orderBy = "region"
						}
					}
					cursor = db.query("mytable", null, null, null, null, null, orderBy)
				}
			}
			cursor?.let { c ->
				if (c.moveToFirst()) {
					var str: String
					do {
						str = ""
						for (cn in c.columnNames) {
							val columnIndex = c.getColumnIndex(cn)
							val value = if (columnIndex != -1) c.getString(columnIndex) else "null"
							str = str.plus("$cn = $value; ")
						}
						Log.d(TAG, str)
					} while (c.moveToNext())
				}
				c.close()
			} ?: Log.d(TAG, "Cursor is null")
			dbHelper.close()
		}
		btnAll.setOnClickListener(clickListener)
		btnFunc.setOnClickListener(clickListener)
		btnPeople.setOnClickListener(clickListener)
		btnSort.setOnClickListener(clickListener)
		btnGroup.setOnClickListener(clickListener)
		btnHaving.setOnClickListener(clickListener)

		dbHelper = DBHelper(this)
		db = dbHelper.writableDatabase
		val c = db.query("mytable", null, null, null, null, null, null)
		if (c.getCount() == 0) {
			with(ContentValues()) {
				for (i in name.indices) {
					put("name", name[i])
					put("people", people[i])
					put("region", region[i])
					Log.d(TAG, "id = ${db.insert("mytable", null, this)}") // this relies on ContentValues
				}
			}
		}
		c.close()
		clickListener.onClick(btnAll)
	}
	class DBHelper(context: Context?) : SQLiteOpenHelper(context, "myDB", null, 1) {
		override fun onCreate(db: SQLiteDatabase?) {
			Log.d(TAG, "--- onCreate database ---")
			db?.execSQL("create table mytable(id integer primary key autoincrement, name text, people integer, region text)")
		}
		override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

		}
	}
}
