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
import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.view.Display
import android.view.Surface
import android.view.WindowManager
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
	private lateinit var sensorMagnet: Sensor
	private var sb = StringBuilder()
	private var timer: Timer? = null
	private var rotation: Int = 0

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
		sensorMagnet = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD)
	}
	override fun onResume() {
		super.onResume()
		sensorManager.registerListener(listener, sensorAccel, SensorManager.SENSOR_DELAY_NORMAL)
		sensorManager.registerListener(listener, sensorMagnet, SensorManager.SENSOR_DELAY_NORMAL)
		timer = Timer()
		val task = object : TimerTask() {
			override fun run() {
				runOnUiThread {
					getDeviceOrientation()
					getActualDeviceOrientation()
					showInfo()
				}
			}
		}
		timer?.schedule(task, 0, 400)
		val windowManager = getSystemService(Context.WINDOW_SERVICE) as WindowManager
		val display = windowManager.getDefaultDisplay()
		val rotation = display.rotation
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
		sb.append("Orientation: ${format(valuesResult)}")
			.append("\nOrientation 2: ${format(valuesResult2)}")
		tvText.setText(sb)
		Log.d(TAG, "${sb.toString()}")
	}
	private val r = FloatArray(9)

	private fun getDeviceOrientation() {
		SensorManager.getRotationMatrix(r, null, valuesAccel, valuesMagnet)
		SensorManager.getOrientation(r, valuesResult)
		valuesResult[0] = Math.toDegrees(valuesResult[0].toDouble()).toFloat()
		valuesResult[1] = Math.toDegrees(valuesResult[1].toDouble()).toFloat()
		valuesResult[2] = Math.toDegrees(valuesResult[2].toDouble()).toFloat()
	}
	val inR = FloatArray(9)
	val outR = FloatArray(9)

	private fun getActualDeviceOrientation() {
		SensorManager.getRotationMatrix(inR, null, valuesAccel, valuesMagnet)
		var x_axis = SensorManager.AXIS_X
		var y_axis = SensorManager.AXIS_Y
		when (rotation) {
			Surface.ROTATION_0 -> {}
			Surface.ROTATION_90 -> {
				x_axis = SensorManager.AXIS_Y
				y_axis = SensorManager.AXIS_MINUS_X
			}
			Surface.ROTATION_180 -> {
				y_axis = SensorManager.AXIS_MINUS_Y
			}
			Surface.ROTATION_270 -> {
				x_axis = SensorManager.AXIS_MINUS_Y
				y_axis = SensorManager.AXIS_X
			}
		}
		SensorManager.remapCoordinateSystem(inR, x_axis, y_axis, outR)
		SensorManager.getOrientation(outR, valuesResult2)
		valuesResult2[0] = Math.toDegrees(valuesResult2[0].toDouble()).toFloat()
		valuesResult2[1] = Math.toDegrees(valuesResult2[1].toDouble()).toFloat()
		valuesResult2[2] = Math.toDegrees(valuesResult2[2].toDouble()).toFloat()
	}
	private val valuesAccel = FloatArray(3)
	private val valuesMagnet = FloatArray(3)
	private val valuesResult = FloatArray(3)
	private val valuesResult2 = FloatArray(3)

	private val listener = object : SensorEventListener {
		override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}
		override fun onSensorChanged(event: SensorEvent?) {
			event ?: return
			when (event.sensor.type) {
				Sensor.TYPE_ACCELEROMETER -> {
					event.values.copyInto(valuesAccel, endIndex = 3)
				}
				Sensor.TYPE_MAGNETIC_FIELD -> {
					event.values.copyInto(valuesMagnet, endIndex = 3)
				}
			}
		}
	}
}
