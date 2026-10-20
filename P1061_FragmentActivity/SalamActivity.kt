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
import android.widget.TextView

class SalamActivity : Activity(), Fragment2.OnSomeEventListener {
	private companion object {
		const val TAG = "localhost.idroid.salamun"
	}
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.main)

		val frag2 = Fragment2()
		val ft = fragmentManager.beginTransaction()
		ft.add(R.id.fragment2, frag2)
		ft.commit()
	}
	override fun someEvent(s: String) {
		val frag1 = fragmentManager.findFragmentById(R.id.fragment1)
		(frag1.view.findViewById(R.id.textView) as TextView).text = "Text from Fragment 2: $s"
	}
	fun onClick(view: View?) {
		val frag1 = fragmentManager.findFragmentById(R.id.fragment1)
		(frag1.view.findViewById(R.id.textView) as TextView).text = "Access to Fragment1 from Activity"
		val frag2 = fragmentManager.findFragmentById(R.id.fragment2)
		(frag2.view.findViewById(R.id.textView) as TextView).text = "Access to Fragment2 from Activity"
	}
}
