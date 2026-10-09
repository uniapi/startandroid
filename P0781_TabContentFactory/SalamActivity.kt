/* \uFDFD
 *                     \u0648\u0671\u0630\u0643\u0631 \u0631\u0628\u0651\u0643
 *        \u0643\u062A\u0628\u0647 \u0639\u0628\u062F \u0671\u0644\u0643\u0631\u064A\u0645
 *  \u06F1\u06F4\u06F4\u06F8 \u0631\u0628\u064A\u0639 \u0671\u0644\u0622\u062E\u0631 \u06F2\u06F8
 */
package localhost.idroid.salamun

import android.util.Log
import android.content.Intent
import android.app.TabActivity
import android.os.Bundle
import android.view.View
import android.view.Gravity
import android.view.ViewGroup.LayoutParams
import android.widget.TabHost
import android.widget.TabWidget
import android.widget.LinearLayout
import android.widget.FrameLayout
import android.widget.TextView

class SalamActivity : TabActivity() {
	private companion object {
		const val TAG = "localhost.idroid.salamun"
		const val TABS_TAG_1 = "Tag 1"
		const val TABS_TAG_2 = "Tag 2"
	}
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
/*		val rootLayout = LinearLayout(this).apply {
			layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
			orientation = LinearLayout.VERTICAL
		}
		val tabHost = TabHost(this).apply {
			id = android.R.id.tabhost		// necessary system id!
			layoutParams = LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
		}
		val innerLayout = LinearLayout(this).apply {
			layoutParams = FrameLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
			orientation = LinearLayout.VERTICAL
		}
		val tabWidget = TabWidget(this).apply {
			id = android.R.id.tabs			// necessary system id!
			layoutParams = LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
		}
		val tabContent = FrameLayout(this).apply {
			id = android.R.id.tabcontent	// necessary system id!
			layoutParams = LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
		}
		with(innerLayout) {
			addView(tabWidget)
			addView(tabContent)
		}
		tabHost.addView(innerLayout)
		rootLayout.addView(tabHost)
		setContentView(rootLayout)
		tabHost.setup(this.localActivityManager)
*/		setContentView(R.layout.main)
		val tabHost = getTabHost()	// initializing, so no need to call setup()
		val tabSpec1 = tabHost.newTabSpec(TABS_TAG_1)
			.setContent(tabFactory)
			.setIndicator("Вкладка 1")
		tabHost.addTab(tabSpec1)
		val tabSpec2 = tabHost.newTabSpec(TABS_TAG_2)
			.setContent(tabFactory)
			.setIndicator("Вкладка 2")
		tabHost.addTab(tabSpec2)
	}
	private val tabFactory = TabHost.TabContentFactory { tag ->
		when (tag) {
			TABS_TAG_1 -> layoutInflater.inflate(R.layout.tab, null)
			TABS_TAG_2 -> TextView(this@SalamActivity).apply { text = "Это создано вручную" }
			else -> null
		}
	}
}
