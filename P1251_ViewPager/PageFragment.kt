/* \uFDFD
 *                           \u0648\u0671\u0630\u0643\u0631 \u0631\u0628\u0651\u0643
 *              \u0643\u062A\u0628\u0647 \u0639\u0628\u062F \u0671\u0644\u0643\u0631\u064A\u0645
 *  \u06F1\u06F4\u06F4\u06F8 \u062C\u0645\u0627\u062F\u0649 \u0627\u0644\u0623\u0648\u0644\u0649 \u06F1\u06F7
 */
package localhost.idroid.salamun

import java.util.Random

import android.util.Log
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.View
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import android.graphics.Color

class PageFragment : Fragment() {
	companion object {
		private const val TAG = "localhost.idroid.salamun"
		private const val ARGUMENT_PAGE_NUMBER = "arg_page_numer"
		private const val SAVE_PAGE_NUMBER = "save_page_number"
		@JvmStatic
		fun newInstance(page: Int): PageFragment {
			val pageFragment = PageFragment()
			val arguments = Bundle().apply {
				putInt(ARGUMENT_PAGE_NUMBER, page)
			}
			pageFragment.arguments = arguments
			return pageFragment
		}
		private var pageNumber: Int = 0
		private var backColor: Int = 0
	}
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		pageNumber = arguments?.getInt(ARGUMENT_PAGE_NUMBER) ?: 0
		Log.d(TAG, "onCreate: $pageNumber")
		val rnd = Random()
		backColor = Color.argb(40, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256))
		var savedPageNumber = -1
		if (savedInstanceState != null) {
			savedPageNumber = savedInstanceState.getInt(SAVE_PAGE_NUMBER)
		}
		Log.d(TAG, "savedPageNumber = $savedPageNumber")
	}
	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
		val view = inflater.inflate(R.layout.fragment, null)
		val tvPage = view.findViewById(R.id.tvPage) as TextView
		tvPage.text = "Page: $pageNumber"
		tvPage.setBackgroundColor(backColor)
		return view
	}
	override fun onSaveInstanceState(outState: Bundle) {
		super.onSaveInstanceState(outState)
		outState.putInt(SAVE_PAGE_NUMBER, pageNumber)
	}
	override fun onDestroy() {
		super.onDestroy()
		Log.d(TAG, "onDestroy: $pageNumber")
	}
}
