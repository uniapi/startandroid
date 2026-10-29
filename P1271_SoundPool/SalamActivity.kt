/* \uFDFD
 *                           \u0648\u0671\u0630\u0643\u0631 \u0631\u0628\u0651\u0643
 *              \u0643\u062A\u0628\u0647 \u0639\u0628\u062F \u0671\u0644\u0643\u0631\u064A\u0645
 *  \u06F1\u06F4\u06F4\u06F8 \u062C\u0645\u0627\u062F\u0649 \u0627\u0644\u0623\u0648\u0644\u0649 \u06F1\u06F7
 */
package localhost.idroid.salamun

import java.io.IOException
import java.util.concurrent.TimeUnit

import android.util.Log
import android.app.Activity
import android.media.AudioManager
import android.media.SoundPool
import android.media.SoundPool.OnLoadCompleteListener
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.Gravity
import android.view.ViewGroup.LayoutParams
import android.widget.RelativeLayout
import android.widget.Button

class SalamActivity : Activity(), OnLoadCompleteListener {
	companion object {
		private const val TAG = "localhost.idroid.salamun"
	}
	private val MAX_STREAMS = 5
	private lateinit var sp: SoundPool
	private var soundIdShot = 0
	private var soundIdExplosion = 0

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		val rlRoot = RelativeLayout(this).apply {
			layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
		}
		val btnPlay = Button(this).apply {
			layoutParams = RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT).apply {
				addRule(RelativeLayout.ALIGN_PARENT_LEFT)
				addRule(RelativeLayout.ALIGN_PARENT_TOP)
			}
			text = getString(R.string.play)
			setOnClickListener { onClick(it) }
		}
		with(rlRoot) {
			addView(btnPlay)
		}
		setContentView(rlRoot)

		sp = SoundPool(MAX_STREAMS, AudioManager.STREAM_MUSIC, 0)
		sp.setOnLoadCompleteListener(this)
		soundIdShot = sp.load(this, R.raw.beep, 1)
		Log.d(TAG, "soundIdShot = $soundIdShot")
		try {
			soundIdExplosion = sp.load(assets.openFd("sig.wav"), 1)
		} catch (e: IOException) {
			e.printStackTrace()
		}
		Log.d(TAG, "soundIdExplosion = $soundIdExplosion")
	}
	fun onClick(view: View) {
		val streamIdShot = sp.play(soundIdShot, 1f, 1f, 0, 0, 0.5f)
		val streamIdExplosion = sp.play(soundIdExplosion, 1f, 1f, 0, 0, 2f)
		sp.pause(streamIdShot)
		try { TimeUnit.SECONDS.sleep(2) } catch (e: InterruptedException) { e.printStackTrace() }
		sp.resume(streamIdShot)
	}
	override fun onLoadComplete(soundPool: SoundPool?, sampleId: Int, status: Int) {
		Log.d(TAG, "onLoadComplete, sampleId = $sampleId, status = $status")
	}
}
