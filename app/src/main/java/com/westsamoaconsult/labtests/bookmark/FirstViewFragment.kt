package com.westsamoaconsult.labtests.bookmark

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.westsamoaconsult.labtests.MainApplication
import com.westsamoaconsult.labtests.R
import com.westsamoaconsult.labtests.database.CategoryItem
import com.westsamoaconsult.labtests.utils.FragmentUtils
import kotlinx.android.synthetic.main.info_fragment.*

class FirstViewFragment: Fragment(), FirstViewAdapter.OnItemClickListener {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.bookmark_first_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.adapter = FirstViewAdapter(context!!, MainApplication.instance.database.allCategories, this)
    }

    override fun onClick(category: CategoryItem) {
        FragmentUtils.addFragment(SecondViewFragment.newInstance(category.autoId, category.name), activity!!.supportFragmentManager, R.id.fragmentContainer)
    }
}
