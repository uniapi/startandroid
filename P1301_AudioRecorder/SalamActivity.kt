/* \uFDFD
 *                           \u0648\u0671\u0630\u0643\u0631 \u0631\u0628\u0651\u0643
 *              \u0643\u062A\u0628\u0647 \u0639\u0628\u062F \u0671\u0644\u0643\u0631\u064A\u0645
 *  \u06F1\u06F4\u06F4\u06F8 \u062C\u0645\u0627\u062F\u0649 \u0627\u0644\u0623\u0648\u0644\u0649 \u06F2\u06F1
 */
package localhost.idroid.salamun

import java.io.File

import android.util.Log
import android.app.Activity
import android.media.AudioFormat
import android.media.AudioRecord
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
	private val myBufferSize = 8192
	private var audioRecord: AudioRecord? = null
	@Volatile private var isReading: Boolean = false

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		val llRoot = LinearLayout(this).apply {
			layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
			orientation = LinearLayout.VERTICAL
		}
		val	params = LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
		val llRecordControl = LinearLayout(this).apply {
			layoutParams = LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
		}
		val btnRecordStart = Button(this).apply { setText(R.string.start_record); setOnClickListener(::recordStart) }
		val btnRecordStop = Button(this).apply { setText(R.string.stop_record); setOnClickListener(::recordStop) }
		val llReadControl = LinearLayout(this).apply {
			layoutParams = LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
		}
		val btnReadStart = Button(this).apply { setText(R.string.start_read); setOnClickListener { readStart(it) } }
		val btnReadStop = Button(this).apply { setText(R.string.stop_read); setOnClickListener { readStop(it) } }
		with(llRecordControl) {
			addView(btnRecordStart, params)
			addView(btnRecordStop, params)
		}
		with(llReadControl) {
			addView(btnReadStart, params)
			addView(btnReadStop, params)
		}
		with(llRoot) {
			addView(llRecordControl)
			addView(llReadControl)
		}
		setContentView(llRoot)

		createAudioRecorder()
		Log.d(TAG, "init state = ${audioRecord?.getState()}")
	}
	private fun createAudioRecorder() {
		val sampleRate = 8000
		val channelConfig = AudioFormat.CHANNEL_IN_MONO
		val audioFormat = AudioFormat.ENCODING_PCM_16BIT
		val minInternalBufferSize = AudioRecord.getMinBufferSize(sampleRate, channelConfig, audioFormat)
		val internalBufferSize = minInternalBufferSize * 4
		Log.d(TAG, "minInternalBufferSize = $minInternalBufferSize, internalBufferSize = $internalBufferSize, myBufferSize = $myBufferSize")
		audioRecord = AudioRecord(MediaRecorder.AudioSource.MIC, sampleRate, channelConfig, audioFormat, internalBufferSize).apply {
			setPositionNotificationPeriod(1000)
			setNotificationMarkerPosition(10000)
			setRecordPositionUpdateListener(object : AudioRecord.OnRecordPositionUpdateListener {
				override fun onPeriodicNotification(recorder: AudioRecord) {
					Log.d(TAG, "onPeriodicNotification")
				}
				override fun onMarkerReached(recorder: AudioRecord) {
					Log.d(TAG, "onMarkerReached")
					isReading = false
				}
			})
		}
	}
	fun recordStart(v: View) {
		Log.d(TAG, "record start")
		audioRecord?.startRecording()
		val recordingState = audioRecord?.recordingState
		Log.d(TAG, "recordingState = $recordingState")
	}
	fun recordStop(v: View) {
		Log.d(TAG, "record stop")
		audioRecord?.stop()
	}
	fun readStart(v: View) {
		Log.d(TAG, "read start")
		isReading = true
		Thread(Runnable {
			if (audioRecord == null) return@Runnable
			val myBuffer = ByteArray(myBufferSize)
			var totalCount = 0
			while (isReading) {
				val readCount = audioRecord!!.read(myBuffer, 0, myBufferSize)
				totalCount += readCount
				Log.d(TAG, "readCount = $readCount, totalCount = $totalCount")
			}
		}).start()
	}
	fun readStop(v: View) {
		Log.d(TAG, "read stop")
		isReading = false
	}
	override fun onDestroy() {
		super.onDestroy()
		isReading = false
		audioRecord?.release()
	}
}
