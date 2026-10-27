/* \uFDFD
 *                           \u0648\u0671\u0630\u0643\u0631 \u0631\u0628\u0651\u0643
 *              \u0643\u062A\u0628\u0647 \u0639\u0628\u062F \u0671\u0644\u0643\u0631\u064A\u0645
 *  \u06F1\u06F4\u06F4\u06F8 \u062C\u0645\u0627\u062F\u0649 \u0627\u0644\u0623\u0648\u0644\u0649 \u06F1\u06F6
 */
package localhost.idroid.salamun

import java.text.SimpleDateFormat
import java.util.Date

import android.util.Log
import android.appwidget.AppWidgetManager
import android.content.Intent
import android.content.Context
import android.widget.RemoteViews
import android.widget.RemoteViewsService.RemoteViewsFactory

class MyFactory(private val context: Context, intent: Intent) : RemoteViewsFactory {
	companion object {
		private const val TAG = "localhost.idroid.salamun"
	}
	init {
		Log.d(TAG, "MyFactory()")
	}
	private lateinit var data: ArrayList<String>
	private val sdf= SimpleDateFormat("HH:mm:ss")
	private val widgetId: Int = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID)

	override fun onCreate() {
		data = ArrayList()
	}
	override fun getCount(): Int = data.size
	override fun getItemId(position: Int): Long = position.toLong()
	override fun getLoadingView(): RemoteViews? = null
	override fun getViewAt(position: Int): RemoteViews {
		val rView = RemoteViews(context.packageName, R.layout.item)
		rView.setTextViewText(R.id.tvItemText, data[position])
		val clickIntent = Intent().apply {
			putExtra(MyProvider.ITEM_POSITION, position)
		}
		rView.setOnClickFillInIntent(R.id.tvItemText, clickIntent)
		Log.d(TAG, "MyFactory: getViewAt")
		return rView
	}
	override fun getViewTypeCount(): Int = 1
	override fun hasStableIds(): Boolean = true
	override fun onDataSetChanged() {
		data.clear()
		data.add(sdf.format(Date(System.currentTimeMillis())))
		data.add(this@MyFactory.hashCode().toString())
		data.add(widgetId.toString())
		for (i in 3 until 15)
			data.add("Item $i")
	}
	override fun onDestroy() {}
}
