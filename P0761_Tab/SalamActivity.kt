/* \uFDFD
 *                     \u0648\u0671\u0630\u0643\u0631 \u0631\u0628\u0651\u0643
 *        \u0643\u062A\u0628\u0647 \u0639\u0628\u062F \u0671\u0644\u0643\u0631\u064A\u0645
 *  \u06F1\u06F4\u06F4\u06F8 \u0631\u0628\u064A\u0639 \u0671\u0644\u0622\u062E\u0631 \u06F2\u06F7
 */
package localhost.idroid.salamun

import android.util.Log
import android.app.Activity
import android.os.Bundle
import android.view.View
import android.view.Gravity
import android.view.ViewGroup.LayoutParams
import android.widget.LinearLayout
import android.widget.FrameLayout
import android.widget.TabHost
import android.widget.TabHost.OnTabChangeListener
import android.widget.TabWidget
import android.widget.TextView
import android.widget.Button
import android.widget.Toast

class SalamActivity : Activity() {
	private companion object {
		const val TAG = "localhost.idroid.salamun"
	}
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
/*		val rootLayout = LinearLayout(this).apply {
			layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
			orientation = LinearLayout.VERTICAL
		}
		val tvParams = FrameLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
		val tabHost = TabHost(this).apply {
			id = android.R.id.tabhost		// necessary system id!
			layoutParams = LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
		}
		val tabLayout = LinearLayout(this).apply {
			layoutParams = FrameLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
			orientation = LinearLayout.VERTICAL
		}
		val twTabs = TabWidget(this).apply {
			id = android.R.id.tabs			// necessary system id!
			layoutParams = LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
		}
		val tabContent = FrameLayout(this).apply {
			id = android.R.id.tabcontent	// necessary system id!
			layoutParams = LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
		}
		val tvTab1 = TextView(this).apply { setText(R.string.text_tab1) }
		val tvTab2 = TextView(this).apply { setText(R.string.text_tab2) }
		val tvTab3 = TextView(this).apply { setText(R.string.text_tab3) }
		with(tabContent) {
			addView(tvTab1, tvParams)
			addView(tvTab2, tvParams)
			addView(tvTab3, tvParams)
		}
		with(tabLayout) {
			addView(twTabs)
			addView(tabContent)
		}
		tabHost.addView(tabLayout)
		rootLayout.addView(tabHost)
		setContentView(rootLayout)
*/		setContentView(R.layout.main)
		val tabHost = findViewById(android.R.id.tabhost) as TabHost
		tabHost.setup()

		val tabSpec1 = tabHost.newTabSpec("tag1")
			.setIndicator("Вкладка 1")
			.setContent(R.id.tvTab1)
		tabHost.addTab(tabSpec1)
		val tabSpec2 = tabHost.newTabSpec("tag2")
			.setIndicator("Вкладка 2", getResources().getDrawable(R.drawable.tab_icon_selector))
			.setContent(R.id.tvTab2)
		tabHost.addTab(tabSpec2)
		val view = getLayoutInflater().inflate(R.layout.tab_header, null)
		val tabSpec3 = tabHost.newTabSpec("tag3")
			.setIndicator(view)
			.setContent(R.id.tvTab3)
		tabHost.addTab(tabSpec3)

		tabHost.setCurrentTabByTag("tag2")

		tabHost.setOnTabChangedListener { tabId: String ->
			Toast.makeText(getBaseContext(), "tabId = $tabId", Toast.LENGTH_SHORT).show()
		}
	}
}
