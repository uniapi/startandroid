/* \uFDFD
 *                           \u0648\u0671\u0630\u0643\u0631 \u0631\u0628\u0651\u0643
 *              \u0643\u062A\u0628\u0647 \u0639\u0628\u062F \u0671\u0644\u0643\u0631\u064A\u0645
 *  \u06F1\u06F4\u06F4\u06F8 \u062C\u0645\u0627\u062F\u0649 \u0627\u0644\u0623\u0648\u0644\u0649 \u06F1\u06F1
 */
package localhost.idroid.salamun

import android.util.Log
import android.app.Activity
import android.os.Bundle
import android.support.v4.app.ListFragment
import android.view.View
import android.widget.ArrayAdapter
import android.widget.ListView

class TitlesFragment : ListFragment() {
	interface OnItemClickListener {
		fun itemClick(position: Int)
	}
	private var listener: OnItemClickListener? = null

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		val adapter = ArrayAdapter<String>(
			getActivity(),
			android.R.layout.simple_list_item_1,
			resources.getStringArray(R.array.headers)
		)
		listAdapter = adapter
	}
	override fun onAttach(activity: Activity) {
		super.onAttach(activity)
		listener = activity as OnItemClickListener
	}
	override fun onListItemClick(list: ListView, view: View, position: Int, id: Long) {
		super.onListItemClick(list, view, position, id)
		listener?.itemClick(position)
	}
}
