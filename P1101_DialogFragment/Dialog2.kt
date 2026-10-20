/* \uFDFD
 *                           \u0648\u0671\u0630\u0643\u0631 \u0631\u0628\u0651\u0643
 *              \u0643\u062A\u0628\u0647 \u0639\u0628\u062F \u0671\u0644\u0643\u0631\u064A\u0645
 *  \u06F1\u06F4\u06F4\u06F8 \u062C\u0645\u0627\u062F\u0649 \u0627\u0644\u0623\u0648\u0644\u0649 \u06F9
 */
package localhost.idroid.salamun

import android.util.Log
import android.app.AlertDialog
import android.app.Dialog
import android.app.DialogFragment
import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.LayoutInflater
import android.widget.Button

class Dialog2 : DialogFragment(), DialogInterface.OnClickListener {
	private companion object {
		const val TAG = "localhost.idroid.salamun"
	}
	override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
		val context = activity ?: throw IllegalStateException("Activity cannot be null")
		val adb = AlertDialog.Builder(context)
			.setTitle("Title!")
			.setPositiveButton(R.string.yes, this)
			.setNegativeButton(R.string.no, this)
			.setNeutralButton(R.string.maybe, this)
			.setMessage(R.string.message_text)
		return adb.create()
	}
	override fun onClick(dialog: DialogInterface?, which: Int) {
		val i = when (which) {
			Dialog.BUTTON_POSITIVE -> R.string.yes
			Dialog.BUTTON_NEGATIVE -> R.string.no
			Dialog.BUTTON_NEUTRAL -> R.string.maybe
			else -> 0
		}
		if (i > 0) {
			val message = activity?.resources?.getString(i)
			Log.d(TAG, "Dialog 2: $message")
		}
	}
	override fun onDismiss(dialog: DialogInterface?) {
		super.onDismiss(dialog)
		Log.d(TAG, "Dialog 2: onDismiss")
	}
	override fun onCancel(dialog: DialogInterface?) {
		super.onCancel(dialog)
		Log.d(TAG, "Dialog 2: onCancel")
	}
}
