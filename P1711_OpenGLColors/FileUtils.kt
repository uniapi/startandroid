/* \uFDFD
 *                           \u0648\u0671\u0630\u0643\u0631 \u0631\u0628\u0651\u0643
 *              \u0643\u062A\u0628\u0647 \u0639\u0628\u062F \u0671\u0644\u0643\u0631\u064A\u0645
 *  \u06F1\u06F4\u06F4\u06F8 \u062C\u0645\u0627\u062F\u0649 \u0627\u0644\u0622\u062E\u0631\u0629 \u06F1\u06F5
 */
package localhost.idroid.salamun

import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader

import android.content.Context
import android.content.res.Resources

object FileUtils {
	@JvmStatic
	fun readTextFromRaw(context: Context, resourceId: Int): String {
		val stringBuilder = StringBuilder()
		try {
			context.resources.openRawResource(resourceId).bufferedReader().use { reader ->
				var line: String?
				while (reader.readLine().also { line = it } != null) {
					stringBuilder.append(line)
					stringBuilder.append("\r\n")
				}
			}
		} catch (ioex: IOException) {
			ioex.printStackTrace()
		} catch (nfex: Resources.NotFoundException) {
			nfex.printStackTrace()
		}
		return stringBuilder.toString()
	}
}
