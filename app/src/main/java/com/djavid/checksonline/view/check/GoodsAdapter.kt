package com.djavid.checksonline.view.check

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.djavid.checksonline.R
import com.djavid.checksonline.model.entities.Item
import kotlinx.android.synthetic.main.item_good.view.*

class GoodsAdapter(
		private val context: Context
) : RecyclerView.Adapter<GoodsAdapter.ViewHolder>() {
	
	private var goods = mutableListOf<Item>()
	
	fun showGoods(items: List<Item>) {
		goods = items.toMutableList()
		notifyDataSetChanged()
	}
	
	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
		val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_good, parent, false)
		return ViewHolder(itemView)
	}
	
	override fun getItemCount() = goods.size
	
	override fun onBindViewHolder(holder: ViewHolder, position: Int) {
		val good = goods[position]
		holder.goodName.text = good.name?.trim() ?: context.getString(R.string.no_title)
		holder.totalSum.text = context.getString(R.string.format_price).format(good.sum / 100f)
		holder.quantity.text = context.getString(R.string.format_quantity).format(good.quantity.toString(), good.price / 100f)
		holder.categoryName.text = good.category
	}
	
	class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
		val goodName: TextView = itemView.goodName
		val categoryName: TextView = itemView.categoryName
		val totalSum: TextView = itemView.totalSum
		val quantity: TextView = itemView.quantity
	}
	
}