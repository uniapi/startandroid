/* \uFDFD
 *                           \u0648\u0671\u0630\u0643\u0631 \u0631\u0628\u0651\u0643
 *              \u0643\u062A\u0628\u0647 \u0639\u0628\u062F \u0671\u0644\u0643\u0631\u064A\u0645
 *  \u06F1\u06F4\u06F4\u06F8 \u062C\u0645\u0627\u062F\u0649 \u0627\u0644\u0623\u0648\u0644\u0649 \u06F9
 */
package localhost.idroid.salamun

import android.util.Log
import android.app.Activity
import android.app.DialogFragment
import android.os.Bundle
import android.view.View

class SalamActivity : Activity(), View.OnClickListener {
	private companion object {
		const val TAG = "localhost.idroid.salamun"
	}
	private lateinit var dlg1: DialogFragment
	private lateinit var dlg2: DialogFragment

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.main)

		dlg1 = Dialog1()
		dlg2 = Dialog2()
	}
	override fun onClick(v: View?) {
		when (v?.id) {
			R.id.btnDlg1 -> dlg1.show(fragmentManager, "dlg1")
			R.id.btnDlg2 -> dlg2.show(fragmentManager, "dlg2")
		}
	}
}
