/* \uFDFD
 *                     \u0648\u0671\u0630\u0643\u0631 \u0631\u0628\u0651\u0643
 *        \u0643\u062A\u0628\u0647 \u0639\u0628\u062F \u0671\u0644\u0643\u0631\u064A\u0645
 *  \u06F1\u06F4\u06F4\u06F8 \u0631\u0628\u064A\u0639 \u0671\u0644\u0622\u062E\u0631 \u06F2\u06F5
 */
package localhost.idroid.salamun

import android.util.Log
import android.app.Activity
import android.app.Dialog
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.DialogInterface.OnClickListener
import android.os.Bundle
import android.view.View
import android.view.ViewGroup.LayoutParams
import android.widget.LinearLayout
import android.widget.Button
import android.widget.Toast

class SalamActivity : Activity() {
    private companion object {
    	const val TAG = "localhost.idroid.salamun"
		const val DIALOG_EXIT = 1
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val rootLayout = LinearLayout(this).apply {
            layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
            orientation = LinearLayout.VERTICAL
        }
		val btnExit = Button(this).apply {
			layoutParams = LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
			text = getString(R.string.exit)
			setOnClickListener {
				showDialog(DIALOG_EXIT)
			}
		}
		with(rootLayout) {
			addView(btnExit)
		}
		setContentView(rootLayout)
	}
	override fun onCreateDialog(id: Int): Dialog? {
		when (id) {
			DIALOG_EXIT -> {
				val adb = AlertDialog.Builder(this).apply {
					setTitle(R.string.exit)
					setMessage(R.string.save_data)
					setIcon(android.R.drawable.ic_dialog_info)
					setPositiveButton(R.string.yes, myClickListener)
					setNegativeButton(R.string.no, myClickListener)
					setNeutralButton(R.string.cancel, myClickListener)
					setCancelable(false)
				}
				return adb.create()
			}
			else -> return super.onCreateDialog(id)
		}
	}
	override fun onBackPressed() {
		showDialog(DIALOG_EXIT)
	}
	private val myClickListener = { dialog: DialogInterface, which: Int ->
		when (which) {
			DialogInterface.BUTTON_POSITIVE -> {
				saveData()
				finish()
			}
			DialogInterface.BUTTON_NEGATIVE -> {
				finish()
			}
			DialogInterface.BUTTON_NEUTRAL -> {
			}
		}
	}
	private fun saveData() {
		Toast.makeText(this, R.string.saved, Toast.LENGTH_SHORT).show()
	}
}
