/* \uFDFD
 *                     \u0648\u0671\u0630\u0643\u0631 \u0631\u0628\u0651\u0643
 *        \u0643\u062A\u0628\u0647 \u0639\u0628\u062F \u0671\u0644\u0643\u0631\u064A\u0645
 *  \u06F1\u06F4\u06F4\u06F8 \u0631\u0628\u064A\u0639 \u0671\u0644\u0622\u062E\u0631 \u06F2\u06F8
 */
package localhost.idroid.salamun

import java.io.IOException
import java.io.StringReader

import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserException
import org.xmlpull.v1.XmlPullParserFactory

import android.util.Log
import android.text.TextUtils
import android.app.Activity
import android.os.Bundle
import android.view.View
import android.view.Gravity
import android.view.ViewGroup.LayoutParams
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Button

class SalamActivity : Activity() {
	private companion object {
		const val TAG = "localhost.idroid.salamun"
	}
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		val rootLayout = LinearLayout(this).apply {
			layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
			orientation = LinearLayout.VERTICAL
		}
		setContentView(rootLayout)

		try {
			val xpp = prepareXpp()
			while (xpp.eventType != XmlPullParser.END_DOCUMENT) {
				when (xpp.eventType) {
					XmlPullParser.START_DOCUMENT -> Log.d(TAG, "START_DOCUMENT")
					XmlPullParser.START_TAG -> {
						Log.d(TAG, "START_TAG: name = ${xpp.name}, depth = ${xpp.depth}, attrCount = ${xpp.attributeCount}")
						val attributes = (0 until xpp.attributeCount).joinToString(", ") { index ->
							"${xpp.getAttributeName(index)} = ${xpp.getAttributeValue(index)}"
						}
						if (attributes.isNotEmpty())
							Log.d(TAG, "Attributes: $attributes")
					}
					XmlPullParser.END_TAG -> Log.d(TAG, "END_TAG: name = ${xpp.name}")
					XmlPullParser.TEXT -> Log.d(TAG, "text = ${xpp.text}")
				}
				xpp.next()
			}
			Log.d(TAG, "END_DOCUMENT")
		}
		catch (e: XmlPullParserException) {
			e.printStackTrace()
		}
		catch (e: IOException) {
			e.printStackTrace()
		}
	}
//	private fun prepareXpp(): XmlPullParser = getResources().getXml(R.xml.data)
	private fun prepareXpp(): XmlPullParser {
		val factory = XmlPullParserFactory.newInstance().apply {
			isNamespaceAware = true		// switching on namespace support
		}
		return factory.newPullParser().apply {
			setInput(StringReader("<data><phone><company>Samsung</company></phone></data>"))
		}
	}
}
