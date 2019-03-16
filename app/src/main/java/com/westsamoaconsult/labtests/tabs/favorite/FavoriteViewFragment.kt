package com.westsamoaconsult.labtests.tabs.favorite

import android.icu.text.Normalizer.NO
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
import com.westsamoaconsult.labtests.tabs.recent.RecentsViewAdapter
import com.westsamoaconsult.labtests.utils.Utils
import kotlinx.android.synthetic.main.info_fragment.*


class FavoriteViewFragment: BaseFragment(), SecondViewAdapter.OnItemClickListener {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(com.westsamoaconsult.labtests.R.layout.bookmark_first_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        onForeground()
        recyclerView.layoutManager = LinearLayoutManager(activity)
    }

    override fun onClick(articleId: Int) {
        Utils.addFragment(DetailViewFragment.newInstance(articleId), activity!!.supportFragmentManager, R.id.fragmentContainer)
    }

    override fun onForeground() {
        super.onForeground()

        (activity as BaseActivity).apply {
            setTitle("Favorites")
            setRightButtonVisible(true, "Edit")
        }

        recyclerView.adapter = FavoriteViewAdapter(MainApplication.instance.database.getFavorites(), this)
    }

    override fun onRightButtonPressed() {
//        [self.tableView setEditing:!self.tableView.editing animated:YES];
//
//        if (self.tableView.editing)
//            [self.navigationItem.rightBarButtonItem setTitle:@"Done"];
//        else
//        [self.navigationItem.rightBarButtonItem setTitle:@"Edit"];
    }
}
