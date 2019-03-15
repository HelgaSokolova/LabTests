package com.westsamoaconsult.labtests.tabs.bookmark.detail

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import com.westsamoaconsult.labtests.R
import kotlinx.android.synthetic.main.detail_item_color.view.*

class ChangeColorAdapter(private val colors: Array<String>, private val listener: OnItemClickListener) :
    RecyclerView.Adapter<ChangeColorAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        return ViewHolder(inflater.inflate(R.layout.detail_item_color, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var color = colors[position].replace(' ', '_').toLowerCase()
        color = color.substring(0, color.lastIndexOf("."))

        holder.apply {
            val imageId = itemView.context.resources.getIdentifier(color, "drawable", itemView.context.packageName)
            imageView.setImageResource(imageId)

            btnImage.setOnClickListener {
                listener.onClick(colors[position])
            }
        }
    }

    override fun getItemCount() = colors.size

    //    View HOLDER
    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val btnImage: FrameLayout = itemView.btnImage
        val imageView: ImageView = itemView.imageView
    }

    // OnClickListener
    interface OnItemClickListener {
        fun onClick(color: String)
    }
}