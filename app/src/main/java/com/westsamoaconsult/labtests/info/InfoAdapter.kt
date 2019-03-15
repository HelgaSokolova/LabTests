package com.westsamoaconsult.labtests.info

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.westsamoaconsult.labtests.R
import kotlinx.android.synthetic.main.item_default.view.*
import kotlinx.android.synthetic.main.info_item_range.view.*
import kotlinx.android.synthetic.main.info_item_section.view.*
import kotlinx.android.synthetic.main.info_item_text_size.view.*

class InfoAdapter(val infoList: List<InfoItem>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun getItemViewType(position: Int): Int {
        return infoList[position].type
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        return when (viewType) {
            InfoItem.SECTION -> SectionViewHolder(inflater.inflate(R.layout.info_item_section, parent, false))
            InfoItem.RANGE -> RangeViewHolder(inflater.inflate(R.layout.info_item_range, parent, false))
            InfoItem.TEXTSIZE -> TextSizeViewHolder(inflater.inflate(R.layout.info_item_text_size, parent, false))
            else -> DefaultViewHolder(inflater.inflate(R.layout.item_default, parent, false))
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        holder as InfoItemViewHolder
        holder.bindViews(infoList[position])
    }

    override fun getItemCount() = infoList.size

//    View Holders
    interface InfoItemViewHolder {
        fun bindViews(item: InfoItem)
    }

    class SectionViewHolder(itemView: View): RecyclerView.ViewHolder(itemView), InfoItemViewHolder {
        override fun bindViews(item: InfoItem) {
            item as SectionInfoItem
            itemView.sectionTitle.text = item.description
        }
    }

    class RangeViewHolder(itemView: View): RecyclerView.ViewHolder(itemView), InfoItemViewHolder {
        override fun bindViews(item: InfoItem) {
            item as RangeInfoItem

            itemView.segmentGroup.apply {
                check(item.defaultCheckId)
                setOnCheckedChangeListener(item.listener)
            }
        }
    }

    class TextSizeViewHolder(itemView: View): RecyclerView.ViewHolder(itemView), InfoItemViewHolder {
        override fun bindViews(item: InfoItem) {
            item as TextSizeInfoItem

            itemView.segmentGroupText.apply {
                check(item.defaultCheckId)
                setOnCheckedChangeListener(item.listener)
            }
        }
    }

    class DefaultViewHolder(itemView: View): RecyclerView.ViewHolder(itemView), InfoItemViewHolder {
        override fun bindViews(item: InfoItem) {
            item as DefaultInfoItem

            itemView.apply {
                title.text = item.description
                setOnClickListener {
                    item.listener.onClick(item.description)
                }
            }
        }
    }


    // OnClickListener
    interface OnItemClickListener {
        fun onClick(description: String)
    }
}