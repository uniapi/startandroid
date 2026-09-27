/* \uFDFD
 *                     \u0648\u0671\u0630\u0643\u0631 \u0631\u0628\u0651\u0643
 *        \u0643\u062A\u0628\u0647 \u0639\u0628\u062F \u0671\u0644\u0643\u0631\u064A\u0645
 *  \u06F1\u06F4\u06F4\u06F8 \u0631\u0628\u064A\u0639 \u0671\u0644\u0622\u062E\u0631 \u06F1\u06F5
 */
package localhost.idroid.salamun

import android.util.Log
import android.content.Intent
import android.content.SharedPreferences
import android.content.SharedPreferences.Editor
import android.app.Activity
import android.os.Bundle
import android.view.View
import android.view.Gravity
import android.view.ViewGroup.LayoutParams
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.EditText
import android.widget.Button
import android.widget.Toast

class SalamActivity : Activity() {
    private companion object {
        val TAG = "localhost.idroid.salamun"
    }
	private lateinit var etText: EditText
	private val SAVED_TEXT = "saved_text"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val rootLayout = LinearLayout(this).apply {
            layoutParams = LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT
            )
            orientation = LinearLayout.VERTICAL
        }
		etText = EditText(this).apply {
			layoutParams = LinearLayout.LayoutParams(
				LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT
			)
			requestFocus()
		}
		val layout = LinearLayout(this).apply {
			layoutParams = LinearLayout.LayoutParams(
				LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT
			)
			orientation = LinearLayout.HORIZONTAL
		}
		val btnSave = Button(this).apply {
			layoutParams = LinearLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT
			)
			text = "Save"
		}
		val btnLoad = Button(this).apply {
			layoutParams = LinearLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT
			)
			text = "Load"
		}
		with(layout) {
			addView(btnSave)
			addView(btnLoad)
		}
		with(rootLayout) {
			addView(etText)
			addView(layout)
		}
		setContentView(rootLayout)
		btnSave.setOnClickListener { _: View? ->
			saveText()
		}
		btnLoad.setOnClickListener { _: View? ->
			loadText()
		}
		loadText()
	}
	override fun onDestroy() {
		super.onDestroy()
		saveText()
	}
	private fun saveText() {
		val sPref = getPreferences(MODE_PRIVATE)
		with(sPref.edit()) {
			putString(SAVED_TEXT, etText.text?.toString() ?: "")
			commit()
			Toast.makeText(this@SalamActivity, "Text saved", Toast.LENGTH_SHORT).show()
		}
	}
	private fun loadText() {
		val sPref = getPreferences(MODE_PRIVATE)
		val savedText = sPref.getString(SAVED_TEXT, "")
		etText.setText(savedText)
		Toast.makeText(this, "Text loaded", Toast.LENGTH_SHORT).show()
	}
}
