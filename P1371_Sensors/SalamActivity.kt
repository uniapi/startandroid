/* \uFDFD
 *                           \u0648\u0671\u0630\u0643\u0631 \u0631\u0628\u0651\u0643
 *              \u0643\u062A\u0628\u0647 \u0639\u0628\u062F \u0671\u0644\u0643\u0631\u064A\u0645
 *  \u06F1\u06F4\u06F4\u06F8 \u062C\u0645\u0627\u062F\u0649 \u0627\u0644\u0622\u062E\u0631\u0629 \u06F3
 */
package localhost.idroid.salamun

import android.util.Log
import android.app.Activity
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.view.View
import android.view.Gravity
import android.view.ViewGroup.LayoutParams
import android.widget.RelativeLayout
import android.widget.FrameLayout
import android.widget.ScrollView
import android.widget.TextView
import android.widget.Button

class SalamActivity : Activity() {
	companion object {
		private const val TAG = "localhost.idroid.salamun"
	}
	private lateinit var tvText: TextView
	private lateinit var sensorManager: SensorManager
	private lateinit var sensors: List<Sensor>
	private lateinit var sensorLight: Sensor

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		val rlRoot = RelativeLayout(this).apply {
			layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
		}
		val btnSensList = Button(this).apply {
			id = R.id.btnSensList
			layoutParams = RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
			text = getString(R.string.list)
			setOnClickListener { onClickSensList(it) }
		}
		val btnSensLight = Button(this).apply {
			id = R.id.btnSensLight
			layoutParams = RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT).apply {
				addRule(RelativeLayout.RIGHT_OF, R.id.btnSensList)
			}
			text = getString(R.string.light)
			setOnClickListener { onClickSensLight(it) }
		}
		val sv = ScrollView(this).apply {
			layoutParams = RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT).apply {
				addRule(RelativeLayout.BELOW, R.id.btnSensList)
			}
		}
		tvText = TextView(this).apply {
			layoutParams = FrameLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
		}
		sv.addView(tvText)
		with(rlRoot) {
			addView(btnSensList)
			addView(btnSensLight)
			addView(sv)
		}
		setContentView(rlRoot)

		sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
		sensors = sensorManager.getSensorList(Sensor.TYPE_ALL)
		sensorLight = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT)
	}
	fun onClickSensList(view: View?) {
		sensorManager.unregisterListener(listenerLight, sensorLight)
		val sb = StringBuilder()
		for (sensor in sensors) {
			sb.append("name = ${sensor.name}, type = ${sensor.type}")
				.append("\nvendor = ${sensor.vendor}, version = ${sensor.version}")
				.append("\nmax = ${sensor.maximumRange}, resolution = ${sensor.resolution}")
				.append("\n-----------------------------------------------------------------\n")
		}
		tvText.setText(sb)
		Log.d(TAG, "${sb.toString()}")
	}
	fun onClickSensLight(view: View?) {
		sensorManager.registerListener(listenerLight, sensorLight, SensorManager.SENSOR_DELAY_NORMAL)
	}
	override fun onPause() {
		super.onPause()
		sensorManager.unregisterListener(listenerLight, sensorLight)
	}
	private val listenerLight = object : SensorEventListener {
		override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}
		override fun onSensorChanged(event: SensorEvent?) {
			event?.let {
				tvText.text = it.values[0].toString()
			}
		}
	}
}
