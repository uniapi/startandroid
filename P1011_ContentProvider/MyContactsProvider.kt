/* \uFDFD
 *                           \u0648\u0671\u0630\u0643\u0631 \u0631\u0628\u0651\u0643
 *              \u0643\u062A\u0628\u0647 \u0639\u0628\u062F \u0671\u0644\u0643\u0631\u064A\u0645
 *  \u06F1\u06F4\u06F4\u06F8 \u062C\u0645\u0627\u062F\u0649 \u0627\u0644\u0623\u0648\u0644\u0649 \u06F8
 */
package localhost.idroid.salama

import android.util.Log
import android.content.ContentProvider
import android.content.ContentUris
import android.content.ContentValues
import android.content.Context
import android.content.UriMatcher
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.net.Uri
import android.text.TextUtils

class MyContactsProvider : ContentProvider() {
	companion object {
		const val TAG = "localhost.idroid.salamun"
		const val DB_NAME = "mydb"
		const val DB_VERSION = 1
		const val CONTACT_TABLE = "contacts"
		const val CONTACT_ID = "_id"
		const val CONTACT_NAME = "name"
		const val CONTACT_EMAIL = "email"
		const val DB_CREATE = """
			create table $CONTACT_TABLE (
				$CONTACT_ID integer primary key autoincrement,
				$CONTACT_NAME text,
				$CONTACT_EMAIL text
			);
		"""
		const val AUTHORITY = "localhost.idroid.providers.AddressBook"
		const val CONTACT_PATH = "contacts"
		val CONTACT_CONTENT_URI: Uri = Uri.parse("content://$AUTHORITY/$CONTACT_PATH")
		const val CONTACT_CONTENT_TYPE = "vnd.android.cursor.dir/vnd.$AUTHORITY.$CONTACT_PATH"
		const val CONTACT_CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.$AUTHORITY.$CONTACT_PATH"
		const val URI_CONTACTS = 1
		const val URI_CONTACTS_ID = 2
		private val uriMatcher = UriMatcher(UriMatcher.NO_MATCH).apply {
			addURI(AUTHORITY, CONTACT_PATH, URI_CONTACTS)
			addURI(AUTHORITY, "$CONTACT_PATH/#", URI_CONTACTS_ID)
		}
	}
	private var dbHelper: DBHelper? = null
	private var db: SQLiteDatabase? = null

	override fun onCreate(): Boolean {
		Log.d(TAG, "onCreate")
		dbHelper = DBHelper(context)
		return true
	}
	override fun query(
		uri: Uri, projection: Array<String>?, selection: String?,
		selectionArgs: Array<String>?, sortOrder: String?
	): Cursor? {
		var currentSelection = selection
		var currentSortOrder = sortOrder
		Log.d(TAG, "query, $uri")
		when (uriMatcher.match(uri)) {
			URI_CONTACTS -> {
				Log.d(TAG, "URI_CONTACTS")
				if (TextUtils.isEmpty(currentSortOrder)) {
					currentSortOrder = "$CONTACT_NAME ASC"
				}
			}
			URI_CONTACTS_ID -> {
				val id = uri.lastPathSegment
				Log.d(TAG, "URI_CONTACTS_ID, $id")
				if (TextUtils.isEmpty(currentSelection)) {
					currentSelection = "$CONTACT_ID = $id"
				} else {
					currentSelection = "$currentSelection AND $CONTACT_ID = $id"
				}
			}
			else -> throw IllegalArgumentException("Wrong URI: $uri")
		}
		db = dbHelper?.writableDatabase
		var cursor = db?.query(
			CONTACT_TABLE, projection, currentSelection,
			selectionArgs, null, null, currentSortOrder
		)
		context?.contentResolver?.let { resolver ->
			cursor?.setNotificationUri(resolver, CONTACT_CONTENT_URI)
		}
		return cursor
	}
	override fun insert(uri: Uri, values: ContentValues?): Uri? {
		Log.d(TAG, "insert, $uri")
		if (uriMatcher.match(uri) != URI_CONTACTS) {
			throw IllegalArgumentException("Wrong URI: $uri")
		}
		db = dbHelper?.writableDatabase
		val rowId = db?.insert(CONTACT_TABLE, null, values) ?: -1
		val resultUri = ContentUris.withAppendedId(CONTACT_CONTENT_URI, rowId)
		context?.contentResolver?.notifyChange(resultUri, null)
		return resultUri
	}
	override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int {
		var currentSelection = selection
		Log.d(TAG, "delete, $uri")
		when (uriMatcher.match(uri)) {
			URI_CONTACTS -> Log.d(TAG, "URI_CONTACTS")
			URI_CONTACTS_ID -> {
				val id = uri.lastPathSegment
				Log.d(TAG, "URI_CONTACTS_ID, $id")
				if (TextUtils.isEmpty(currentSelection)) {
					currentSelection = "$CONTACT_ID = $id"
				} else {
					currentSelection = "$currentSelection AND $CONTACT_ID = $id"
				}
			}
			else -> throw IllegalArgumentException("Wrong URI: $uri")
		}
		db = dbHelper?.writableDatabase
		val cnt = db?.delete(CONTACT_TABLE, currentSelection, selectionArgs) ?: 0
		context?.contentResolver?.notifyChange(uri, null)
		return cnt
	}
	override fun update(
		uri: Uri, values: ContentValues?, selection: String?,
		selectionArgs: Array<String>?
	): Int {
		var currentSelection = selection
		Log.d(TAG, "update, $uri")
		when (uriMatcher.match(uri)) {
			URI_CONTACTS -> Log.d(TAG, "URI_CONTACTS")
			URI_CONTACTS_ID -> {
				val id = uri.lastPathSegment
				Log.d(TAG, "URI_CONTACTS_ID, $id")
				if (TextUtils.isEmpty(currentSelection)) {
					currentSelection = "$CONTACT_ID = $id"
				} else {
					currentSelection = "$currentSelection AND $CONTACT_ID = $id"
				}
			}
			else -> throw IllegalArgumentException("Wrong URI: $uri")
		}
		db = dbHelper?.writableDatabase
		val cnt = db?.update(CONTACT_TABLE, values, currentSelection, selectionArgs) ?: 0
		context?.contentResolver?.notifyChange(uri, null)
		return cnt
	}
	override fun getType(uri: Uri): String? {
		Log.d(TAG, "getType, $uri")
		return when (uriMatcher.match(uri)) {
			URI_CONTACTS -> CONTACT_CONTENT_TYPE
			URI_CONTACTS_ID -> CONTACT_CONTENT_ITEM_TYPE
			else -> null
		}
	}
	private inner class DBHelper(context: Context?) : SQLiteOpenHelper(
		context, DB_NAME, null, DB_VERSION
	) {
		override fun onCreate(db: SQLiteDatabase) {
			db.execSQL(DB_CREATE)
			val cv = ContentValues()
			for (i in 1..3) {
				cv.put(CONTACT_NAME, "name $i")
				cv.put(CONTACT_EMAIL, "email $i")
				db.insert(CONTACT_TABLE, null, cv)
			}
		}
		override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {

		}
	}
}
