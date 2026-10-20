/* \uFDFD
 *                           \u0648\u0671\u0630\u0643\u0631 \u0631\u0628\u0651\u0643
 *              \u0643\u062A\u0628\u0647 \u0639\u0628\u062F \u0671\u0644\u0643\u0631\u064A\u0645
 *  \u06F1\u06F4\u06F4\u06F8 \u062C\u0645\u0627\u062F\u0649 \u0627\u0644\u0623\u0648\u0644\u0649 \u06F9
 */
package localhost.idroid.salamun

import android.util.Log
import android.app.ListFragment
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.LayoutInflater
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast

class MainList : ListFragment() {
	private val data = arrayOf("one", "two", "three", "four")

	override fun onActivityCreated(savedInstanceState: Bundle?) {
		super.onActivityCreated(savedInstanceState)
		val adapter = ArrayAdapter<String>(activity, android.R.layout.simple_list_item_1, data)
		listAdapter = adapter
	}
	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
		return inflater.inflate(R.layout.fragment, null)
	}
	override fun onListItemClick(list: ListView?, view: View?, position: Int, id: Long) {
		super.onListItemClick(list, view, position, id)
		activity?.let { context ->
			Toast.makeText(context, "position = $position", Toast.LENGTH_SHORT).show()
		}
	}
}
