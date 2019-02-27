package com.westsamoaconsult.labtests.information

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.westsamoaconsult.labtests.R
import kotlinx.android.synthetic.main.info_item_section.view.*


class InfoAdapter(val infoList: List<InfoItem>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun getItemViewType(position: Int): Int {
        return infoList[position].type
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        return when (viewType) {
            InfoItem.TYPE.SECTION -> SectionViewHolder(inflater.inflate(R.layout.info_item_section, parent, false))
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

}