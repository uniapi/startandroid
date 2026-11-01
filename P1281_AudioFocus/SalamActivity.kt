/* \uFDFD
 *                           \u0648\u0671\u0630\u0643\u0631 \u0631\u0628\u0651\u0643
 *              \u0643\u062A\u0628\u0647 \u0639\u0628\u062F \u0671\u0644\u0643\u0631\u064A\u0645
 *  \u06F1\u06F4\u06F4\u06F8 \u062C\u0645\u0627\u062F\u0649 \u0627\u0644\u0623\u0648\u0644\u0649 \u06F1\u06F7
 */
package localhost.idroid.salamun

import java.io.IOException

import android.util.Log
import android.app.Activity
import android.media.AudioManager
import android.media.AudioManager.OnAudioFocusChangeListener
import android.media.MediaPlayer
import android.media.MediaPlayer.OnCompletionListener
import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.Gravity
import android.view.ViewGroup.LayoutParams
import android.widget.RelativeLayout
import android.widget.Button

class SalamActivity : Activity(), OnCompletionListener {
	private companion object {
		const val TAG = "localhost.idroid.salamun"
		const val ID_PLAY_MUSIC = 1
		const val ID_PLAY_SOUNDG = 2
		const val ID_PLAY_SOUNDGT = 3
		const val ID_PLAY_SOUNDGTD = 4
	}
	private lateinit var audioManager: AudioManager
	private var afListenerMusic: AFListener? = null
	private var afListenerSound: AFListener? = null
	private var mpMusic: MediaPlayer? = null
	private var mpSound: MediaPlayer? = null

	private data class ButtonConfig(
		val textRes: Int,
		val onClick: (View) -> Unit,
		val setupRules: RelativeLayout.LayoutParams.(Map<Int, Int>) -> Unit
	)

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		val rlRoot = RelativeLayout(this).apply {
			layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
		}
		val ids = mapOf(
			R.string.music to ID_PLAY_MUSIC,
			R.string.sound_gain to ID_PLAY_SOUNDG,
			R.string.sound_gain_transient to ID_PLAY_SOUNDGT,
			R.string.sound_gain_transient_duck to ID_PLAY_SOUNDGTD
		)
		val buttonConfigs = listOf(
			ButtonConfig(R.string.music, ::onClickMusic) {
				addRule(RelativeLayout.ALIGN_PARENT_LEFT)
				addRule(RelativeLayout.ALIGN_PARENT_TOP)
			},
			ButtonConfig(R.string.sound_gain, ::onClickSound) { ids ->
				addRule(RelativeLayout.ALIGN_PARENT_LEFT)
				addRule(RelativeLayout.BELOW, ids[R.string.music]!!)
			},
			ButtonConfig(R.string.sound_gain_transient, ::onClickSound) { ids ->
				addRule(RelativeLayout.ALIGN_BASELINE, ids[R.string.sound_gain]!!)
				addRule(RelativeLayout.RIGHT_OF, ids[R.string.sound_gain]!!)
			},
			ButtonConfig(R.string.sound_gain_transient_duck, ::onClickSound) { ids ->
				addRule(RelativeLayout.ALIGN_BASELINE, ids[R.string.sound_gain]!!)
				addRule(RelativeLayout.RIGHT_OF, ids[R.string.sound_gain_transient]!!)
			}
		)
		buttonConfigs.forEach { config ->
			val btn = Button(this).apply {
				id = ids[config.textRes]!!
				text = getString(config.textRes)
				setOnClickListener { config.onClick(this) }
			}
			val params = RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT).apply {
				config.setupRules(this, ids)
			}
			rlRoot.addView(btn, params)
		}
		setContentView(rlRoot)

		audioManager = getSystemService(Context.AUDIO_SERVICE) as AudioManager
	}
	fun onClickMusic(view: View) {
		val musicPlayer = MediaPlayer()
		mpMusic = musicPlayer
		try {
			musicPlayer.setDataSource("/sdcard/Music/music.wav")
			musicPlayer.prepare()
		} catch (e: IOException) {
			e.printStackTrace()
			Log.d(TAG, "error: ${e.localizedMessage}")
		}
		musicPlayer.setOnCompletionListener(this)
		val listener = AFListener(musicPlayer, "Music")
		afListenerMusic = listener
		val requestResult = audioManager.requestAudioFocus(
			listener,
			AudioManager.STREAM_MUSIC,
			AudioManager.AUDIOFOCUS_GAIN
		)
		Log.d(TAG, "Music request focus, result: $requestResult")
		musicPlayer.start()
	}
	fun onClickSound(view: View) {
		val durationHint = when (view.id) {
			ID_PLAY_SOUNDG -> AudioManager.AUDIOFOCUS_GAIN
			ID_PLAY_SOUNDGT -> AudioManager.AUDIOFOCUS_GAIN_TRANSIENT
			ID_PLAY_SOUNDGTD -> AudioManager.AUDIOFOCUS_GAIN_TRANSIENT_MAY_DUCK
			else -> AudioManager.AUDIOFOCUS_GAIN
		}
		val soundPlayer = MediaPlayer.create(this, R.raw.sig)
		mpSound = soundPlayer
		soundPlayer.setOnCompletionListener(this)
		val listener = AFListener(soundPlayer, "Sound")
		afListenerSound = listener
		val requestResult = audioManager.requestAudioFocus(
			listener,
			AudioManager.STREAM_MUSIC,
			durationHint
		)
		Log.d(TAG, "Sound request focus, result: $requestResult")
		soundPlayer.start()
	}
	override fun onCompletion(mp: MediaPlayer?) {
		if (mp == mpMusic) {
			Log.d(TAG, "Music: abandon focus")
			audioManager.abandonAudioFocus(afListenerMusic)
		} else if (mp == mpSound) {
			Log.d(TAG, "Sound: abandon focus")
			audioManager.abandonAudioFocus(afListenerSound)
		}
	}
	override fun onDestroy() {
		super.onDestroy()
		mpMusic?.release()
		mpSound?.release()
		afListenerMusic?.let { audioManager.abandonAudioFocus(it) }
		afListenerSound?.let { audioManager.abandonAudioFocus(it) }
	}
	inner class AFListener(var mp: MediaPlayer, var label: String) : OnAudioFocusChangeListener {
		override fun onAudioFocusChange(focusChange: Int) {
			val event = when (focusChange) {
				AudioManager.AUDIOFOCUS_LOSS -> {
					mp.pause()
					"AUDIOFOCUS_LOSS"
				}
				AudioManager.AUDIOFOCUS_LOSS_TRANSIENT -> {
					mp.pause()
					"AUDIOFOCUS_LOSS_TRANSIENT"
				}
				AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK -> {
					mp.setVolume(0.5f, 0.5f)
					"AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK"
				}
				AudioManager.AUDIOFOCUS_GAIN -> {
					if (!mp.isPlaying())
						mp.start()
					mp.setVolume(1.0f, 1.0f)
					"AUDIOFOCUS_GAIN"
				}
				else -> "UNKNONW"
			}
			Log.d(TAG, "$label onAudioFocusChange: $event")
		}
	}
}
