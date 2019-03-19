package com.westsamoaconsult.labtests.tabs.recent

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
import com.westsamoaconsult.labtests.utils.Utils
import kotlinx.android.synthetic.main.info_fragment.*


class RecentsViewFragment: BaseFragment(), SecondViewAdapter.OnItemClickListener {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.bookmark_first_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        onForeground()
        recyclerView.layoutManager = LinearLayoutManager(activity)
    }

    override fun onClick(articleId: Int) {
        Utils.addFragment(DetailViewFragment.newInstance(articleId), activity!!.supportFragmentManager, R.id.fragmentLeftContainer)
    }

    override fun onForeground() {
        super.onForeground()

        (activity as BaseActivity).apply {
            setTitle("Recents")
            setRightButtonVisible(true, "Clear")
        }

        recyclerView.adapter = SecondViewAdapter(MainApplication.instance.database.getRecents(), this)
    }

    override fun onRightButtonPressed() {
        MainApplication.instance.database.clearRecents()
        recyclerView.adapter = SecondViewAdapter(MainApplication.instance.database.getRecents(), this)
    }
}
