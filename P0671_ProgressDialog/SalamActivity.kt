/* \uFDFD
 *					 \u0648\u0671\u0630\u0643\u0631 \u0631\u0628\u0651\u0643
 *		\u0643\u062A\u0628\u0647 \u0639\u0628\u062F \u0671\u0644\u0643\u0631\u064A\u0645
 *  \u06F1\u06F4\u06F4\u06F8 \u0631\u0628\u064A\u0639 \u0671\u0644\u0622\u062E\u0631 \u06F2\u06F6
 */
package localhost.idroid.salamun

import android.util.Log
import android.app.Activity
import android.app.Dialog
import android.app.ProgressDialog
import android.content.DialogInterface
import android.content.DialogInterface.OnClickListener
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.view.View
import android.view.Gravity
import android.view.ViewGroup.LayoutParams
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Button

class SalamActivity : Activity(), View.OnClickListener {
	private companion object {
		const val TAG = "localhost.idroid.salamun"
		const val ID_BTN_DEFAULT = 1
		const val ID_BTN_HORIZ = 2
	}
	private var pd: ProgressDialog? = null
	private var h: Handler? = null

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		val rootLayout = LinearLayout(this).apply {
			layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
			orientation = LinearLayout.VERTICAL
		}
		val btnDefault = Button(this).apply {
			id = ID_BTN_DEFAULT
			layoutParams = LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
			text = getString(R.string.dflt)
			setOnClickListener(this@SalamActivity)
		}
		val btnHoriz = Button(this).apply {
			id = ID_BTN_HORIZ
			layoutParams = LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
			text = getString(R.string.horiz)
			setOnClickListener(this@SalamActivity)
		}
		with(rootLayout) {
			addView(btnDefault)
			addView(btnHoriz)
		}
		setContentView(rootLayout)
	}
	override fun onClick(view: View?) {
		when (view?.id) {
			ID_BTN_DEFAULT -> {
				pd = ProgressDialog(this).apply {
					setTitle("Title")
					setMessage("Message")
					setButton(Dialog.BUTTON_POSITIVE, "OK") { dialog: DialogInterface, which: Int -> }
					show()
				}
			}
			ID_BTN_HORIZ -> {
				pd = ProgressDialog(this).apply {
					setTitle("Title")
					setMessage("Message")
					setProgressStyle(ProgressDialog.STYLE_HORIZONTAL)
					max = 2148
					isIndeterminate = true
					show()
				}
				h = object : Handler(Looper.getMainLooper()) {
					override fun handleMessage(msg: Message) {
						pd?.apply {
							isIndeterminate = false
							if (progress < max) {
								incrementProgressBy(50)
								incrementSecondaryProgressBy(75)
								h?.sendEmptyMessageDelayed(0, 100)
							}
							else {
								dismiss()
							}
						}
					}
				}
				h?.sendEmptyMessageDelayed(0, 2000)
			}
		}
	}
}
