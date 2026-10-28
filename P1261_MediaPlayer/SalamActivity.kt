/* \uFDFD
 *                           \u0648\u0671\u0630\u0643\u0631 \u0631\u0628\u0651\u0643
 *              \u0643\u062A\u0628\u0647 \u0639\u0628\u062F \u0671\u0644\u0643\u0631\u064A\u0645
 *  \u06F1\u06F4\u06F4\u06F8 \u062C\u0645\u0627\u062F\u0649 \u0627\u0644\u0623\u0648\u0644\u0649 \u06F1\u06F8
 */
package localhost.idroid.salamun

import java.io.IOException

import android.util.Log
import android.app.Activity
import android.content.ContentUris
import android.content.Context
import android.media.AudioManager
import android.media.MediaPlayer
import android.media.MediaPlayer.OnCompletionListener
import android.media.MediaPlayer.OnPreparedListener
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.view.View
import android.view.ViewGroup.LayoutParams
import android.widget.LinearLayout
import android.widget.Button
import android.widget.CheckBox

class SalamActivity : Activity(), OnPreparedListener, OnCompletionListener {
	companion object {
		private const val TAG = "localhost.idroid.salamun"
		private const val DATA_HTTP = "http://dl.dropboxusercontent.com/u/6197740/explosion.mp3"
		private const val DATA_STREAM = "http://online.radiorecord.ru:8101/rr_128"
		private val DATA_SD = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC).absolutePath + "/music.mp3"
		private val DATA_URI: Uri = ContentUris.withAppendedId(android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, 13359)
	}
	private var mediaPlayer: MediaPlayer? = null
	private lateinit var am: AudioManager

	private lateinit var btnStartHttp: Button
	private lateinit var btnStartStream: Button
	private lateinit var btnStartSd: Button
	private lateinit var btnStartUri: Button
	private lateinit var btnStartRaw: Button

	private lateinit var btnPause: Button
	private lateinit var btnResume: Button
	private lateinit var btnStop: Button
	private lateinit var chbLoop: CheckBox

	private lateinit var btnBackward: Button
	private lateinit var btnForward: Button
	private lateinit var btnInfo: Button

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		val llRoot = LinearLayout(this).apply {
			layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
			orientation = LinearLayout.VERTICAL
			setPadding(15, 15, 15, 15)
		}
		// Row 1 Setup (Media Source Selectors)
		val llRow1 = LinearLayout(this).createRowParams().apply {
			(layoutParams as LinearLayout.LayoutParams).topMargin = 30
		}
		val row1Configs = listOf(
			Triple("HTTP", { v: Button -> btnStartHttp = v }, { initAsyncPlayer(DATA_HTTP) }),
			Triple("STREAM", { v: Button -> btnStartStream = v }, { initAsyncPlayer(DATA_STREAM) }),
			Triple("SD", { v: Button -> btnStartSd = v }, { initAsyncPlayer(DATA_SD) }),
			Triple("URI", { v: Button -> btnStartUri = v }, { initSyncPlayer { setDataSource(DATA_SD) } }),
			Triple("RAW", { v: Button -> btnStartRaw = v }, { initRawPlayer() })
		)
		for ((titleText, referenceAssigner, mediaAction) in row1Configs) {
			val btn = createStandardButton(titleText) { mediaAction() }
			referenceAssigner(btn)
			llRow1.addView(btn)
		}
		// Row 2 Setup (Playback Actions & Toggles)
		val llRow2 = LinearLayout(this).createRowParams().apply {
			(layoutParams as LinearLayout.LayoutParams).topMargin = 90
		}
		btnPause = createStandardButton("PAUSE") { handlePlaybackControl(PlaybackCommand.PAUSE) }
		btnResume = createStandardButton("RESUME") { handlePlaybackControl(PlaybackCommand.RESUME) }
		btnStop = createStandardButton("STOP") { handlePlaybackControl(PlaybackCommand.STOP) }
		chbLoop = CheckBox(this).apply {
			layoutParams = LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
			text = "LOOP"
			setOnCheckedChangeListener { _, isChecked ->
				mediaPlayer?.isLooping = isChecked
			}
		}
		with(llRow2) {
			addView(btnPause)
			addView(btnResume)
			addView(btnStop)
			addView(chbLoop)
		}
		// Row 3 Setup (Navigation Seek Commands)
		val llRow3 = LinearLayout(this).createRowParams().apply {
			(layoutParams as LinearLayout.LayoutParams).topMargin = 30
		}
		btnBackward = createStandardButton("BACKWARD") { handlePlaybackControl(PlaybackCommand.BACKWARD) }
		btnForward = createStandardButton("FORWARD") { handlePlaybackControl(PlaybackCommand.FORWARD) }
		btnInfo = createStandardButton("INFO") { handlePlaybackControl(PlaybackCommand.INFO) }
		with(llRow3) {
			addView(btnBackward)
			addView(btnForward)
			addView(btnInfo)
		}
		with(llRoot) {
			addView(llRow1)
			addView(llRow2)
			addView(llRow3)
		}
		setContentView(llRoot)
	}
	// --- SYSTEM MEDIA PLAYER ACTION HOOK PIPELINES ---
	private fun initAsyncPlayer(sourcePath: String) {
		releaseMP()
		try {
			Log.d(TAG, "start network channel payload")
			mediaPlayer = MediaPlayer().apply {
				setDataSource(sourcePath)
				setAudioStreamType(AudioManager.STREAM_MUSIC)
				Log.d(TAG, "prepareAsync")
				setOnPreparedListener(this@SalamActivity)
				prepareAsync()
				finalizePlayerConfiguration(this)
			}
		} catch (e: IOException) {
			Log.e(TAG, "Async initialization failure", e)
		}
	}
	private fun initSyncPlayer(dataSourceConfiguration: MediaPlayer.() -> Unit) {
		releaseMP()
		try {
			Log.d(TAG, "start storage hardware payload")
			mediaPlayer = MediaPlayer().apply {
				dataSourceConfiguration()
				setAudioStreamType(AudioManager.STREAM_MUSIC)
				prepare()
				start()
				finalizePlayerConfiguration(this)
			}
		} catch (e: IOException) {
			Log.e(TAG, "Sync initialization failure", e)
		}
	}
	private fun initRawPlayer() {
		releaseMP()
		val rawResourceId = resources.getIdentifier("explosion", "raw", packageName)
		if (rawResourceId != 0) {
			Log.d(TAG, "start local Raw file payload")
			mediaPlayer = MediaPlayer.create(this, rawResourceId).apply {
				start()
				finalizePlayerConfiguration(this)
			}
		} else {
			Log.e(TAG, "R.raw.explosion file descriptor trace not found")
		}
	}
	private fun finalizePlayerConfiguration(player: MediaPlayer) {
		player.isLooping = chbLoop.isChecked
		player.setOnCompletionListener(this)
	}
	private fun releaseMP() {
		mediaPlayer?.let { player ->
			try {
				player.release()
				mediaPlayer = null
			} catch (e: Exception) {
				Log.e(TAG, "Player destruction sequence leak", e)
			}
		}
	}
	private enum class PlaybackCommand { PAUSE, RESUME, STOP, BACKWARD, FORWARD, INFO }
	private fun handlePlaybackControl(command: PlaybackCommand) {
		val player = mediaPlayer ?: return
		when (command) {
			PlaybackCommand.PAUSE -> if (player.isPlaying) player.pause()
			PlaybackCommand.RESUME -> if (!player.isPlaying) player.start()
			PlaybackCommand.STOP -> player.stop()
			PlaybackCommand.BACKWARD -> player.seekTo((player.currentPosition - 3000).coerceAtLeast(0))
			PlaybackCommand.FORWARD -> player.seekTo((player.currentPosition + 3000).coerceAtMost(player.duration))
			PlaybackCommand.INFO -> {
				Log.d(TAG, "Playing ${player.isPlaying}")
				Log.d(TAG, "Time ${player.currentPosition} / ${player.duration}")
				Log.d(TAG, "Looping ${player.isLooping}")
				Log.d(TAG, "Volume ${am.getStreamVolume(AudioManager.STREAM_MUSIC)}")
			}
		}
	}
	override fun onPrepared(mp: MediaPlayer) {
		Log.d(TAG, "onPrepared")
		mp.start()
	}
	override fun onCompletion(mp: MediaPlayer) {
		Log.d(TAG, "onCompletion")
	}
	override fun onDestroy() {
		releaseMP()
		super.onDestroy()
	}
	private fun LinearLayout.createRowParams(): LinearLayout {
		orientation = LinearLayout.HORIZONTAL
		layoutParams = LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
		return this
	}
	private fun Context.createStandardButton(title: String, clickAction: (View) -> Unit): Button {
		return Button(this).apply {
			layoutParams = LinearLayout.LayoutParams(0, LayoutParams.WRAP_CONTENT, 1f)
			text = title
			textSize = 12f
			setOnClickListener { clickAction(it) }
		}
	}
}
