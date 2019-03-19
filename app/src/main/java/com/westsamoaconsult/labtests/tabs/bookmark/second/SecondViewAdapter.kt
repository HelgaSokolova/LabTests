package com.westsamoaconsult.labtests.tabs.bookmark.second

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.westsamoaconsult.labtests.R
import com.westsamoaconsult.labtests.database.ArticleItem
import com.westsamoaconsult.labtests.utils.Utils
import kotlinx.android.synthetic.main.item_default.view.*

class SecondViewAdapter(private val articles: List<ArticleItem>, val listener: OnItemClickListener) :
    RecyclerView.Adapter<SecondViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        return ViewHolder(inflater.inflate(R.layout.item_default, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val article = articles[position]

        holder.itemView.apply {
            viewLayout.layoutParams.height = Utils.intToDp(60, context)
            defaultDivider.setBackgroundResource(R.color.colorPrimary)

            title.text = article.name
            article.subtitle?.let {
                subTitle.text = it
                subTitle.visibility = View.VISIBLE
            }

            setOnClickListener {
                listener.onClick(article.itemId)
            }
        }
    }

    override fun getItemCount() = articles.size

//    View HOLDER
    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){}

    // OnClickListener
    interface OnItemClickListener {
        fun onClick(articleId: Int)
    }
}