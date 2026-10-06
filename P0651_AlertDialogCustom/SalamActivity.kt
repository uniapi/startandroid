/* \uFDFD
 *					 \u0648\u0671\u0630\u0643\u0631 \u0631\u0628\u0651\u0643
 *		\u0643\u062A\u0628\u0647 \u0639\u0628\u062F \u0671\u0644\u0643\u0631\u064A\u0645
 *  \u06F1\u06F4\u06F4\u06F8 \u0631\u0628\u064A\u0639 \u0671\u0644\u0622\u062E\u0631 \u06F2\u06F6
 */
package localhost.idroid.salamun

import java.util.Date
import java.text.SimpleDateFormat
import android.util.Log
import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.View
import android.view.ViewGroup.LayoutParams
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Button

class SalamActivity : Activity() {
	private companion object {
		const val TAG = "localhost.idroid.salamun"
		const val DIALOG = 1
	}
	private var btn: Int = 0
	private lateinit var view: LinearLayout
	private lateinit var tvTime: TextView
	private lateinit var tvCount: TextView
	private lateinit var btnAdd: Button
	private var sdf = SimpleDateFormat("HH:mm:ss")
	private var textViews = ArrayList<TextView>(10)

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		val rootLayout = LinearLayout(this).apply {
			layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
			orientation = LinearLayout.VERTICAL
		}
		btnAdd = Button(this).apply {
			id = 1
			layoutParams = LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
			text = getString(R.string.add)
		}
		val btnRemove = Button(this).apply {
			id = 2
			layoutParams = LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
			text = getString(R.string.remove)
		}
		with(rootLayout) {
			addView(btnAdd)
			addView(btnRemove)
		}
		setContentView(rootLayout)
		textViews = ArrayList(10)

		val onClick = View.OnClickListener { view: View? ->
			btn = view?.id ?: 0
			showDialog(DIALOG)
		}
		btnAdd.setOnClickListener(onClick)
		btnRemove.setOnClickListener(onClick)
		// view for Dialog without XML
		view = LinearLayout(this).apply {
			layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
			orientation = LinearLayout.VERTICAL
		}
		tvTime = TextView(this).apply {
			layoutParams = LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
		}
		tvCount = TextView(this).apply {
			layoutParams = LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
		}
		with(view) {
			addView(tvTime)
			addView(tvCount)
		}
	}
// XML version with inflate
/*	override fun onCreateDialog(id: Int): Dialog? {
		val adb = AlertDialog.Builder(this)
		adb.setTitle("Custom dialog")
		view = layoutInflater.inflate(R.layout.dialog, null) as LinearLayout
		adb.setView(view)
		tvCount = view.findViewById(R.id.tvCount) as TextView
		return adb.create()
	}*/
	override fun onCreateDialog(id: Int): Dialog? {
		val adb = AlertDialog.Builder(this)
		adb.setTitle("Custom dialog")
		adb.setView(view)
		return adb.create()
	}
	override fun onPrepareDialog(id: Int, dialog: Dialog) {
		super.onPrepareDialog(id, dialog)
		if (id == DIALOG) {
		//	val tvTime = dialog.window?.findViewById(R.id.tvTime) as? TextView
			tvTime/*?*/.text = sdf.format(Date(System.currentTimeMillis()))
			if (btn == btnAdd.id) {
				val tv = TextView(this).apply { text = "TextView ${textViews.size + 1}" }
				view.addView(tv, LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT))
				textViews.add(tv)
			}
			else {
				if (textViews.size > 0) {
					val tv = textViews[textViews.size - 1]
					view.removeView(tv)
					textViews.remove(tv)
				}
			}
			tvCount.text = "Кол-во TextView = ${textViews.size}"
		}
	}
}
