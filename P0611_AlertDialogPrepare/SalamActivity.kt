/* \uFDFD
 *                     \u0648\u0671\u0630\u0643\u0631 \u0631\u0628\u0651\u0643
 *        \u0643\u062A\u0628\u0647 \u0639\u0628\u062F \u0671\u0644\u0643\u0631\u064A\u0645
 *  \u06F1\u06F4\u06F4\u06F8 \u0631\u0628\u064A\u0639 \u0671\u0644\u0622\u062E\u0631 \u06F2\u06F5
 */
package localhost.idroid.salamun

import java.text.SimpleDateFormat
import java.util.Date
import android.util.Log
import android.app.Activity
import android.app.Dialog
import android.app.AlertDialog
import android.os.Bundle
import android.view.View
import android.view.ViewGroup.LayoutParams
import android.widget.LinearLayout
import android.widget.Button
import android.widget.Toast

class SalamActivity : Activity() {
    private companion object {
    	const val TAG = "localhost.idroid.salamun"
		const val DIALOG = 1
    }
	val sdf = SimpleDateFormat("HH:mm:ss")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val rootLayout = LinearLayout(this).apply {
            layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
            orientation = LinearLayout.VERTICAL
        }
		val btnClick = Button(this).apply {
			layoutParams = LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
			text = getString(R.string.hello)
			setOnClickListener {
				showDialog(DIALOG)
			}
		}
		with(rootLayout) {
			addView(btnClick)
		}
		setContentView(rootLayout)
	}
	override fun onCreateDialog(id: Int): Dialog? {
		Log.d(TAG, "onCreateDialog")
		when (id) {
			DIALOG -> {
				val adb = AlertDialog.Builder(this).apply {
					setTitle("Current time")
					setMessage(sdf.format(Date(System.currentTimeMillis())))
					//setCancelable(false)
				}
				return adb.create()
			}
			else -> return super.onCreateDialog(id)
		}
	}
	override fun onPrepareDialog(id: Int, dialog: Dialog) {
		super.onPrepareDialog(id, dialog)
		Log.d(TAG, "onPrepareDialog")
		when (id) {
			DIALOG -> (dialog as AlertDialog).setMessage(sdf.format(Date()))
		}
	}
}
