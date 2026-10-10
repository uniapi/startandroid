/* \uFDFD
 *                     \u0648\u0671\u0630\u0643\u0631 \u0631\u0628\u0651\u0643
 *        \u0643\u062A\u0628\u0647 \u0639\u0628\u062F \u0671\u0644\u0643\u0631\u064A\u0645
 *  \u06F1\u06F4\u06F4\u06F8 \u0631\u0628\u064A\u0639 \u0671\u0644\u0622\u062E\u0631 \u06F2\u06F8
 */
package localhost.idroid.salamun

import java.util.concurrent.TimeUnit
import java.util.Random

import android.util.Log
import android.app.Activity
import android.os.Handler
import android.os.Message
import android.os.Bundle
import android.view.View
import android.view.Gravity
import android.view.ViewGroup.LayoutParams
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Button
import android.widget.ProgressBar

class SalamActivity : Activity(), View.OnClickListener {
	private companion object {
		const val TAG = "localhost.idroid.salamun"
		const val STATUS_NONE = 0			// no connection
		const val STATUS_CONNECTING = 1
		const val STATUS_CONNECTED = 2
		const val STATUS_DOWNLOAD_START = 3
		const val STATUS_DOWNLOAD_FILE = 4
		const val STATUS_DOWNLOAD_END = 5
		const val STATUS_DOWNLOAD_NONE = 6	// no files to download
	}
	private lateinit var h: Handler
	private lateinit var tvStatus: TextView
	private lateinit var pbDownload: ProgressBar
	private lateinit var btnConnect: Button

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		val rootLayout = LinearLayout(this).apply {
			layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
			orientation = LinearLayout.VERTICAL
		}
		val params = LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
		btnConnect = Button(this).apply { text = getString(R.string.connect); setOnClickListener(this@SalamActivity) }
		tvStatus = TextView(this)
		pbDownload = ProgressBar(this, null, android.R.attr.progressBarStyleHorizontal).apply {
			layoutParams = LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
			isIndeterminate = false
			visibility = View.VISIBLE
		}
		with(rootLayout) {
			addView(btnConnect, params)
			addView(tvStatus, params)
			addView(pbDownload)
		}
		setContentView(rootLayout)

		h = object : Handler() {
			override fun handleMessage(msg: Message) {
				when (msg.what) {
					STATUS_NONE -> {
						btnConnect.setEnabled(true)
						tvStatus.text = "Not connected"
						pbDownload.visibility = View.GONE
					}
					STATUS_CONNECTING -> {
						btnConnect.setEnabled(false)
						tvStatus.text = "Connecting"
					}
					STATUS_CONNECTED -> {
						tvStatus.text = "Connected"
					}
					STATUS_DOWNLOAD_START -> {
						tvStatus.text = "Start download ${msg.arg1} files"
						with(pbDownload) {
							max = msg.arg1
							progress = 0
							visibility = View.VISIBLE
						}
					}
					STATUS_DOWNLOAD_FILE -> {
						tvStatus.text = "Downloading. Left ${msg.arg2} files"
						pbDownload.progress = msg.arg1
						saveFile(msg.obj as ByteArray)
					}
					STATUS_DOWNLOAD_END -> {
						tvStatus.text = "Download complete!"
					}
					STATUS_DOWNLOAD_NONE -> {
						tvStatus.text = "No files for download"
					}
				}
			}
		}
		h.sendEmptyMessage(STATUS_NONE)
	}
	override fun onClick(view: View?) {
		Thread {
			var msg: Message
			var file: ByteArray
			val rand = Random()
			try {
				h.sendEmptyMessage(STATUS_CONNECTING)
				TimeUnit.SECONDS.sleep(1)
				h.sendEmptyMessage(STATUS_CONNECTED)
				TimeUnit.SECONDS.sleep(1)
				val filesCount = rand.nextInt(5)
				if (filesCount == 0) {
					h.sendEmptyMessage(STATUS_DOWNLOAD_NONE)
					TimeUnit.MILLISECONDS.sleep(1500)
					h.sendEmptyMessage(STATUS_NONE)
					return@Thread
				}
				msg = h.obtainMessage(STATUS_DOWNLOAD_START, filesCount, 0)
				h.sendMessage(msg)
				for (i in 1..filesCount) {
					file = downloadFile()
					msg = h.obtainMessage(STATUS_DOWNLOAD_FILE, i, filesCount - i, file)
					h.sendMessage(msg)
				}
				h.sendEmptyMessage(STATUS_DOWNLOAD_END)
				TimeUnit.MILLISECONDS.sleep(1500)
				h.sendEmptyMessage(STATUS_NONE)
			} catch (e: InterruptedException) {
				e.printStackTrace()
			}
		}.start()
	}
	private fun downloadFile(): ByteArray {
		TimeUnit.SECONDS.sleep(2)
		return ByteArray(1024)
	}
	private fun saveFile(file: ByteArray) {

	}
}
