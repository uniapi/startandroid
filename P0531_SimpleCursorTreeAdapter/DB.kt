/* \uFDFD
 *                     \u0648\u0671\u0630\u0643\u0631 \u0631\u0628\u0651\u0643
 *        \u0643\u062A\u0628\u0647 \u0639\u0628\u062F \u0671\u0644\u0643\u0631\u064A\u0645
 *  \u06F1\u06F4\u06F4\u06F8 \u0631\u0628\u064A\u0639 \u0671\u0644\u0622\u062E\u0631 \u06F2\u06F3
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
		private const val COMPANY_TABLE = "company"
		const val COMPANY_COLUMN_ID = "_id"
		const val COMPANY_COLUMN_NAME = "name"
		private const val COMPANY_TABLE_CREATE = """
			CREATE TABLE $COMPANY_TABLE (
				$COMPANY_COLUMN_ID INTEGER PRIMARY KEY,
				$COMPANY_COLUMN_NAME TEXT
			);
		"""
		private const val PHONE_TABLE = "phone"
		const val PHONE_COLUMN_ID = "_id"
		const val PHONE_COLUMN_NAME = "name"
		const val PHONE_COLUMN_COMPANY = "company"
		private const val PHONE_TABLE_CREATE = """
			CREATE TABLE $PHONE_TABLE (
				$PHONE_COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
				$PHONE_COLUMN_NAME TEXT,
				$PHONE_COLUMN_COMPANY INTEGER
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
	fun getCompanyData(): Cursor? {
		return mDB?.query(COMPANY_TABLE, null, null, null, null, null, null)
	}
	fun getPhoneData(companyID: Long): Cursor? {
		return mDB?.query(PHONE_TABLE, null, "$PHONE_COLUMN_COMPANY = $companyID", null, null, null, null)
	}
	private inner class DBHelper(
		context: Context,
		name: String,
		factory: CursorFactory?,
		version: Int
	) : SQLiteOpenHelper(context, name, factory, version) {
		override fun onCreate(db: SQLiteDatabase?) {
			db?.let {
				val cv = ContentValues()
				val companies = arrayOf("HTC", "Samsung", "LG")
				it.execSQL(COMPANY_TABLE_CREATE)
				companies.forEachIndexed { i, companyName ->
					with(cv) {
						put(COMPANY_COLUMN_ID, i + 1)
						put(COMPANY_COLUMN_NAME, companyName)
					}
					it.insert(COMPANY_TABLE, null, cv)
				}
				val phonesHTC = arrayOf("Sensation", "Desire", "Wildfire", "Hero")
				val phonesSams = arrayOf("Galaxy S II", "Galaxy Nexus", "Wave")
				val phonesLG = arrayOf("Optimus", "Optimus Link", "Optimus Black", "Optimus One")
				it.execSQL(PHONE_TABLE_CREATE)
				cv.clear()
				phonesHTC.forEach { phone ->
					cv.apply {
						put(PHONE_COLUMN_COMPANY, 1)
						put(PHONE_COLUMN_NAME, phone)
					}
					it.insert(PHONE_TABLE, null, cv)
				}
				phonesSams.forEach { phone ->
					cv.apply {
						put(PHONE_COLUMN_COMPANY, 2)
						put(PHONE_COLUMN_NAME, phone)
					}
					it.insert(PHONE_TABLE, null, cv)
				}
				phonesLG.forEach { phone ->
					cv.apply {
						put(PHONE_COLUMN_COMPANY, 3)
						put(PHONE_COLUMN_NAME, phone)
					}
					it.insert(PHONE_TABLE, null, cv)
				}
			}
		}
		override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

		}
	}
}
