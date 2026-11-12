/* \uFDFD
 *                           \u0648\u0671\u0630\u0643\u0631 \u0631\u0628\u0651\u0643
 *              \u0643\u062A\u0628\u0647 \u0639\u0628\u062F \u0671\u0644\u0643\u0631\u064A\u0645
 *  \u06F1\u06F4\u06F4\u06F8 \u062C\u0645\u0627\u062F\u0649 \u0627\u0644\u0622\u062E\u0631\u0629 \u06F3
 */
package localhost.idroid.salamun

import java.util.Timer
import java.util.TimerTask

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
import android.widget.TextView

class SalamActivity : Activity() {
	companion object {
		private const val TAG = "localhost.idroid.salamun"
	}
	private lateinit var tvText: TextView
	private lateinit var sensorManager: SensorManager
	private lateinit var sensorAccel: Sensor
	private lateinit var sensorLinAccel: Sensor
	private lateinit var sensorGravity: Sensor
	private var sb = StringBuilder()
	private var timer: Timer? = null

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		val rlRoot = RelativeLayout(this).apply {
			layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
		}
		tvText = TextView(this).apply {
			layoutParams = RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
			setTextAppearance(this@SalamActivity, android.R.style.TextAppearance_Large)
		}
		with(rlRoot) {
			addView(tvText)
		}
		setContentView(rlRoot)

		sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
		sensorAccel = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
		sensorLinAccel = sensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION)
		sensorGravity = sensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY)
	}
	override fun onResume() {
		super.onResume()
		sensorManager.registerListener(listener, sensorAccel, SensorManager.SENSOR_DELAY_NORMAL)
		sensorManager.registerListener(listener, sensorLinAccel, SensorManager.SENSOR_DELAY_NORMAL)
		sensorManager.registerListener(listener, sensorGravity, SensorManager.SENSOR_DELAY_NORMAL)
		timer = Timer()
		val task = object : TimerTask() {
			override fun run() {
				runOnUiThread {
					showInfo()
				}
			}
		}
		timer?.schedule(task, 0, 400)
	}
	override fun onPause() {
		super.onPause()
		sensorManager.unregisterListener(listener)
		timer?.cancel()
	}
	private fun format(values: FloatArray): String {
		return String.format("%1$.1f\t\t%2$.1f\t\t%3$.1f", values[0], values[1], values[2])
	}
	private fun showInfo() {
		sb.clear()
		sb.append("Accelerometer: ${format(valuesAccel)}")
			.append("\n\nAccel motion: ${format(valuesAccelMotion)}")
			.append("\nAccel gravity: ${format(valuesAccelGravity)}")
			.append("\n\nLin accel: ${format(valuesLinAccel)}")
			.append("\nGravity: ${format(valuesGravity)}")
		tvText.setText(sb)
		Log.d(TAG, "${sb.toString()}")
	}
	private val valuesAccel = FloatArray(3)
	private val valuesAccelMotion = FloatArray(3)
	private val valuesAccelGravity = FloatArray(3)
	private val valuesLinAccel = FloatArray(3)
	private val valuesGravity = FloatArray(3)

	private val listener = object : SensorEventListener {
		override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}
		override fun onSensorChanged(event: SensorEvent?) {
			event ?: return
			when (event.sensor.type) {
				Sensor.TYPE_ACCELEROMETER -> {
					for (i in 0..2) {
						valuesAccel[i] = event.values[i]
						valuesAccelGravity[i] = 0.1f * event.values[i] + 0.9f * valuesAccelGravity[i]
						valuesAccelMotion[i] = event.values[i] - valuesAccelGravity[i]
					}
				}
				Sensor.TYPE_LINEAR_ACCELERATION -> {
					event.values.copyInto(valuesLinAccel, endIndex = 3)
				}
				Sensor.TYPE_GRAVITY -> {
					event.values.copyInto(valuesGravity, endIndex = 3)
				}
			}
		}
	}
}
