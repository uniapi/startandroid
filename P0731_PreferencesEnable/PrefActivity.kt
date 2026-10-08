/* \uFDFD
 *		  			   \u0648\u0671\u0630\u0643\u0631 \u0631\u0628\u0651\u0643
 *		  \u0643\u062A\u0628\u0647 \u0639\u0628\u062F \u0671\u0644\u0643\u0631\u064A\u0645
 *  \u06F1\u06F4\u06F4\u06F8 \u0631\u0628\u064A\u0639 \u0671\u0644\u0622\u062E\u0631 \u06F2\u06F7
 */
package localhost.idroid.salamun

import android.os.Bundle
import android.preference.CheckBoxPreference
import android.preference.Preference
import android.preference.Preference.OnPreferenceClickListener
import android.preference.PreferenceCategory
import android.preference.PreferenceActivity

class PrefActivity : PreferenceActivity() {
	private lateinit var chb3: CheckBoxPreference
	private lateinit var categ2: PreferenceCategory

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		addPreferencesFromResource(R.xml.pref)

		chb3 = findPreference("chb3") as CheckBoxPreference
		categ2 = findPreference("categ2") as PreferenceCategory
		categ2.setEnabled(chb3.isChecked())

		chb3.setOnPreferenceClickListener { preference: Preference? ->
			categ2.setEnabled(chb3.isChecked())
			false
		}
	}
}
