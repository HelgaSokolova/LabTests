package com.westsamoaconsult.labtests.bookmark.detail

import android.support.v7.widget.RecyclerView
import android.view.View
import kotlinx.android.synthetic.main.detail_item_header.view.*
import kotlinx.android.synthetic.main.detail_item_image.view.*


class HeaderDetailViewHolder(itemView: View): RecyclerView.ViewHolder(itemView),
    DetailViewAdapter.DetailItemViewHolder {
    override fun bindViews(item: DetailViewItem) {
        item as HeaderDetailItem
        var logo = item.imageName.replace('-', '_').toLowerCase()
        logo = logo.substring(0, logo.lastIndexOf("."))

        itemView.apply {
            firstText.text = item.description
            firstText.viewTreeObserver.addOnGlobalLayoutListener {
                secondText.text = firstText.text.subSequence(firstText.layout
                    .getEllipsisStart(0), firstText.text.length).toString()
            }

            val imageId = context.resources.getIdentifier(logo, "drawable", context.packageName)
            headerImage.setBackgroundResource(imageId)
        }
    }
}