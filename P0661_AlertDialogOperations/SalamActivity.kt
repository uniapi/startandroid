/* \uFDFD
 *					 \u0648\u0671\u0630\u0643\u0631 \u0631\u0628\u0651\u0643
 *		\u0643\u062A\u0628\u0647 \u0639\u0628\u062F \u0671\u0644\u0643\u0631\u064A\u0645
 *  \u06F1\u06F4\u06F4\u06F8 \u0631\u0628\u064A\u0639 \u0671\u0644\u0622\u062E\u0631 \u06F2\u06F6
 */
package localhost.idroid.salamun

import android.util.Log
import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.content.DialogInterface.OnCancelListener
import android.content.DialogInterface.OnDismissListener
import android.content.DialogInterface.OnShowListener
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.Gravity
import android.view.ViewGroup.LayoutParams
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Button

class SalamActivity : Activity() {
	private companion object {
		const val TAG = "localhost.idroid.salamun"
		const val DIALOG = 1
	}
	private lateinit var dialog: Dialog

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		val rootLayout = LinearLayout(this).apply {
			layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
			orientation = LinearLayout.VERTICAL
		}
		val btnClick = Button(this).apply {
			layoutParams = LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
			text = getString(R.string.dialog)
			setOnClickListener { _: View? ->
				showDialog(DIALOG)
				val h = Handler(Looper.getMainLooper())
				h.postDelayed({
					method1()
				}, 2000)
				h.postDelayed({
					method2()
				}, 4000)
			}
		}
		with(rootLayout) {
			addView(btnClick)
		}
		setContentView(rootLayout)
	}
	override fun onCreateDialog(id: Int): Dialog? {
		if (id == DIALOG) {
			Log.d(TAG, "Create")
			val adb = AlertDialog.Builder(this).apply {
				setTitle("Title")
				setMessage("Message")
				setPositiveButton("OK", null)
			}
			dialog = adb.create().apply {
				setOnShowListener { _: DialogInterface ->
					Log.d(TAG, "Show")
				}
				setOnCancelListener { _: DialogInterface ->
					Log.d(TAG, "Cancel")
				}
				setOnDismissListener { _: DialogInterface ->
					Log.d(TAG, "Dismiss")
				}
			}
			return dialog
		}
		return super.onCreateDialog(id)
	}
	private fun method1() {
	//	dialog.dismiss()
	//	dialog.cancel()
	//	dialog.hide()
	//	dismissDialog(DIALOG)
		removeDialog(DIALOG)
	}
	private fun method2() {
		showDialog(DIALOG)
	}
}
