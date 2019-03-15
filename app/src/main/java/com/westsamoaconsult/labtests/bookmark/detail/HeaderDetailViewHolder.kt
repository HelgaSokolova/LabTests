package com.westsamoaconsult.labtests.bookmark.detail

import android.support.v7.widget.RecyclerView
import android.view.View
import kotlinx.android.synthetic.main.detail_item_header.view.*


class HeaderDetailViewHolder(itemView: View): RecyclerView.ViewHolder(itemView),
    DetailViewAdapter.DetailItemViewHolder {
    override fun bindViews(item: DetailViewItem) {
        item as HeaderDetailItem
        var logo = item.imageName.replace('-', '_').toLowerCase()
        logo = logo.substring(0, logo.lastIndexOf("."))

        itemView.apply {
            firstText.text = item.description
            firstText.viewTreeObserver.addOnGlobalLayoutListener {
                firstText.apply {
                    val lastVisibleLineNumber = layout.getLineForVertical(scrollY + height)
                    val end = layout.getLineEnd(lastVisibleLineNumber)
                    itemView.secondText.text = item.description.substring(end, item.description.length)
                    if (itemView.secondText.text.isEmpty()) {
                        itemView.secondText.visibility = View.GONE
                    }
                }
            }

            val imageId = context.resources.getIdentifier(logo, "drawable", context.packageName)
            headerImage.setBackgroundResource(imageId)

            btnImage.setOnClickListener(item.listener)
        }
    }
}