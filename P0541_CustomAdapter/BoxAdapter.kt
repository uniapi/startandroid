/* \uFDFD
 *                     \u0648\u0671\u0630\u0643\u0631 \u0631\u0628\u0651\u0643
 *        \u0643\u062A\u0628\u0647 \u0639\u0628\u062F \u0671\u0644\u0643\u0631\u064A\u0645
 *  \u06F1\u06F4\u06F4\u06F8 \u0631\u0628\u064A\u0639 \u0671\u0644\u0622\u062E\u0631 \u06F2\u06F3
 */
package localhost.idroid.salamun

import android.content.Context
//import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams
import android.view.Gravity
import android.widget.BaseAdapter
import android.widget.LinearLayout
import android.widget.CheckBox
import android.widget.CompoundButton
import android.widget.CompoundButton.OnCheckedChangeListener
import android.widget.ImageView
import android.widget.TextView

class BoxAdapter(
	private val ctx: Context,
	private val objects: ArrayList<Product>
) : BaseAdapter() {
//	private val lInflater: LayoutInflater = //LayoutInflater.from(ctx)
//		ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
	override fun getCount(): Int = objects.size
	override fun getItem(position: Int): Any = objects[position]
	override fun getItemId(position: Int): Long = position.toLong()
// Version with LayoutInflater and item.xml
/*	override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
		val view = convertView ?: lInflater.inflate(R.layout.item, parent, false)
		val p = getProduct(position)
		(view.findViewById(R.id.tvDescr) as TextView).text = p.name
		(view.findViewById(R.id.tvPrice) as TextView).text = p.price.toString()
		(view.findViewById(R.id.ivImage) as ImageView).setImageResource(p.image)
		val cbBuy = (view.findViewById(R.id.cbBox) as CheckBox).apply {
			setOnCheckedChangeListener(myCheckChangeList)
			tag = position
			isChecked = p.box
		}
		return view
	}*/
// Version with pure-code
	override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
		val view = convertView ?: LinearLayout(ctx).apply {
			layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
			orientation = LinearLayout.HORIZONTAL
			// cbBox
			addView(CheckBox(ctx).apply {
				layoutParams = LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
			})
			// llInner
			addView(LinearLayout(ctx).apply {
				layoutParams = LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, 1f).apply {
					setMargins(dpToPx(ctx, 5f), 0, 0, 0)
				}
				orientation = LinearLayout.VERTICAL
				// tvDescr
				addView(TextView(ctx).apply {
					layoutParams = LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT).apply {
						setMargins(0, dpToPx(ctx, 5f), 0, 0)
					}
					textSize = 20f
				})
				// tvPrice
				addView(TextView(ctx).apply {
					layoutParams = LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT).apply {
						gravity = Gravity.RIGHT
						setMargins(0, 0, dpToPx(ctx, 10f), 0)
					}
				})
			})
			// ivImage
			addView(ImageView(ctx).apply {
				layoutParams = LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
				setImageResource(android.R.drawable.sym_def_app_icon)
			})
		}
		val  p = getProduct(position)
		val rootLayout = view as ViewGroup
		(rootLayout.getChildAt(0) as CheckBox).apply {
			setOnCheckedChangeListener(myCheckChangeList)
			tag = position
			isChecked = p.box
		}
		val innerLayout = rootLayout.getChildAt(1) as ViewGroup
		(innerLayout.getChildAt(0) as TextView).text = p.name
		(innerLayout.getChildAt(1) as TextView).text = p.price.toString()
		(rootLayout.getChildAt(2) as ImageView).setImageResource(p.image)
		return view
	}
	fun getProduct(position: Int): Product = getItem(position) as Product
	fun getBox(): ArrayList<Product> {
		val box = ArrayList<Product>()
		for (p in objects)
			if (p.box)
				box.add(p)
		return box
	}
	val myCheckChangeList = OnCheckedChangeListener { buttonView: CompoundButton?, isChecked: Boolean ->
		val tag = buttonView?.tag
		if (tag is Int)
			getProduct(tag).box = isChecked
	}
}
