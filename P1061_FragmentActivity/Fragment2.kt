/* \uFDFD
 *                           \u0648\u0671\u0630\u0643\u0631 \u0631\u0628\u0651\u0643
 *              \u0643\u062A\u0628\u0647 \u0639\u0628\u062F \u0671\u0644\u0643\u0631\u064A\u0645
 *  \u06F1\u06F4\u06F4\u06F8 \u062C\u0645\u0627\u062F\u0649 \u0627\u0644\u0623\u0648\u0644\u0649 \u06F9
 */
package localhost.idroid.salamun

import android.util.Log
import android.app.Activity
import android.app.Fragment
import android.os.Bundle
import android.view.View
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Button

class Fragment2 : Fragment() {
	interface OnSomeEventListener {
		fun someEvent(s: String)
	}
	private companion object {
		const val TAG = "localhost.idroid.salamun"
	}
	private lateinit var someEventListener: OnSomeEventListener
	override fun onAttach(activity: Activity) {
		super.onAttach(activity)
		try {
			someEventListener = activity as OnSomeEventListener
		} catch (e: ClassCastException) {
			throw ClassCastException("$activity must implement OnSomeEventListener")
		}
	}
	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
		val view = inflater.inflate(R.layout.fragment2, null)
		val button = view.findViewById(R.id.button)
		button.setOnClickListener { _: View? ->
			Log.d(TAG, "Button click in Fragment2")
			someEventListener.someEvent("Test text to Fragment1")
		}
		return view
	}
}
