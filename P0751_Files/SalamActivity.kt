/* \uFDFD
 *		 			   \u0648\u0671\u0630\u0643\u0631 \u0631\u0628\u0651\u0643
 *		  \u0643\u062A\u0628\u0647 \u0639\u0628\u062F \u0671\u0644\u0643\u0631\u064A\u0645
 *  \u06F1\u06F4\u06F4\u06F8 \u0631\u0628\u064A\u0639 \u0671\u0644\u0622\u062E\u0631 \u06F2\u06F7
 */
package localhost.idroid.salamun

import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.File
import java.io.FileNotFoundException
import java.io.FileReader
import java.io.FileWriter
import java.io.IOException
import java.io.InputStreamReader
import java.io.OutputStreamWriter

import android.util.Log
import android.app.Activity
import android.os.Environment
import android.os.Bundle
import android.view.View
import android.view.Gravity
import android.view.ViewGroup.LayoutParams
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Button

class SalamActivity : Activity(), View.OnClickListener {
	private companion object {
		const val TAG = "localhost.idroid.salamun"
		const val ID_BTN_WRITE = 1
		const val ID_BTN_READ = 2
		const val ID_BTN_WRITE_SD = 3
		const val ID_BTN_READ_SD = 4
	}
	private val FILENAME = "file"
	private val DIR_SD = "MyFiles"
	private val FILENAME_SD = "fileSD"

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		val rootLayout = LinearLayout(this).apply {
			layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
			orientation = LinearLayout.VERTICAL
		}
		val llParams = LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
		val btnParams = LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
		val btnWrite = Button(this).apply {
			id = ID_BTN_WRITE
			setText(R.string.write_file)
			setOnClickListener(this@SalamActivity)
		}
		val btnRead = Button(this).apply {
			id = ID_BTN_READ
			setText(R.string.read_file)
			setOnClickListener(this@SalamActivity)
		}
		val btnWriteSd = Button(this).apply {
			id = ID_BTN_WRITE_SD
			setText(R.string.write_file_sd)
			setOnClickListener(this@SalamActivity)
		}
		val btnReadSd = Button(this).apply {
			id = ID_BTN_READ_SD
			setText(R.string.read_file_sd)
			setOnClickListener(this@SalamActivity)
		}
		with(rootLayout) {
			addView(LinearLayout(this@SalamActivity).apply {
				addView(btnWrite, btnParams)
				addView(btnRead, btnParams)
			}, llParams)
			addView(LinearLayout(this@SalamActivity).apply {
				addView(btnWriteSd, btnParams)
				addView(btnReadSd, btnParams)
			}, llParams)
		}
		setContentView(rootLayout)
	}
	override fun onClick(view: View?) {
		when (view?.id) {
			ID_BTN_WRITE -> writeFile()
			ID_BTN_READ -> readFile()
			ID_BTN_WRITE_SD -> writeFileSD()
			ID_BTN_READ_SD -> readFileSD()
		}
	}
	private fun writeFile() {
		try {
			openFileOutput(FILENAME, MODE_PRIVATE).use { fos ->
				BufferedWriter(OutputStreamWriter(fos)).use { bw ->
					bw.write("Содержимое файла")
				}
			}
			Log.d(TAG, "Файл записан")
		}
		catch(e: FileNotFoundException) {
			e.printStackTrace()
		}
		catch(e: IOException) {
			e.printStackTrace()
		}
	}
	private fun readFile() {
		try {
			openFileInput(FILENAME).use { fis ->
				BufferedReader(InputStreamReader(fis)).use { br ->
					var str: String?
					while (br.readLine().also { str = it } != null) {
						Log.d(TAG, str ?: "")
					}
				}
			}
		}
		catch (e: FileNotFoundException) {
			e.printStackTrace()
		}
		catch (e: IOException) {
			e.printStackTrace()
		}
	}
	private fun writeFileSD() {
		// Check if SD is accessable
		if (Environment.getExternalStorageState() != Environment.MEDIA_MOUNTED) {
			Log.d(TAG, "SD-карта не доступна: ${Environment.getExternalStorageState()}")
			return
		}
		val sdPath = File(Environment.getExternalStorageDirectory(), DIR_SD)
		sdPath.mkdirs()
		val sdFile = File(sdPath, FILENAME_SD)
		try {
			BufferedWriter(FileWriter(sdFile)).use { bw ->
				bw.write("Содержимое файла на SD")
			}
			Log.d(TAG, "Содержимое файла на SD: ${sdFile.absolutePath}")
		}
		catch (e: IOException) {
			e.printStackTrace()
		}
	}
	private fun readFileSD() {
		// Check if SD is accessable
		if (Environment.getExternalStorageState() != Environment.MEDIA_MOUNTED) {
			Log.d(TAG, "SD-карта не доступна: ${Environment.getExternalStorageState()}")
			return
		}
		val sdPath = File(Environment.getExternalStorageDirectory(), DIR_SD)
		val sdFile = File(sdPath, FILENAME_SD)
		try {
			BufferedReader(FileReader(sdFile)).use { br ->
				var str: String?
				while (br.readLine().also { str = it } != null) {
					Log.d(TAG, str ?: "")
				}
			}
		}
		catch (e: FileNotFoundException) {
			e.printStackTrace()
		}
		catch (e: IOException) {
			e.printStackTrace()
		}
	}
}
