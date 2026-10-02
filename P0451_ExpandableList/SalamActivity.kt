/* \uFDFD
 *                     \u0648\u0671\u0630\u0643\u0631 \u0631\u0628\u0651\u0643
 *        \u0643\u062A\u0628\u0647 \u0639\u0628\u062F \u0671\u0644\u0643\u0631\u064A\u0645
 *  \u06F1\u06F4\u06F4\u06F8 \u0631\u0628\u064A\u0639 \u0671\u0644\u0622\u062E\u0631 \u06F2\u06F1
 */
package localhost.idroid.salamun

import android.util.Log
import android.app.Activity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup.LayoutParams
import android.widget.LinearLayout
import android.widget.ExpandableListView
import android.widget.SimpleExpandableListAdapter

class SalamActivity : Activity() {
    private companion object {
        val TAG = "localhost.idroid.salamun"
    }
	private val groups = arrayOf("HTC", "Samsung", "LG")
	private val phonesHTC = arrayOf("Sensation", "Desire", "Wildfire", "Hero")
	private val phonesSams = arrayOf("Galaxy S II", "Galaxy Nexus", "Wave")
	private val phonesLG = arrayOf("Optimus", "Optimus Link", "Optimus Black", "Optimus One")

//	private lateinit var groupData: ArrayList<Map<String, String>>
//	private lateinit var childDataItem: ArrayList<Map<String, String>>
//	private lateinit var childData : ArrayList<ArrayList<Map<String, String>>>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val rootLayout = LinearLayout(this).apply {
            layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
            orientation = LinearLayout.VERTICAL
        }
		val elvMain = ExpandableListView(this).apply {
			layoutParams = LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
		}
		with(rootLayout) {
			addView(elvMain)
		}
		setContentView(rootLayout)
// Direct Translation from Java:
/*		groupData = ArrayList()
		for (group in groups) {
			val groupMap = HashMap<String, String>()
			groupMap["groupName"] = group
			groupData.add(groupMap)
		}
		val groupFrom = arrayOf("groupName")
		val groupTo = intArrayOf(android.R.id.text1)
		childData = ArrayList()

		childDataItem = ArrayList()
		for (phone in phonesHTC) {
			val phoneMap = HashMap<String, String>()
			phoneMap["phoneName"] = phone
			childDataItem.add(phoneMap)
		}
		childData.add(childDataItem)

		childDataItem = ArrayList()
		for (phone in phonesSams) {
			val phoneMap = HashMap<String, String>()
			phoneMap["phoneName"] = phone
			childDataItem.add(phoneMap)
		}
		childData.add(childDataItem)

		childDataItem = ArrayList()
		for (phone in phonesLG) {
			val phoneMap = HashMap<String, String>()
			phoneMap["phoneName"] = phone
			childDataItem.add(phoneMap)
		}
		childData.add(childDataItem)

		val childFrom = arrayOf("phoneName")
		val childTo = intArrayOf(android.R.id.text1)
*/
// Idiomatic Kotlin
		val groupData = groups.map { group ->
			mapOf("groupName" to group)
		}
		val groupFrom = arrayOf("groupName")
		val groupTo = intArrayOf(android.R.id.text1)
		val allPhones = arrayOf(phonesHTC, phonesSams, phonesLG)
		val childData = allPhones.map { phoneArray ->
			phoneArray.map { phone -> mapOf("phoneName" to phone) }
		}
		val childFrom = arrayOf("phoneName")
		val childTo = intArrayOf(android.R.id.text1)


		val adapter = SimpleExpandableListAdapter(
			this,
			groupData,
			android.R.layout.simple_expandable_list_item_1,
			groupFrom,
			groupTo,
			childData,
			android.R.layout.simple_list_item_1,
			childFrom,
			childTo
		)
		elvMain.setAdapter(adapter)
	}
}
