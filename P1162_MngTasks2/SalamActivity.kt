/* \uFDFD
 *                           \u0648\u0671\u0630\u0643\u0631 \u0631\u0628\u0651\u0643
 *              \u0643\u062A\u0628\u0647 \u0639\u0628\u062F \u0671\u0644\u0643\u0631\u064A\u0645
 *  \u06F1\u06F4\u06F4\u06F8 \u062C\u0645\u0627\u062F\u0649 \u0627\u0644\u0623\u0648\u0644\u0649 \u06F1\u06F2
 */
package localhost.idroid.salama

import android.util.Log
import android.app.Activity
import android.app.ActivityManager
import android.app.ActivityManager.RunningTaskInfo
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.ViewGroup.LayoutParams
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Button

class SalamActivity : Activity() {
	private companion object {
		const val TAG = "localhost.idroid.salamun"
	}
	var list: List<RunningTaskInfo>? = null
	var am: ActivityManager? = null

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		val llRoot = LinearLayout(this).apply {
			layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
			orientation = LinearLayout.VERTICAL
		}
		val btnStart = Button(this).apply {
			layoutParams = LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
			text = getString(R.string.start)
			setOnClickListener { view: View? -> onClick(view) }
		}
		val btnInfo = Button(this).apply {
			layoutParams = LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
			text = getString(R.string.info)
			setOnClickListener { view: View? -> onInfoClick(view) }
		}
		with(llRoot) {
			addView(btnStart)
			addView(btnInfo)
		}
		setContentView(llRoot)

		setTitle(resources.getString(R.string.app_name) + " : " + getLocalClassName())
		am = getSystemService(ACTIVITY_SERVICE) as ActivityManager
	}
	fun onInfoClick(v: View?) {
		list = am?.getRunningTasks(10)
		val localList = list ?: return
		for (task in localList) {
			if (task.baseActivity.flattenToShortString().startsWith("localhost.idroid.salam")) {
				Log.d(TAG, "--------------")
				Log.d(TAG, "Count: ${task.numActivities}")
				Log.d(TAG, "Root: ${task.baseActivity.flattenToShortString()}")
				Log.d(TAG, "Top: ${task.topActivity?.flattenToShortString()}")
			}
		}
	}
	fun onClick(view: View?) {
		try {
			startActivity(
				Intent("mngtasks1_activity_c")
					.addFlags(Intent.FLAG_ACTIVITY_TASK_ON_HOME)
					.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
			)
		} catch (e: Exception) {
			Log.d(TAG, "error: ${e.localizedMessage}")
		}
	}
}
