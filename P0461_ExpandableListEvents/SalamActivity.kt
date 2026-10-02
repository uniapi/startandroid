/* \uFDFD
 *                     \u0648\u0671\u0630\u0643\u0631 \u0631\u0628\u0651\u0643
 *        \u0643\u062A\u0628\u0647 \u0639\u0628\u062F \u0671\u0644\u0643\u0631\u064A\u0645
 *  \u06F1\u06F4\u06F4\u06F8 \u0631\u0628\u064A\u0639 \u0671\u0644\u0622\u062E\u0631 \u06F2\u06F2
 */
package localhost.idroid.salamun

import android.util.Log
import android.content.Context
import android.app.Activity
import android.os.Bundle
import android.view.View
import android.view.Gravity
import android.view.ViewGroup.LayoutParams
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.SimpleExpandableListAdapter
import android.widget.ExpandableListView

class AdapterHelper(private val ctx: Context) {
	companion object {
		private const val ATTR_GROUP_NAME = "groupName"
		private const val ATTR_PHONE_NAME = "phoneName"
	}
	val groups = arrayOf("HTC", "Samsung", "LG")
	val phonesHTC = arrayOf("Sensation", "Desire", "Wildfire", "Hero")
	val phonesSams = arrayOf("Galaxy S II", "Galaxy Nexus", "Wave")
	val phonesLG = arrayOf("Optimus", "Optimus Link", "Optimus Black", "Optimus One")
	var internalAdapter: SimpleExpandableListAdapter? = null
		private set
	fun getAdapter(): SimpleExpandableListAdapter {
		val groupData = groups.map { group ->
			mapOf(ATTR_GROUP_NAME to group)
		}
		val groupFrom = arrayOf(ATTR_GROUP_NAME)
		val groupTo = intArrayOf(android.R.id.text1)
		val allPhones = arrayOf(phonesHTC, phonesSams, phonesLG)
		val childData = allPhones.map { phoneArray ->
			phoneArray.map { phone ->
				mapOf(ATTR_PHONE_NAME to phone)
			}
		}
		val childFrom = arrayOf(ATTR_PHONE_NAME)
		val childTo = intArrayOf(android.R.id.text1)
		val newAdapter = SimpleExpandableListAdapter(
			ctx,
			groupData,
			android.R.layout.simple_expandable_list_item_1,
			groupFrom,
			groupTo,
			childData,
			android.R.layout.simple_list_item_1,
			childFrom,
			childTo
		)
		internalAdapter = newAdapter
		return newAdapter
	}
	fun getGroupText(groupPos: Int): String? {
		val groupData = internalAdapter?.getGroup(groupPos) as? Map<String, String>
		return groupData?.get(ATTR_GROUP_NAME)
	}
	fun getChildText(groupPos: Int, childPos: Int): String? {
		val childData = internalAdapter?.getChild(groupPos, childPos) as? Map<String, String>
		return childData?.get(ATTR_PHONE_NAME)
	}
	fun getGroupChildText(groupPos: Int, childPos: Int): String {
		return "${getGroupText(groupPos).orEmpty()} ${getChildText(groupPos, childPos).orEmpty()}".trim()
	}
}

class SalamActivity : Activity() {
    private companion object {
        val TAG = "localhost.idroid.salamun"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val rootLayout = LinearLayout(this).apply {
            layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
            orientation = LinearLayout.VERTICAL
        }
		val tvInfo = TextView(this).apply {
			layoutParams = LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
		}
		val elvMain = ExpandableListView(this).apply {
			layoutParams = LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
		}
		val mainLayout = LinearLayout(this).apply {
			layoutParams = LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
			orientation = LinearLayout.VERTICAL
		}
		with(mainLayout) {
			addView(tvInfo)
			addView(elvMain)
		}
		rootLayout.addView(mainLayout)
		setContentView(rootLayout)

		val ah = AdapterHelper(this)
		elvMain.setAdapter(ah.getAdapter())

		elvMain.setOnChildClickListener { _: ExpandableListView?, _: View?, groupPosition: Int, childPosition: Int, id: Long ->
			Log.d(TAG, "onChildClick groupPosition = $groupPosition, childPosition = $childPosition, id = $id")
			tvInfo.text = ah.getGroupChildText(groupPosition, childPosition)
			false
		}
		elvMain.setOnGroupClickListener { _: ExpandableListView?, _: View?, groupPosition: Int, id: Long ->
			Log.d(TAG, "onGroupClick groupPosition = $groupPosition, id = $id")
			if (groupPosition == 1) true else false
		}
		elvMain.setOnGroupCollapseListener { groupPosition: Int ->
			Log.d(TAG, "onGroupCollapse groupPosition = $groupPosition")
			tvInfo.text = "Свернули ${ah.getGroupText(groupPosition)}"
		}
		elvMain.setOnGroupExpandListener { groupPosition: Int ->
			Log.d(TAG, "onGroupExpand groupPosition = $groupPosition")
			tvInfo.text = "Развернули ${ah.getGroupText(groupPosition)}"
		}
		elvMain.expandGroup(2)
	}
}
