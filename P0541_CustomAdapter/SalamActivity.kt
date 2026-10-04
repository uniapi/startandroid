/* \uFDFD
 *                     \u0648\u0671\u0630\u0643\u0631 \u0631\u0628\u0651\u0643
 *        \u0643\u062A\u0628\u0647 \u0639\u0628\u062F \u0671\u0644\u0643\u0631\u064A\u0645
 *  \u06F1\u06F4\u06F4\u06F8 \u0631\u0628\u064A\u0639 \u0671\u0644\u0622\u062E\u0631 \u06F2\u06F3
 */
package localhost.idroid.salamun

import android.util.Log
import android.app.Activity
import android.os.Bundle
import android.view.View
import android.view.Gravity
import android.view.ViewGroup.LayoutParams
import android.widget.ListView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Button
import android.widget.Toast

class SalamActivity : Activity() {
    private companion object {
        val TAG = "localhost.idroid.salamun"
    }
	private val products = ArrayList<Product>()
	private lateinit var boxAdapter: BoxAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val rootLayout = LinearLayout(this).apply {
            layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
            orientation = LinearLayout.VERTICAL
        }
		val lvMain = ListView(this).apply {
            layoutParams = LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, 1f)
		}
		val btnBox = Button(this).apply {
			layoutParams = LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT).apply {
				gravity = Gravity.CENTER_HORIZONTAL
				setMargins(0, dpToPx(this@SalamActivity, 5f), 0, 0)
			}
			text = getString(R.string.box)
			setOnClickListener { showResults(it) }
		}
		with(rootLayout) {
			addView(lvMain)
			addView(btnBox)
		}
		setContentView(rootLayout)

		fillData()
		boxAdapter = BoxAdapter(this, products)
		lvMain.adapter = boxAdapter
	}
	private fun fillData() {
		for (i in 1..20)
			products.add(Product("Product $i", i * 1000, android.R.drawable.sym_def_app_icon, false))
	}
	fun showResults(view: View?) {
		val selectedProducts = boxAdapter.getBox().joinToString(separator = "\n") { it.name ?: "" }
		val result = if (selectedProducts.isEmpty()) "Корзина пуста" else "Товары в корзине:\n$selectedProducts"
		Toast.makeText(this, result, Toast.LENGTH_LONG).show()
	}
}
