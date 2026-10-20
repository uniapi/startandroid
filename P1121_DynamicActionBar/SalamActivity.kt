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
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.CheckBox

class SalamActivity : Activity() {
	private companion object {
		const val TAG = "localhost.idroid.salamun"
	}
	private val MENU_ID = 1
	private lateinit var chbAddDel: CheckBox
	private lateinit var chbVisible: CheckBox
	private lateinit var frag1: Fragment
	private lateinit var frag2: Fragment
	private lateinit var frag: Fragment

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.main)

		chbAddDel = findViewById(R.id.chbAddDel) as CheckBox
		chbVisible = findViewById(R.id.chbVisible) as CheckBox
		frag1 = Fragment1()
		frag2 = Fragment2()
		frag = frag1
	}
	override fun onCreateOptionsMenu(menu: Menu?): Boolean {
		menuInflater.inflate(R.menu.main, menu)
		menu?.setGroupVisible(R.id.groupVsbl, chbVisible.isChecked)
		if (chbAddDel.isChecked) {
			menu?.add(0, MENU_ID, 0, R.string.menu_item1)
				?.setIcon(android.R.drawable.ic_delete)
				?.setShowAsAction(
					MenuItem.SHOW_AS_ACTION_ALWAYS or MenuItem.SHOW_AS_ACTION_WITH_TEXT
				)
		} else {
			menu?.removeItem(MENU_ID)
		}
		return true
	}
	fun onClick(view: View?) {
		when (view?.id) {
			R.id.chbAddDel, R.id.chbVisible -> {
				invalidateOptionsMenu()
			}
			R.id.btnFrag -> {
				frag = if (frag === frag1) frag2 else frag1
				fragmentManager.beginTransaction()
					.replace(R.id.cont, frag)
					.commit()
			}
		}
	}
}
