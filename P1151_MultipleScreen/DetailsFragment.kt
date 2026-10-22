/* \uFDFD
 *                           \u0648\u0671\u0630\u0643\u0631 \u0631\u0628\u0651\u0643
 *              \u0643\u062A\u0628\u0647 \u0639\u0628\u062F \u0671\u0644\u0643\u0631\u064A\u0645
 *  \u06F1\u06F4\u06F4\u06F8 \u062C\u0645\u0627\u062F\u0649 \u0627\u0644\u0623\u0648\u0644\u0649 \u06F1\u06F1
 */
package localhost.idroid.salamun

import android.util.Log
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView

class DetailsFragment : Fragment() {
	companion object {
		@JvmStatic
		fun newInstance(pos: Int): DetailsFragment {
			val details = DetailsFragment()
			var args = Bundle().apply {
				putInt("position", pos)
			}
			details.arguments = args
			return details
		}
	}
	fun getPosition(): Int {
		return arguments?.getInt("position", 0) ?: 0
	}
	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
		val view = inflater.inflate(R.layout.details, container, false)
		val tv = view.findViewById(R.id.tvText) as TextView
		val contentArray = resources.getStringArray(R.array.content)
		tv.text = contentArray.getOrNull(getPosition()) ?: ""
		return view
	}
}
