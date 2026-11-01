/* \uFDFD
 *                           \u0648\u0671\u0630\u0643\u0631 \u0631\u0628\u0651\u0643
 *              \u0643\u062A\u0628\u0647 \u0639\u0628\u062F \u0671\u0644\u0643\u0631\u064A\u0645
 *  \u06F1\u06F4\u06F4\u06F8 \u062C\u0645\u0627\u062F\u0649 \u0627\u0644\u0623\u0648\u0644\u0649 \u06F2\u06F1
 */
package localhost.idroid.salamun

import java.io.File

import android.util.Log
import android.app.Activity
import android.media.MediaPlayer
import android.media.MediaRecorder
import android.os.Bundle
import android.os.Environment
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
	private var mediaRecorder: MediaRecorder? = null
	private var mediaPlayer: MediaPlayer? = null
	private var fileName: String? = null

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		val llRoot = LinearLayout(this).apply {
			layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
			orientation = LinearLayout.VERTICAL
		}
		val	params = LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
		val tvRecord = TextView(this).apply {
			text = getString(R.string.record)
		}
		val llRecordControl = LinearLayout(this).apply {
			layoutParams = LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
		}
		val btnRecordStart = Button(this).apply { setText(R.string.start); setOnClickListener(::recordStart) }
		val btnRecordStop = Button(this).apply { setText(R.string.stop); setOnClickListener(::recordStop) }
		val tvPlay = TextView(this).apply {
			layoutParams = LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT).apply {
				topMargin = 20
			}
			text = getString(R.string.play)
		}
		val llPlayControl = LinearLayout(this).apply {
			layoutParams = LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
		}
		val btnPlayStart = Button(this).apply { setText(R.string.start); setOnClickListener { playStart(it) } }
		val btnPlayStop = Button(this).apply { setText(R.string.stop); setOnClickListener { playStop(it) } }
		with(llRecordControl) {
			addView(btnRecordStart, params)
			addView(btnRecordStop, params)
		}
		with(llPlayControl) {
			addView(btnPlayStart, params)
			addView(btnPlayStop, params)
		}
		with(llRoot) {
			addView(tvRecord, params)
			addView(llRecordControl)
			addView(tvPlay)
			addView(llPlayControl)
		}
		setContentView(llRoot)

		fileName = Environment.getExternalStorageDirectory().absolutePath + "/record.3gpp"
	}
	fun recordStart(v: View) {
		try {
			releaseRecorder()
			val outFile = File(fileName)
			if (outFile.exists()) {
				outFile.delete()
			}
			mediaRecorder = MediaRecorder().apply {
				setAudioSource(MediaRecorder.AudioSource.MIC)
				setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP)
				setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB)
				setOutputFile(fileName)
				prepare()
			}
			mediaRecorder!!.start()
		} catch (e: Exception) {
			Log.d(TAG, "error: ${e.localizedMessage}")
		}
	}
	fun recordStop(v: View) {
		mediaRecorder?.stop()
	}
	fun playStart(v: View) {
		try {
			releasePlayer()
			mediaPlayer = MediaPlayer().apply {
				setDataSource(fileName)
				prepare()
			}
			mediaPlayer!!.start()
		} catch (e: Exception) {
			Log.d(TAG, "error: ${e.localizedMessage}")
		}
	}
	fun playStop(v: View) {
		mediaPlayer?.stop()
	}
	private fun releaseRecorder() {
		mediaRecorder?.release()
		mediaRecorder = null
	}
	private fun releasePlayer() {
		mediaPlayer?.release()
		mediaPlayer = null
	}
	override fun onDestroy() {
		super.onDestroy()
		releasePlayer()
		releaseRecorder()
	}
}
