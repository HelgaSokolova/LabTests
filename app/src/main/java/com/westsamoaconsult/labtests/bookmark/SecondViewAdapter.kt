package com.westsamoaconsult.labtests.bookmark

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.westsamoaconsult.labtests.database.CategoryItem
import kotlinx.android.synthetic.main.bookmark_item.view.*

class SecondViewAdapter(val mContext: Context, val categories: List<CategoryItem>, val listener: OnItemClickListener) :
    RecyclerView.Adapter<SecondViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        return ViewHolder(inflater.inflate(com.westsamoaconsult.labtests.R.layout.bookmark_item, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val category = categories[position]
        var logo = category.logo.replace('-', '_').toLowerCase()
        logo = logo.substring(0, logo.lastIndexOf("."))

        val imageId = mContext.resources.getIdentifier(logo, "drawable", mContext.getPackageName())
        holder.apply {
            imageView.setBackgroundResource(imageId)
            textLabel.text = category.name
            itemView.setOnClickListener {
                listener.onClick(category)
            }
        }
    }

    override fun getItemCount() = categories.size

//    View HOLDER
    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val imageView: ImageView = itemView.imageView
        val textLabel: TextView = itemView.textLabel
    }

    // OnClickListener
    interface OnItemClickListener {
        fun onClick(category: CategoryItem)
    }
}