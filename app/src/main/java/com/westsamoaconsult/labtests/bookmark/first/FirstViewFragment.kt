package com.westsamoaconsult.labtests.bookmark.first

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.westsamoaconsult.labtests.MainApplication
import com.westsamoaconsult.labtests.R
import com.westsamoaconsult.labtests.bookmark.second.SecondViewFragment
import com.westsamoaconsult.labtests.common.BaseActivity
import com.westsamoaconsult.labtests.common.BaseFragment
import com.westsamoaconsult.labtests.database.CategoryItem
import com.westsamoaconsult.labtests.utils.Utils
import kotlinx.android.synthetic.main.info_fragment.*

class FirstViewFragment: BaseFragment(),
    FirstViewAdapter.OnItemClickListener {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.bookmark_first_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onForeground()

        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.adapter = FirstViewAdapter(context!!, MainApplication.instance.database.allCategories, this)
    }

    override fun onClick(category: CategoryItem) {
        Utils.addFragment(
            SecondViewFragment.newInstance(
                category.autoId,
                category.name
            ), activity!!.supportFragmentManager, R.id.fragmentContainer)
    }

    override fun onForeground() {
        super.onForeground()

        (activity as BaseActivity).setTitle("Categories")
        (activity as BaseActivity).setBackButtonVisible(false)
    }
}
