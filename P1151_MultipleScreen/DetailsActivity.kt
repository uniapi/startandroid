/* \uFDFD
 *                           \u0648\u0671\u0630\u0643\u0631 \u0631\u0628\u0651\u0643
 *              \u0643\u062A\u0628\u0647 \u0639\u0628\u062F \u0671\u0644\u0643\u0631\u064A\u0645
 *  \u06F1\u06F4\u06F4\u06F8 \u062C\u0645\u0627\u062F\u0649 \u0627\u0644\u0623\u0648\u0644\u0649 \u06F9
 */
package localhost.idroid.salamun

import android.content.res.Configuration
import android.os.Bundle
import android.support.v4.app.FragmentActivity

class DetailsActivity : FragmentActivity() {
	private companion object {
		const val TAG = "localhost.idroid.salamun"
	}
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE && isLarge()) {
			finish()
			return
		}
		if (savedInstanceState == null) {
			val details = DetailsFragment.newInstance(intent.getIntExtra("position", 0))
			supportFragmentManager.beginTransaction().add(android.R.id.content, details).commit()
		}
	}
	fun isLarge(): Boolean {
		return (resources.configuration.screenLayout and Configuration.SCREENLAYOUT_SIZE_MASK) >= Configuration.SCREENLAYOUT_SIZE_LARGE
	}
}
