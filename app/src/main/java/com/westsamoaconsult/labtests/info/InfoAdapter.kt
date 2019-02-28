package com.westsamoaconsult.labtests.info

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.westsamoaconsult.labtests.R
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
            InfoItem.TYPE.SECTION -> SectionViewHolder(inflater.inflate(R.layout.info_item_section, parent, false))
            InfoItem.TYPE.RANGE -> RangeViewHolder(inflater.inflate(R.layout.info_item_range, parent, false))
            InfoItem.TYPE.TEXTSIZE -> TextSizeViewHolder(inflater.inflate(R.layout.info_item_text_size, parent, false))
            else -> SectionViewHolder(inflater.inflate(R.layout.info_item_section, parent, false))
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as InfoItemViewHolder).bindViews(infoList[position])
    }

    override fun getItemCount() = infoList.size

//    View Holders
    interface InfoItemViewHolder {
        fun bindViews(item: InfoItem)
    }

    class SectionViewHolder(itemView: View): RecyclerView.ViewHolder(itemView), InfoItemViewHolder {
        override fun bindViews(item: InfoItem) {
            val mItem = item as SectionInfoItem
            itemView.sectionTitle.text = mItem.description
        }
    }

    class RangeViewHolder(itemView: View): RecyclerView.ViewHolder(itemView), InfoItemViewHolder {
        override fun bindViews(item: InfoItem) {
            val mItem = item as RangeInfoItem

            itemView.segmentGroup.check(mItem.defaultCheckId)
            itemView.segmentGroup.setOnCheckedChangeListener(mItem.listener)
        }
    }

    class TextSizeViewHolder(itemView: View): RecyclerView.ViewHolder(itemView), InfoItemViewHolder {
        override fun bindViews(item: InfoItem) {
            val mItem = item as TextSizeInfoItem

            itemView.segmentGroupText.check(mItem.defaultCheckId)
            itemView.segmentGroupText.setOnCheckedChangeListener(mItem.listener)
        }
    }

}