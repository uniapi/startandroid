/* \uFDFD
 *                           \u0648\u0671\u0630\u0643\u0631 \u0631\u0628\u0651\u0643
 *              \u0643\u062A\u0628\u0647 \u0639\u0628\u062F \u0671\u0644\u0643\u0631\u064A\u0645
 *  \u06F1\u06F4\u06F4\u06F8 \u062C\u0645\u0627\u062F\u0649 \u0627\u0644\u0623\u0648\u0644\u0649 \u06F1\u06F7
 */
package localhost.idroid.salamun

import android.util.Log
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import android.support.v4.view.PagerAdapter
import android.support.v4.view.ViewPager
import android.view.ViewGroup.LayoutParams
import android.widget.RelativeLayout


class SalamActivity : FragmentActivity() {
	companion object {
		private const val TAG = "localhost.idroid.salamun"
		private const val PAGE_COUNT = 10
	}
	private lateinit var pager: ViewPager
	private lateinit var pagerAdapter: PagerAdapter

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.main)
		pager = findViewById(R.id.pager) as ViewPager
		pagerAdapter = MyFragmentPagerAdapter(supportFragmentManager)
		pager.adapter = pagerAdapter
		pager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
			override fun onPageSelected(position: Int) {
				Log.d(TAG, "onPageSelected, position = $position")
			}
			override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

			}
			override fun onPageScrollStateChanged(state: Int) {

			}
		})
	}
	private inner class MyFragmentPagerAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {
		override fun getItem(position: Int): Fragment {
			return PageFragment.newInstance(position)
		}
		override fun getCount(): Int = PAGE_COUNT
		override fun getPageTitle(position: Int): CharSequence = "Title $position"
	}
}
