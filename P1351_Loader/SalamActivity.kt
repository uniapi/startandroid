/* \uFDFD
 *                           \u0648\u0671\u0630\u0643\u0631 \u0631\u0628\u0651\u0643
 *              \u0643\u062A\u0628\u0647 \u0639\u0628\u062F \u0671\u0644\u0643\u0631\u064A\u0645
 *  \u06F1\u06F4\u06F4\u06F8 \u062C\u0645\u0627\u062F\u0649 \u0627\u0644\u0622\u062E\u0631\u0629 \u06F2
 */
package localhost.idroid.salamun

import android.util.Log
import android.app.Activity
import android.app.LoaderManager.LoaderCallbacks
import android.content.Loader
import android.database.ContentObserver
import android.os.Bundle
import android.view.View
import android.view.Gravity
import android.view.ViewGroup.LayoutParams
import android.widget.RelativeLayout
import android.widget.RadioGroup
import android.widget.RadioButton
import android.widget.TextView
import android.widget.Button

class SalamActivity : Activity(), LoaderCallbacks<String> {
	companion object {
		private const val TAG = "localhost.idroid.salamun"
		const val LOADER_TIME_ID = 1
		var lastCheckedId = 0
	}
	private lateinit var tvTime: TextView
	private lateinit var rgTimeFormat: RadioGroup

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		val pxOfDp10 = dpToPx(this, 10f)
		val rlRoot = RelativeLayout(this).apply {
			layoutParams = RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT).apply {
				setMargins(pxOfDp10, pxOfDp10, pxOfDp10, pxOfDp10)
			}
		}
		tvTime = TextView(this).apply {
			id = R.id.tvTime
			layoutParams = RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT).apply {
				setMargins(pxOfDp10, pxOfDp10, pxOfDp10, pxOfDp10)
			}
			setTextAppearance(this@SalamActivity, android.R.style.TextAppearance_Medium)
		}
		rgTimeFormat = RadioGroup(this).apply {
			id = R.id.rgTimeFormat
			layoutParams = RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT).apply {
				addRule(RelativeLayout.BELOW, R.id.tvTime)
			}
		}
		val rdShort = RadioButton(this).apply {
			id = R.id.rdShort
			layoutParams = RadioGroup.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
			text = getString(R.string.format_short)
		}
		val rdLong = RadioButton(this).apply {
			id = R.id.rdLong
			layoutParams = RadioGroup.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
			text = getString(R.string.format_long)
		}
		val btnGetTime = Button(this).apply {
			id = R.id.btnGetTime
			layoutParams = RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT).apply {
				addRule(RelativeLayout.BELOW, R.id.rgTimeFormat)
			}
			text= getString(R.string.get_time)
			setOnClickListener { getTimeClick(it) }
		}
		val btnObserver = Button(this).apply {
			id = R.id.btnObserver
			layoutParams = RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT).apply {
				addRule(RelativeLayout.BELOW, R.id.btnGetTime)
			}
			text = getString(R.string.observer)
			setOnClickListener { observerClick(it) }
		}
		with(rgTimeFormat) {
			addView(rdShort)
			addView(rdLong)
		}
		rgTimeFormat.check(R.id.rdShort)
		with(rlRoot) {
			addView(tvTime)
			addView(rgTimeFormat)
			addView(btnGetTime)
			addView(btnObserver)
		}
		setContentView(rlRoot)

		val bndl = Bundle()
		bndl.putString(TimeLoader.ARGS_TIME_FORMAT, getTimeFormat())
		loaderManager.initLoader(LOADER_TIME_ID, bndl, this)
		lastCheckedId = rgTimeFormat.checkedRadioButtonId
	}
	override fun onCreateLoader(id: Int, args: Bundle?): Loader<String>? {
		var loader: Loader<String>? = null
		if (id == LOADER_TIME_ID) {
			loader = TimeAsyncLoader(this, args)
			Log.d(TAG, "onCreateLoader: ${loader.hashCode()}")
		}
		return loader
	}
	override fun onLoadFinished(loader: Loader<String>, result: String?) {
		Log.d(TAG, "onLoadFinished for loader ${loader.hashCode()}, result = $result")
		tvTime.text = result
	}
	override fun onLoaderReset(loader: Loader<String>) {
		Log.d(TAG, "onLoaderReset for loader ${loader.hashCode()}")
	}
	fun getTimeClick(view: View?) {
		val loader: Loader<String>?
		val id = rgTimeFormat.checkedRadioButtonId
		if (id == lastCheckedId) {
			loader = loaderManager.getLoader(LOADER_TIME_ID)
		} else {
			val bndl = Bundle()
			bndl.putString(TimeLoader.ARGS_TIME_FORMAT, getTimeFormat())
			loader = loaderManager.restartLoader(LOADER_TIME_ID, bndl, this)
			lastCheckedId = id
		}
		loader?.forceLoad()
	}
	private fun getTimeFormat(): String {
		var result = TimeLoader.TIME_FORMAT_SHORT
		when (rgTimeFormat.checkedRadioButtonId) {
			R.id.rdShort -> result = TimeLoader.TIME_FORMAT_SHORT
			R.id.rdLong -> result = TimeLoader.TIME_FORMAT_LONG
		}
		return result
	}
	fun observerClick(view: View?) {
		Log.d(TAG, "observerClick")
		val loader = loaderManager.getLoader<String>(LOADER_TIME_ID) ?: return
		val observer: ContentObserver = loader.ForceLoadContentObserver()
		view?.postDelayed(object : Runnable {
			override fun run() {
				observer.dispatchChange(false)
			}
		}, 5000)
	}
}
