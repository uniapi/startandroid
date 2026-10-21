/* \uFDFD
 *                           \u0648\u0671\u0630\u0643\u0631 \u0631\u0628\u0651\u0643
 *              \u0643\u062A\u0628\u0647 \u0639\u0628\u062F \u0671\u0644\u0643\u0631\u064A\u0645
 *  \u06F1\u06F4\u06F4\u06F8 \u062C\u0645\u0627\u062F\u0649 \u0627\u0644\u0623\u0648\u0644\u0649 \u06F9
 */
package localhost.idroid.salamun

import android.util.Log
import android.app.Activity
import android.os.Bundle
import android.view.ActionMode
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ListView
import android.widget.ArrayAdapter
import android.widget.AbsListView
import android.widget.CheckBox
/*
class SalamActivity : Activity() {
	private companion object {
		const val TAG = "localhost.idroid.salamun"
	}
	private var actionMode: ActionMode? = null

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.main)
	}
	override fun onCreateOptionsMenu(menu: Menu?): Boolean {
		menuInflater.inflate(R.menu.main, menu)
		return true
	}
	fun onClick(view: View?) {
		if (actionMode == null)
			actionMode = startActionMode(callback)
		else
			actionMode?.finish()
	}
	private val callback = object : ActionMode.Callback {
		override fun onCreateActionMode(mode: ActionMode?, menu: Menu?): Boolean {
			mode?.menuInflater?.inflate(R.menu.context, menu)
			return true
		}
		override fun onPrepareActionMode(mode: ActionMode?, menu: Menu?): Boolean {
			return false
		}
		override fun onActionItemClicked(mode: ActionMode?, item: MenuItem?): Boolean {
			Log.d(TAG, "item: ${item?.title}")
			return false
		}
		override fun onDestroyActionMode(mode: ActionMode?) {
			Log.d(TAG, "destroy")
			actionMode = null
		}
	}
}
*/
class SalamActivity : Activity() {
	private companion object {
		const val TAG = "localhost.idroid.salamun"
	}
	private var actionMode: ActionMode? = null
	private lateinit var lvActionMode: ListView
	private val data = arrayOf("one", "two", "three", "four", "five")

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.main)

		val adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_activated_1, data)
		lvActionMode = findViewById(R.id.lvActionMode) as ListView
		lvActionMode.adapter = adapter
		lvActionMode.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL)
		lvActionMode.setMultiChoiceModeListener(object : AbsListView.MultiChoiceModeListener {
			override fun onCreateActionMode(mode: ActionMode?, menu: Menu?): Boolean {
				mode?.menuInflater?.inflate(R.menu.context, menu)
				return true
			}
			override fun onPrepareActionMode(mode: ActionMode?, menu: Menu?): Boolean {
				return false
			}
			override fun onActionItemClicked(mode: ActionMode?, item: MenuItem?): Boolean {
				mode?.finish()
				return false
			}
			override fun onDestroyActionMode(mode: ActionMode?) {

			}
			override fun onItemCheckedStateChanged(mode: ActionMode?, position: Int, id: Long, checked: Boolean) {
				Log.d(TAG, "position = $position, checked = $checked")
			}
		})
	}
	override fun onCreateOptionsMenu(menu: Menu?): Boolean {
		menuInflater.inflate(R.menu.main, menu)
		return true
	}
}
