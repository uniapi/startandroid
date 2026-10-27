/* \uFDFD
 *                           \u0648\u0671\u0630\u0643\u0631 \u0631\u0628\u0651\u0643
 *              \u0643\u062A\u0628\u0647 \u0639\u0628\u062F \u0671\u0644\u0643\u0631\u064A\u0645
 *  \u06F1\u06F4\u06F4\u06F8 \u062C\u0645\u0627\u062F\u0649 \u0627\u0644\u0623\u0648\u0644\u0649 \u06F1\u06F6
 */
package localhost.idroid.salamun

import android.util.Log
import android.content.Intent
import android.widget.RemoteViewsService

class MyService : RemoteViewsService() {
	companion object {
		private const val TAG = "localhost.idroid.salamun"
	}
	override fun onGetViewFactory(intent: Intent): RemoteViewsFactory {
		Log.d(TAG, "MyService: onGetViewFactory")
		return MyFactory(getApplicationContext(), intent)
	}
}
