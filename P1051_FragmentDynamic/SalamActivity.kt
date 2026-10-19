/* \uFDFD
 *                           \u0648\u0671\u0630\u0643\u0631 \u0631\u0628\u0651\u0643
 *              \u0643\u062A\u0628\u0647 \u0639\u0628\u062F \u0671\u0644\u0643\u0631\u064A\u0645
 *  \u06F1\u06F4\u06F4\u06F8 \u062C\u0645\u0627\u062F\u0649 \u0627\u0644\u0623\u0648\u0644\u0649 \u06F9
 */
package localhost.idroid.salamun

import android.util.Log
import android.app.Activity
import android.app.FragmentTransaction
import android.os.Bundle
import android.view.View
import android.widget.CheckBox

class SalamActivity : Activity() {
	private companion object {
		const val TAG = "localhost.idroid.salamun"
	}
	private lateinit var frag1: Fragment1
	private lateinit var frag2: Fragment2
	private var fTrans: FragmentTransaction? = null
	private lateinit var chbStack: CheckBox

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.main)

		frag1 = Fragment1()
		frag2 = Fragment2()
		chbStack = findViewById(R.id.chbStack) as CheckBox
	}
	fun onClick(v: View?) {
		fTrans = fragmentManager.beginTransaction()
		when(v?.id) {
			R.id.btnAdd -> fTrans?.add(R.id.frgmCont, frag1)
			R.id.btnRemove -> fTrans?.remove(frag1)
			R.id.btnReplace -> fTrans?.replace(R.id.frgmCont, frag2)
			else -> {
			}
		}
		if (chbStack.isChecked) {
			fTrans?.addToBackStack(null)
		}
		fTrans?.commit()
	}
}
