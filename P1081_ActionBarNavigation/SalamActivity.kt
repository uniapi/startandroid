/* \uFDFD
 *                           \u0648\u0671\u0630\u0643\u0631 \u0631\u0628\u0651\u0643
 *              \u0643\u062A\u0628\u0647 \u0639\u0628\u062F \u0671\u0644\u0643\u0631\u064A\u0645
 *  \u06F1\u06F4\u06F4\u06F8 \u062C\u0645\u0627\u062F\u0649 \u0627\u0644\u0623\u0648\u0644\u0649 \u06F9
 */
package localhost.idroid.salamun

import android.util.Log
import android.app.ActionBar
import android.app.ActionBar.Tab
import android.app.Activity
import android.app.FragmentTransaction
import android.os.Bundle
import android.view.View
import android.view.Menu
import android.widget.LinearLayout
import android.widget.ArrayAdapter

/*class SalamActivity : Activity(), ActionBar.TabListener {
	private companion object {
		const val TAG = "localhost.idroid.salamun"
	}
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		val llRoot = LinearLayout(this)
		setContentView(llRoot)

		val bar = actionBar.apply { setNavigationMode(ActionBar.NAVIGATION_MODE_TABS) }
		val tab1 = bar.newTab().apply { text = "tab1"; setTabListener(this@SalamActivity) }
		bar.addTab(tab1)
		val tab2 = bar.newTab().apply { text = "tab2"; setTabListener(this@SalamActivity) }
		bar.addTab(tab2)
	}
	override fun onTabReselected(tab: Tab?, ft: FragmentTransaction?) {
		Log.d(TAG, "reselected tab: ${tab?.text}")
	}
	override fun onTabSelected(tab: Tab?, ft: FragmentTransaction?) {
		Log.d(TAG, "selected tab: ${tab?.text}")
	}
	override fun onTabUnselected(tab: Tab?, ft: FragmentTransaction?) {
		Log.d(TAG, "unselected tab: ${tab?.text}")
	}
}*/
class SalamActivity : Activity(), ActionBar.OnNavigationListener {
	private companion object {
		const val TAG = "localhost.idroid.salamun"
	}
	private val data = arrayOf("one", "two", "three")

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		val llRoot = LinearLayout(this)
		setContentView(llRoot)

		val bar = actionBar.apply { setNavigationMode(ActionBar.NAVIGATION_MODE_LIST) }
		val adapter = ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, data)
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
		bar.setListNavigationCallbacks(adapter, this)
	}
	override fun onNavigationItemSelected(itemPosition: Int, itemId: Long): Boolean {
		Log.d(TAG, "selected: position = $itemPosition, id = $itemId, ${data[itemPosition]}")
		return false
	}
}
