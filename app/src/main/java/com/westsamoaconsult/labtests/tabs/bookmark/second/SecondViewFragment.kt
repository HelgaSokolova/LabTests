package com.westsamoaconsult.labtests.tabs.bookmark.second

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.westsamoaconsult.labtests.MainApplication
import com.westsamoaconsult.labtests.R
import com.westsamoaconsult.labtests.tabs.bookmark.detail.DetailViewFragment
import com.westsamoaconsult.labtests.common.BaseActivity
import com.westsamoaconsult.labtests.common.BaseFragment
import com.westsamoaconsult.labtests.utils.Utils
import kotlinx.android.synthetic.main.info_fragment.*


class SecondViewFragment: BaseFragment(),
    SecondViewAdapter.OnItemClickListener {
    companion object {
        fun newInstance(categoryId: Int, categoryName: String) = SecondViewFragment().apply {
            arguments = Bundle().apply {
                putInt("categoryId", categoryId)
                putString("categoryName", categoryName)
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(com.westsamoaconsult.labtests.R.layout.bookmark_first_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView.layoutManager = LinearLayoutManager(activity)
        val articles = MainApplication.instance.database.getItemsOfCategory(arguments!!.getInt("categoryId"))
        recyclerView.adapter = SecondViewAdapter(context!!, articles, this)
    }

    override fun onClick(articleId: Int) {
        Utils.addFragment(DetailViewFragment.newInstance(articleId), activity!!.supportFragmentManager, R.id.fragmentContainer)
    }

    override fun onForeground() {
        super.onForeground()

        (activity as BaseActivity).setTitle(arguments!!.getString("categoryName")!!)
        (activity as BaseActivity).setBackButtonVisible(true)
    }
}
