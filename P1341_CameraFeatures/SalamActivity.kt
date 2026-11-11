/* \uFDFD
 *                           \u0648\u0671\u0630\u0643\u0631 \u0631\u0628\u0651\u0643
 *              \u0643\u062A\u0628\u0647 \u0639\u0628\u062F \u0671\u0644\u0643\u0631\u064A\u0645
 *  \u06F1\u06F4\u06F4\u06F8 \u062C\u0645\u0627\u062F\u0649 \u0627\u0644\u0622\u062E\u0631\u0629 \u06F1
 */
package localhost.idroid.salamun

import android.util.Log
import android.app.Activity
import android.hardware.Camera
import android.os.Bundle
import android.view.SurfaceView
import android.view.SurfaceHolder
import android.view.View
import android.view.Gravity
import android.view.ViewGroup.LayoutParams
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.LinearLayout
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import android.widget.Spinner
import android.widget.Button

class SalamActivity : Activity() {
	companion object {
		private const val TAG = "localhost.idroid.salamun"
	}
	private lateinit var surfaceView: SurfaceView
	private lateinit var spEffect: Spinner
	private lateinit var spFlash: Spinner
	private var camera: Camera? = null

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		val pxOfDp10 = dpToPx(this, 10f)
		val llRoot = LinearLayout(this).apply {
			layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
			orientation = LinearLayout.VERTICAL
			setPadding(pxOfDp10, pxOfDp10, pxOfDp10, pxOfDp10)
		}
		surfaceView = SurfaceView(this).apply {
			id = R.id.surfaceView
			layoutParams = LinearLayout.LayoutParams(dpToPx(this@SalamActivity, 400f), dpToPx(this@SalamActivity, 300f)).apply {
				bottomMargin = pxOfDp10
			}
		}
		val tableLayout = TableLayout(this).apply {
			layoutParams = LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
		}
		val rowEffect = TableRow(this).apply {
			layoutParams = TableLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
			setGravity(Gravity.CENTER_VERTICAL)
		}
		val tvColorEffect = TextView(this).apply {
			layoutParams = TableRow.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
			text = getString(R.string.color_effect)
		}
		spEffect = Spinner(this).apply {
			id = R.id.spEffect
			layoutParams = TableRow.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
		}
		val rowFlash = TableRow(this).apply {
			layoutParams = TableLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
			setGravity(Gravity.CENTER_VERTICAL)
		}
		val tvFlashMode = TextView(this).apply {
			layoutParams = TableRow.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
			text = getString(R.string.flash_mode)
		}
		spFlash = Spinner(this).apply {
			id = R.id.spFlash
			layoutParams = TableRow.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
		}
		with(rowEffect) {
			addView(tvColorEffect)
			addView(spEffect)
		}
		with(rowFlash) {
			addView(tvFlashMode)
			addView(spFlash)
		}
		with(tableLayout) {
			addView(rowEffect)
			addView(rowFlash)
		}
		with(llRoot) {
			addView(surfaceView)
			addView(tableLayout)
		}
		setContentView(llRoot)

		val holder = surfaceView.holder
		holder.addCallback(object : SurfaceHolder.Callback {
			override fun surfaceCreated(holder: SurfaceHolder) {
				try {
					camera?.setPreviewDisplay(holder)
					camera?.startPreview()
				} catch (e: Exception) {
					e.printStackTrace()
				}
			}
			override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {}
			override fun surfaceDestroyed(holder: SurfaceHolder) {}
		})
	}
	override fun onResume() {
		super.onResume()
		camera = Camera.open()
		initSpinners()
	}
	override fun onPause() {
		super.onPause()
		camera?.release()
		camera = null
	}
	private fun initSpinners() {
		val currentCamera = camera ?: return
		val colorEffects = currentCamera.parameters.supportedColorEffects
		if (colorEffects != null) {
			val spEffect = initSpinner(
				spEffect,
				colorEffects,
				currentCamera.parameters.colorEffect
			)
			spEffect.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
				override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
					camera?.let { cam ->
						val params = cam.parameters
						params.colorEffect = colorEffects[position]
						cam.parameters = params
					}
				}
				override fun onNothingSelected(parent: AdapterView<*>?) {}
			}
		}
		val flashModes = currentCamera.parameters.supportedFlashModes
		if (flashModes != null) {
			val spFlash = initSpinner(spFlash, flashModes, currentCamera.parameters.flashMode)
			spFlash.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
				override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
					camera?.let { cam ->
						val params = cam.parameters
						params.flashMode = flashModes[position]
						cam.parameters = params
					}
				}
				override fun onNothingSelected(parent: AdapterView<*>?) {}
			}
		}
	}
	private fun initSpinner(spinner: Spinner, data: List<String>, currentValue: String?): Spinner {
		val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, data)
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
		spinner.adapter = adapter

		for (i in data.indices) {
			if (data[i] == currentValue) {
				spinner.setSelection(i)
			}
		}
		return spinner
	}
}
