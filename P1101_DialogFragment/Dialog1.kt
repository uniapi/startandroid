/* \uFDFD
 *                           \u0648\u0671\u0630\u0643\u0631 \u0631\u0628\u0651\u0643
 *              \u0643\u062A\u0628\u0647 \u0639\u0628\u062F \u0671\u0644\u0643\u0631\u064A\u0645
 *  \u06F1\u06F4\u06F4\u06F8 \u062C\u0645\u0627\u062F\u0649 \u0627\u0644\u0623\u0648\u0644\u0649 \u06F9
 */
package localhost.idroid.salamun

import android.util.Log
import android.app.DialogFragment
import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.LayoutInflater
import android.widget.Button

class Dialog1 : DialogFragment(), View.OnClickListener {
	private companion object {
		const val TAG = "localhost.idroid.salamun"
	}
	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
		dialog?.setTitle("Title!")
		val v = inflater.inflate(R.layout.dialog1, null)
		v.findViewById(R.id.btnYes).setOnClickListener(this)
		v.findViewById(R.id.btnNo).setOnClickListener(this)
		v.findViewById(R.id.btnMaybe).setOnClickListener(this)
		return v
	}
	override fun onClick(v: View?) {
		Log.d(TAG, "Dialog 1: ${(v as Button).text}")
		dismiss()
	}
	override fun onDismiss(dialog: DialogInterface?) {
		super.onDismiss(dialog)
		Log.d(TAG, "Dialog 1: onDismiss")
	}
	override fun onCancel(dialog: DialogInterface?) {
		super.onCancel(dialog)
		Log.d(TAG, "Dialog 1: onCancel")
	}
}
