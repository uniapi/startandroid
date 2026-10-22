/* \uFDFD
 *                           \u0648\u0671\u0630\u0643\u0631 \u0631\u0628\u0651\u0643
 *              \u0643\u062A\u0628\u0647 \u0639\u0628\u062F \u0671\u0644\u0643\u0631\u064A\u0645
 *  \u06F1\u06F4\u06F4\u06F8 \u062C\u0645\u0627\u062F\u0649 \u0627\u0644\u0623\u0648\u0644\u0649 \u06F9
 */
package localhost.idroid.salamun

import android.util.Log
import android.content.Intent
import android.app.Activity
import android.os.Bundle
import android.support.v4.app.FragmentActivity

class SalamActivity : FragmentActivity(), TitlesFragment.OnItemClickListener {
	private companion object {
		const val TAG = "localhost.idroid.salamun"
	}
	private var position = 0
	private var withDetails: Boolean = true

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.main)

		if (savedInstanceState != null) {
			position = savedInstanceState.getInt("position")
		}
		withDetails = findViewById(R.id.cont) != null
		if (withDetails) {
			showDetails(position)
		}
	}
	private fun showDetails(pos: Int) {
		if (withDetails) {
			var details = supportFragmentManager.findFragmentById(R.id.cont) as? DetailsFragment
			if (details == null || details.getPosition() != pos) {
				details = DetailsFragment.newInstance(pos)
				supportFragmentManager.beginTransaction()
					.replace(R.id.cont, details).commit()
			}
		} else {
			startActivity(Intent(this, DetailsActivity::class.java).putExtra("position", position))
		}
	}
	override fun itemClick(position: Int) {
		this.position = position
		showDetails(position)
	}
	override fun onSaveInstanceState(outState: Bundle) {
		super.onSaveInstanceState(outState)
		outState.putInt("position", position)
	}
}
