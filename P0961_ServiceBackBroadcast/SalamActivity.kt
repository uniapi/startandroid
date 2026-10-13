/* \uFDFD
 *                           \u0648\u0671\u0630\u0643\u0631 \u0631\u0628\u0651\u0643
 *              \u0643\u062A\u0628\u0647 \u0639\u0628\u062F \u0671\u0644\u0643\u0631\u064A\u0645
 *  \u06F1\u06F4\u06F4\u06F8 \u062C\u0645\u0627\u062F\u0649 \u0627\u0644\u0623\u0648\u0644\u0649 \u06F2
 */
package localhost.idroid.salamun

import android.util.Log
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.BroadcastReceiver
import android.os.Bundle
import android.view.View
import android.view.Gravity
import android.view.ViewGroup.LayoutParams
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Button

class SalamActivity : Activity() {
	companion object {
		const val TAG = "localhost.idroid.salamun"
		private const val TASK1_CODE = 1
		private const val TASK2_CODE = 2
		private const val TASK3_CODE = 3
		const val STATUS_START = 100
		const val STATUS_FINISH = 200
		const val PARAM_TIME = "time"
		const val PARAM_TASK = "task"
		const val PARAM_RESULT = "result"
		const val PARAM_STATUS = "status"
		const val BROADCAST_ACTION = "localhost.idroid.salamun"
	}
	private lateinit var tvTask1: TextView
	private lateinit var tvTask2: TextView
	private lateinit var tvTask3: TextView
	private lateinit var br: BroadcastReceiver

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		val llRoot = LinearLayout(this).apply {
			layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
			orientation = LinearLayout.VERTICAL
		}
		val textParams = LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
		tvTask1 = TextView(this).apply { text = "Task1" }
		tvTask2 = TextView(this).apply { text = "Task2" }
		tvTask3 = TextView(this).apply { text = "Task3" }
		val btnStart = Button(this).apply {
			layoutParams = LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
			text = getString(R.string.start)
			setOnClickListener(onClickStart)
		}
		with(llRoot) {
			addView(tvTask1, textParams)
			addView(tvTask2, textParams)
			addView(tvTask3, textParams)
			addView(btnStart)
		}
		setContentView(llRoot)

		br = object : BroadcastReceiver() {
			override fun onReceive(context: Context, intent: Intent) {
				val task = intent.getIntExtra(PARAM_TASK, 0)
				val status = intent.getIntExtra(PARAM_STATUS, 0)
				Log.d(TAG, "onReceive: task = $task, status = $status")
				when (status) {
					STATUS_START -> when (task) {
						TASK1_CODE -> tvTask1.text = "Task1 start"
						TASK2_CODE -> tvTask2.text = "Task2 start"
						TASK3_CODE -> tvTask3.text = "Task3 start"
					}
					STATUS_FINISH -> {
						val result = intent.getIntExtra(PARAM_RESULT, 0)
						when (task) {
							TASK1_CODE -> tvTask1.text = "Task1 finish, result = $result"
							TASK2_CODE -> tvTask2.text = "Task2 finish, result = $result"
							TASK3_CODE -> tvTask3.text = "Task3 finish, result = $result"
						}
					}
				}
			}
		}
		val intFilt = IntentFilter(BROADCAST_ACTION)
		registerReceiver(br, intFilt)
	}
	override fun onDestroy() {
		super.onDestroy()
		unregisterReceiver(br)
	}
	val onClickStart = View.OnClickListener { _: View? ->
		var intent = Intent(this, MyService::class.java).putExtra(PARAM_TIME, 7).putExtra(PARAM_TASK, TASK1_CODE)
		startService(intent)

		intent = Intent(this, MyService::class.java).putExtra(PARAM_TIME, 4).putExtra(PARAM_TASK, TASK2_CODE)
		startService(intent)

		intent = Intent(this, MyService::class.java).putExtra(PARAM_TIME, 6).putExtra(PARAM_TASK, TASK3_CODE)
		startService(intent)
	}
	override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
		super.onActivityResult(requestCode, resultCode, data)
		Log.d(TAG, "requestCode: $requestCode, resultCode = $resultCode")
		when (resultCode) {
		}
	}
}
