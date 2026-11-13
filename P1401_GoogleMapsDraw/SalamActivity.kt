/* \uFDFD
 *                           \u0648\u0671\u0630\u0643\u0631 \u0631\u0628\u0651\u0643
 *              \u0643\u062A\u0628\u0647 \u0639\u0628\u062F \u0671\u0644\u0643\u0631\u064A\u0645
 *  \u06F1\u06F4\u06F4\u06F8 \u062C\u0645\u0627\u062F\u0649 \u0627\u0644\u0622\u062E\u0631\u0629 \u06F4
 */
package localhost.idroid.salamun

import android.util.Log
import android.os.Bundle
import android.support.v4.app.FragmentActivity
import android.view.View

import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.Marker


class SalamActivity : FragmentActivity() {
	companion object {
		private const val TAG = "localhost.idroid.salamun"
	}
	private var mapFragment: SupportMapFragment? = null
	private var map: GoogleMap? = null
	private var maker: Marker? = null

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.main)

		mapFragment = supportFragmentManager.findFragmentById(R.id.map) as? SupportMapFragment
		map = mapFragment?.map
		if (map == null) {
			finish()
			return
		}
		init()
	}
	private fun init() {
	}
	fun onClickTest(view: View?) {
		marker?.showInfoWindows()
	}
}
