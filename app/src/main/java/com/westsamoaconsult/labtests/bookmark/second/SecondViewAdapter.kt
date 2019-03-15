package com.westsamoaconsult.labtests.bookmark.second

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.westsamoaconsult.labtests.R
import com.westsamoaconsult.labtests.database.ArticleItem
import kotlinx.android.synthetic.main.item_default.view.*

class SecondViewAdapter(private val mContext: Context, private val articles: List<ArticleItem>, val listener: OnItemClickListener) :
    RecyclerView.Adapter<SecondViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        return ViewHolder(
            inflater.inflate(
                R.layout.item_default,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val article = articles[position]

        holder.apply {
            val scale = mContext.resources.displayMetrics.density
            val pixels = (60 * scale + 0.5f)
            viewLayout.layoutParams.height = pixels.toInt()

            divider.setBackgroundResource(R.color.colorPrimary)

            title.text = article.name
            article.subtitle?.let {
                subTitle.text = it
                subTitle.visibility = View.VISIBLE
            }

            itemView.setOnClickListener {
                listener.onClick(article)
            }
        }
    }

    override fun getItemCount() = articles.size

//    View HOLDER
    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val viewLayout = itemView.viewLayout!!
        val title = itemView.title!!
        val subTitle = itemView.subTitle!!
        val divider = itemView.divider!!
    }

    // OnClickListener
    interface OnItemClickListener {
        fun onClick(article: ArticleItem)
    }
}