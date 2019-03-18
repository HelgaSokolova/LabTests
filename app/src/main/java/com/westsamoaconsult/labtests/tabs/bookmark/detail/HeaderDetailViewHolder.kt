package com.westsamoaconsult.labtests.tabs.bookmark.detail

import android.support.v7.widget.RecyclerView
import android.view.View
import kotlinx.android.synthetic.main.detail_item_header.view.*
import android.R.attr.scrollY
import android.R.attr.scrollY






class HeaderDetailViewHolder(itemView: View): RecyclerView.ViewHolder(itemView),
    DetailViewAdapter.DetailItemViewHolder {
    override fun bindViews(item: DetailViewItem) {
        item as HeaderDetailItem
        var logo = item.imageName.replace('-', '_').toLowerCase()
        logo = logo.replace(' ', '_').toLowerCase()
        logo = logo.substring(0, logo.lastIndexOf("."))

        itemView.apply {
            firstText.text = item.description
            firstText.viewTreeObserver.addOnGlobalLayoutListener {
                firstText.apply {
                    val lastVisibleLineNumber = height / lineHeight - 1
                    val end = layout.getLineEnd(lastVisibleLineNumber)
                    text = item.description.substring(0, end)
                    itemView.secondText.text = item.description.substring(end, item.description.length)
                    if (itemView.secondText.text.isEmpty()) {
                        itemView.secondText.visibility = View.GONE
                    }
                }
            }

            firstText.viewTreeObserver.addOnPreDrawListener {
                val lineCounts = firstText.layout.lineCount
                var k = 5
                true
            }

            val imageId = context.resources.getIdentifier(logo, "drawable", context.packageName)
            headerImage.setImageResource(imageId)

            btnImage.setOnClickListener(item.listener)
        }
    }
}