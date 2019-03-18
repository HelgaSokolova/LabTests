package com.westsamoaconsult.labtests.tabs.search

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.westsamoaconsult.labtests.MainApplication
import com.westsamoaconsult.labtests.R
import com.westsamoaconsult.labtests.tabs.bookmark.detail.DetailViewFragment
import com.westsamoaconsult.labtests.tabs.bookmark.second.SecondViewAdapter
import com.westsamoaconsult.labtests.common.BaseActivity
import com.westsamoaconsult.labtests.common.BaseFragment
import com.westsamoaconsult.labtests.database.ArticleItem
import com.westsamoaconsult.labtests.utils.Utils
import kotlinx.android.synthetic.main.action_bar.*
import kotlinx.android.synthetic.main.info_fragment.*


class SearchViewFragment: BaseFragment(), SecondViewAdapter.OnItemClickListener {
    var searchResults = arrayListOf<ArticleItem>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.bookmark_first_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView.layoutManager = LinearLayoutManager(activity)
        searchResults.addAll(MainApplication.instance.database.allArticlesSorted)
        onForeground()

    }

    override fun onClick(articleId: Int) {
        Utils.addFragment(DetailViewFragment.newInstance(articleId), activity!!.supportFragmentManager, R.id.fragmentContainer)
    }

    override fun onForeground() {
        super.onForeground()

        (activity as BaseActivity).apply {
            setTitle("Search")
            onSearchChanged(searchText)
            if (searchText.isEmpty()) {
                setRightButtonVisible(true, R.drawable.search)
            } else {
                setSearchBarVisible(true)
                setRightButtonVisible(true, "Cancel")
            }
        }
    }

    override fun onRightButtonPressed() {
        (activity as BaseActivity).apply {
            if (getSearchBarVisible()) {
                clearSearchBarText()
                setRightButtonVisible(true, R.drawable.search)
            } else {
                setRightButtonVisible(true, "Cancel")
            }
            setSearchBarVisible(!getSearchBarVisible())
        }
    }

    override fun onSearchChanged(text: String) {
        searchResults.clear()
        searchResults.addAll(MainApplication.instance.database.allArticlesSorted.filter {
            it.name.toLowerCase().contains(text.toLowerCase())
                    || it.subtitle?.toLowerCase()?.contains(text.toLowerCase()) ?: false
        })

        recyclerView.adapter = SecondViewAdapter(searchResults, this)
    }
}
