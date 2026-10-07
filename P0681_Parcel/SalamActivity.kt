/* \uFDFD
 *					 \u0648\u0671\u0630\u0643\u0631 \u0631\u0628\u0651\u0643
 *		\u0643\u062A\u0628\u0647 \u0639\u0628\u062F \u0671\u0644\u0643\u0631\u064A\u0645
 *  \u06F1\u06F4\u06F4\u06F8 \u0631\u0628\u064A\u0639 \u0671\u0644\u0622\u062E\u0631 \u06F2\u06F6
 */
package localhost.idroid.salamun

import android.util.Log
import android.app.Activity
import android.os.Bundle
import android.os.Parcel
import android.view.View
import android.view.ViewGroup.LayoutParams
import android.widget.LinearLayout

class SalamActivity : Activity() {
	private companion object {
		const val TAG = "localhost.idroid.salamun"
	}
	private lateinit var p: Parcel
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		val rootLayout = LinearLayout(this).apply {
			layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
			orientation = LinearLayout.VERTICAL
		}
		setContentView(rootLayout)
		writeParcel()
		readParcel()
	}
	private fun writeParcel() {
		p = Parcel.obtain()

		val b: Byte = 1
		val i: Int = 2
		val l: Long = 3L
		val f: Float = 4f
		val d: Double = 5.0
		val s: String = "abc"
		logWriteInfo("before writing");
		p.writeByte(b)
		logWriteInfo("Byte")
		p.writeInt(i)
		logWriteInfo("Int")
		p.writeLong(l)
		logWriteInfo("Long")
		p.writeFloat(f)
		logWriteInfo("Float")
		p.writeDouble(d)
		logWriteInfo("Double")
		p.writeString(s)
		logWriteInfo("String")
	}
	private fun readParcel() {
		logReadInfo("before reading")
		p.setDataPosition(0)
		logReadInfo("Byte = ${p.readByte()}")
		logReadInfo("Int = ${p.readInt()}")
		logReadInfo("Long = ${p.readLong()}")
		logReadInfo("Float = ${p.readFloat()}")
		logReadInfo("Double = ${p.readDouble()}")
		logReadInfo("String = ${p.readString()}")
	}
	private fun logWriteInfo(txt: String) {
		Log.d(TAG, "$txt: dataSize = ${p.dataSize()}")
	}
	private fun logReadInfo(txt: String) {
		Log.d(TAG, "$txt: dataPosition = ${p.dataPosition()}")
	}
}
