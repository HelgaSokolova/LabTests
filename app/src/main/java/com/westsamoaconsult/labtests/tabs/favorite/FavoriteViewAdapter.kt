package com.westsamoaconsult.labtests.tabs.favorite

import android.icu.text.Normalizer.NO
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.daimajia.swipe.adapters.RecyclerSwipeAdapter
import com.westsamoaconsult.labtests.MainApplication
import com.westsamoaconsult.labtests.R
import com.westsamoaconsult.labtests.database.ArticleItem
import com.westsamoaconsult.labtests.tabs.bookmark.second.SecondViewAdapter
import kotlinx.android.synthetic.main.favorite_item.view.*

class FavoriteViewAdapter(private val favorites: ArrayList<ArticleItem>, val listener: SecondViewAdapter.OnItemClickListener) :
    RecyclerSwipeAdapter<FavoriteViewAdapter.ViewHolder>() {

    override fun getSwipeLayoutResourceId(position: Int) = R.id.swipe

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        return ViewHolder(inflater.inflate(R.layout.favorite_item, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val article = favorites[position]

        holder.itemView.apply {
            title.text = article.name
            article.subtitle?.let {
                subTitle.text = it
                subTitle.visibility = View.VISIBLE
            }

            viewLayout.setOnClickListener {
                listener.onClick(article.itemId)
            }

            rightLayout.setOnClickListener {
                mItemManger.removeShownLayouts(swipe)
                favorites.removeAt(position)
                notifyItemRemoved(position)
                notifyItemRangeChanged(position, favorites.size)
                mItemManger.closeAllItems()

                article.isFavorite = false
                MainApplication.instance.database.saveFavorites()
            }

            mItemManger.bindView(this, position)
        }
    }

    override fun getItemCount() = favorites.size

//    View HOLDER
    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){}
}