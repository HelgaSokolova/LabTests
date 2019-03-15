package com.westsamoaconsult.labtests.bookmark.detail

import android.graphics.Color
import android.graphics.Typeface
import android.support.v7.widget.RecyclerView
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.westsamoaconsult.labtests.R
import kotlinx.android.synthetic.main.detail_item_image.view.*
import kotlinx.android.synthetic.main.detail_item_text.view.*
import kotlinx.android.synthetic.main.info_item_section.view.*

class DetailViewAdapter(private val detailList: List<DetailViewItem>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun getItemViewType(position: Int): Int {
        return detailList[position].type
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        return when (viewType) {
            DetailViewItem.SECTION -> SectionViewHolder(inflater.inflate(R.layout.info_item_section, parent, false))
            DetailViewItem.HEADER -> HeaderDetailViewHolder(inflater.inflate(R.layout.detail_item_header, parent, false))
            DetailViewItem.IMAGE -> ImageViewHolder(inflater.inflate(R.layout.detail_item_image, parent, false))
            else -> TextViewHolder(inflater.inflate(R.layout.detail_item_text, parent, false))
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        holder as DetailItemViewHolder
        holder.bindViews(detailList[position])
    }

    override fun getItemCount() = detailList.size


//    View Holders
    interface DetailItemViewHolder {
        fun bindViews(item: DetailViewItem)
    }

    class SectionViewHolder(itemView: View): RecyclerView.ViewHolder(itemView),
        DetailItemViewHolder {
        override fun bindViews(item: DetailViewItem) {
            item as SectionDetailItem
            itemView.apply {
                var height = 30
                if (!item.isFirst) {
                    topBar.visibility = View.VISIBLE
                    height = 50
                }
                viewLayout.layoutParams.height = (height * context.resources.displayMetrics.density + 0.5f).toInt()

                sectionTitle.apply {
                    setBackgroundColor(Color.parseColor("#fcfcfd"))
                    text = item.description
                    setTypeface(typeface, Typeface.BOLD)
                    setPadding(paddingLeft, 0, paddingRight, paddingBottom)
                    gravity = Gravity.CENTER_VERTICAL
                }

                divider.visibility = View.GONE
            }
        }
    }

    class ImageViewHolder(itemView: View): RecyclerView.ViewHolder(itemView),
        DetailItemViewHolder {
        override fun bindViews(item: DetailViewItem) {
            item as ImageDetailItem
            var logo = item.imageName.replace('-', '_').toLowerCase()
            logo = logo.substring(0, logo.lastIndexOf("."))

            itemView.apply {
                val imageId = context.resources.getIdentifier(logo, "drawable", context.packageName)
                imageView.setBackgroundResource(imageId)
            }
        }
    }

    class TextViewHolder(itemView: View): RecyclerView.ViewHolder(itemView),
        DetailItemViewHolder {
        override fun bindViews(item: DetailViewItem) {
            item as TextDetailItem
            itemView.apply {
                text.text = item.description.replace("\n", "\r\n")
            }
        }
    }
}