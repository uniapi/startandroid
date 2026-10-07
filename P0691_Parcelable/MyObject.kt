/* \uFDFD
 *					 \u0648\u0671\u0630\u0643\u0631 \u0631\u0628\u0651\u0643
 *		\u0643\u062A\u0628\u0647 \u0639\u0628\u062F \u0671\u0644\u0643\u0631\u064A\u0645
 *  \u06F1\u06F4\u06F4\u06F8 \u0631\u0628\u064A\u0639 \u0671\u0644\u0622\u062E\u0631 \u06F2\u06F7
 */
package localhost.idroid.salamun

import android.util.Log
import android.app.Activity
import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import android.view.View
import android.view.Gravity
import android.view.ViewGroup.LayoutParams
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Button

class MyObject(val s: String?, val i: Int) : Parcelable {
	init {
		Log.d(TAG, "MyObject(s: String, i: Int)")
	}
	constructor(parcel: Parcel) : this(
		s = parcel.readString(),
		i = parcel.readInt()
	) {
		Log.d(TAG, "MyObject(parcel: Parcel)")
	}
	override fun writeToParcel(parcel: Parcel?, flags: Int) {
		Log.d(TAG, "writeToParcel")
		parcel?.writeString(s)
		parcel?.writeInt(i)
	}
	override fun describeContents(): Int = 0
	companion object {
		private const val TAG = "localhost.idroid.salamun"
		@JvmField
		val CREATOR = object : Parcelable.Creator<MyObject> {
			override fun createFromParcel(parcel: Parcel): MyObject {
				Log.d(TAG, "createFromParcel")
				return MyObject(parcel)
			}
			override fun newArray(size: Int): Array<MyObject?> {
				return arrayOfNulls(size)
			}
		}
	}
}
