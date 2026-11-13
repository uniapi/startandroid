/* \uFDFD
 *                           \u0648\u0671\u0630\u0643\u0631 \u0631\u0628\u0651\u0643
 *              \u0643\u062A\u0628\u0647 \u0639\u0628\u062F \u0671\u0644\u0643\u0631\u064A\u0645
 *  \u06F1\u06F4\u06F4\u06F8 \u062C\u0645\u0627\u062F\u0649 \u0627\u0644\u0622\u062E\u0631\u0629 \u06F4
 */
package localhost.idroid.salamun

import java.util.Date

import android.util.Log
import android.app.Activity
import android.content.Intent
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.view.View
import android.view.Gravity
import android.view.ViewGroup.LayoutParams
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Button

class SalamActivity : Activity() {
	companion object {
		private const val TAG = "localhost.idroid.salamun"
	}
	private lateinit var tvEnabledGPS: TextView
	private lateinit var tvStatusGPS: TextView
	private lateinit var tvLocationGPS: TextView
	private lateinit var tvEnabledNet: TextView
	private lateinit var tvStatusNet: TextView
	private lateinit var tvLocationNet: TextView
	private lateinit var locationManager: LocationManager
	private val sbGPS = StringBuilder()
	private val sbNet = StringBuilder()


	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		val pxOfDp5 = dpToPx(this, 5f)
		val pxOfDp10 = dpToPx(this, 10f)
		val llRoot = LinearLayout(this).apply {
			layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
			orientation = LinearLayout.VERTICAL
			setPadding(pxOfDp5, pxOfDp5, pxOfDp5, pxOfDp5)
		}
		val tvTitleGPS = TextView(this).apply {
			layoutParams = LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
			text = getString(R.string.provider_gps)
			textSize = 30f
		}
		tvEnabledGPS = TextView(this).apply {
			layoutParams = LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
			textSize = 24f
		}
		tvStatusGPS = TextView(this).apply {
			layoutParams = LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
			textSize = 24f
		}
		tvLocationGPS = TextView(this).apply {
			layoutParams = LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
			textSize = 24f
		}
		val tvTitleNet = TextView(this).apply {
			layoutParams = LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT).apply {
				topMargin = pxOfDp10
			}
			text = getString(R.string.provider_network)
			textSize = 30f
		}
		tvEnabledNet = TextView(this).apply {
			layoutParams = LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
			textSize = 24f
		}
		tvStatusNet = TextView(this).apply {
			layoutParams = LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
			textSize = 24f
		}
		tvLocationNet = TextView(this).apply {
			layoutParams = LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
			textSize = 24f
		}
		val btnLocationSettings = Button(this).apply {
			layoutParams = LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT).apply {
				topMargin = pxOfDp10
			}
			text = getString(R.string.location_settings)
			setOnClickListener { onClickLocationSettings(it) }
		}
		with(llRoot) {
			addView(tvTitleGPS)
			addView(tvEnabledGPS)
			addView(tvStatusGPS)
			addView(tvLocationGPS)
			addView(tvTitleNet)
			addView(tvEnabledNet)
			addView(tvStatusNet)
			addView(tvLocationNet)
			addView(btnLocationSettings)
		}
		setContentView(llRoot)

		locationManager = getSystemService(LOCATION_SERVICE) as LocationManager
	}
	override fun onResume() {
		super.onResume()
		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000 * 10, 10f, locationListener)
		locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000 * 10, 10f, locationListener)
		checkEnabled()
	}
	override fun onPause() {
		super.onPause()
		locationManager.removeUpdates(locationListener)
	}
	private val locationListener = object : LocationListener {
		override fun onLocationChanged(location: Location) {
			showLocation(location)
		}
		override fun onProviderDisabled(provider: String) {
			checkEnabled()
		}
		override fun onProviderEnabled(provider: String) {
			checkEnabled()
			val lastLocation: Location? = locationManager.getLastKnownLocation(provider)
			showLocation(lastLocation)
		}
		override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {
			when (provider) {
				LocationManager.GPS_PROVIDER -> {
					tvStatusGPS.text = "Status: $status"
				}
				LocationManager.NETWORK_PROVIDER -> {
					tvStatusNet.text = "Status: $status"
				}
			}
		}
	}
	private fun showLocation(location: Location?) {
		location ?: return
		when (location.provider) {
			LocationManager.GPS_PROVIDER -> tvLocationGPS.text = formatLocation(location)
			LocationManager.NETWORK_PROVIDER -> tvLocationNet.text = formatLocation(location)
		}
	}
	private fun formatLocation(location: Location?): String {
		location ?: return ""
		return String.format("Coordinates: lat %1\$.4f, lon = %2\$.4f, time = %3\$tF %3\$tT",
			location.latitude, location.longitude, Date(location.time)
		)
	}
	private fun checkEnabled() {
		tvEnabledGPS.setText("Enabled: ${locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)}")
		tvEnabledNet.setText("Enabled: ${locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)}")
	}
	fun onClickLocationSettings(view: View?) {
		startActivity(Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS))
	}
}
