/* \uFDFD
 *		  			   \u0648\u0671\u0630\u0643\u0631 \u0631\u0628\u0651\u0643
 *		  \u0643\u062A\u0628\u0647 \u0639\u0628\u062F \u0671\u0644\u0643\u0631\u064A\u0645
 *  \u06F1\u06F4\u06F4\u06F8 \u0631\u0628\u064A\u0639 \u0671\u0644\u0622\u062E\u0631 \u06F2\u06F7
 */
package localhost.idroid.salamun

import android.os.Bundle
import android.preference.CheckBoxPreference
import android.preference.ListPreference
import android.preference.Preference
import android.preference.Preference.OnPreferenceClickListener
import android.preference.PreferenceCategory
import android.preference.PreferenceActivity
import android.preference.PreferenceScreen

class PrefActivity : PreferenceActivity() {
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		val rootScreen: PreferenceScreen = getPreferenceManager().createPreferenceScreen(this)
		setPreferenceScreen(rootScreen)

		val chb1 = CheckBoxPreference(this).apply {
			setKey("chb1")
			setTitle("CheckBox 1")
			setSummaryOn("Description of checkbox 1 on")
			setSummaryOff("Description of checkbox 1 off")
		}
		rootScreen.addPreference(chb1)
		val list = ListPreference(this).apply {
			setKey("list")
			setTitle("List")
			setSummary("Description of list")
			setEntries(R.array.entries)
			setEntryValues(R.array.entry_values)
		}
		rootScreen.addPreference(list)
		val chb2 = CheckBoxPreference(this).apply {
			setKey("chb2")
			setTitle("CheckBox 2")
			setSummary("Description of checkbox 2")
		}
		rootScreen.addPreference(chb2)
		val screen = getPreferenceManager().createPreferenceScreen(this).apply {
			setKey("screen")
			setTitle("Screen")
			setSummary("Description of screen")
		}
		val chb3 = CheckBoxPreference(this).apply {
			setKey("chb3")
			setTitle("CheckBox 3")
			setSummary("Description of checkbox 3")
		}
		screen.addPreference(chb3)

		val categ1 = PreferenceCategory(this).apply {
			setKey("categ1")
			setTitle("Category 1")
			setSummary("Description of category 1")
		}
		screen.addPreference(categ1)
		val chb4 = CheckBoxPreference(this).apply {
			setKey("chb4")
			setTitle("CheckBox 4")
			setSummary("Description of checkbox 4")
		}
		categ1.addPreference(chb4)

		val categ2 = PreferenceCategory(this).apply {
			setKey("categ2")
			setTitle("Category 2")
			setSummary("Description of category 2")
		}
		screen.addPreference(categ2)
		val chb5 = CheckBoxPreference(this).apply {
			setKey("chb5")
			setTitle("CheckBox 5")
			setSummary("Description of checkbox 5")
		}
		categ2.addPreference(chb5)
		val chb6 = CheckBoxPreference(this).apply {
			setKey("chb6")
			setTitle("CheckBox 6")
			setSummary("Description of checkbox 6")
		}
		categ2.addPreference(chb6)

		rootScreen.addPreference(screen)
		list.setDependency("chb1")
		screen.setDependency("chb2")

		categ2.setEnabled(chb3.isChecked())
		chb3.setOnPreferenceClickListener { preference: Preference? ->
			categ2.setEnabled(chb3.isChecked())
			false
		}
	}
}
