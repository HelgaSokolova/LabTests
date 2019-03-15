package com.westsamoaconsult.labtests.recent

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.westsamoaconsult.labtests.R
import com.westsamoaconsult.labtests.bookmark.second.SecondViewAdapter
import com.westsamoaconsult.labtests.database.ArticleItem
import kotlinx.android.synthetic.main.item_default.view.*

class RecentsViewAdapter(private val recents: List<ArticleItem>, val listener: SecondViewAdapter.OnItemClickListener) :
    RecyclerView.Adapter<RecentsViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        return ViewHolder(inflater.inflate(R.layout.item_default, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val article = recents[position]

        holder.apply {
            viewLayout.layoutParams.height = (60 * itemView.context.resources.displayMetrics.density + 0.5f).toInt()

            divider.setBackgroundResource(R.color.colorPrimary)

            title.text = article.name
            article.subtitle?.let {
                subTitle.text = it
                subTitle.visibility = View.VISIBLE
            }

            itemView.setOnClickListener {
                listener.onClick(article.itemId)
            }
        }
    }

    override fun getItemCount() = recents.size

//    View HOLDER
    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val viewLayout = itemView.viewLayout!!
        val title = itemView.title!!
        val subTitle = itemView.subTitle!!
        val divider = itemView.divider!!
    }
}