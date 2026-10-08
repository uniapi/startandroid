/* \uFDFD
 *		  			   \u0648\u0671\u0630\u0643\u0631 \u0631\u0628\u0651\u0643
 *		  \u0643\u062A\u0628\u0647 \u0639\u0628\u062F \u0671\u0644\u0643\u0631\u064A\u0645
 *  \u06F1\u06F4\u06F4\u06F8 \u0631\u0628\u064A\u0639 \u0671\u0644\u0622\u062E\u0631 \u06F2\u06F7
 */
package localhost.idroid.salamun

import android.util.Log
import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import android.preference.PreferenceManager
import android.os.Bundle
import android.view.View
import android.view.Gravity
import android.view.ViewGroup.LayoutParams
import android.view.Menu
import android.view.MenuItem
import android.widget.LinearLayout

class SalamActivity : Activity() {
	private companion object {
		const val TAG = "localhost.idroid.salamun"
	}
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		val rootLayout = LinearLayout(this).apply {
			layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
			orientation = LinearLayout.VERTICAL
		}
		setContentView(rootLayout)
	}
	override fun onCreateOptionsMenu(menu: Menu?): Boolean {
		val mi = menu?.add(0, 1, 0, "Preferences")
		mi?.let {
			it.setIntent(Intent(this, PrefActivity::class.java))
		}
		return super.onCreateOptionsMenu(menu)
	}
}
