package com.westsamoaconsult.labtests

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.view.MenuItem
import com.westsamoaconsult.labtests.tabs.bookmark.first.FirstViewFragment
import com.westsamoaconsult.labtests.common.BaseActivity
import com.westsamoaconsult.labtests.common.BaseFragment
import com.westsamoaconsult.labtests.tabs.favorite.FavoriteViewFragment
import com.westsamoaconsult.labtests.tabs.info.InfoViewFragment
import com.westsamoaconsult.labtests.tabs.recent.RecentsViewFragment
import com.westsamoaconsult.labtests.tabs.search.SearchViewFragment
import com.westsamoaconsult.labtests.utils.Utils
import kotlinx.android.synthetic.main.action_bar.view.*
import kotlinx.android.synthetic.main.main_activity.*


class HomeActivity : BaseActivity(), BottomNavigationView.OnNavigationItemSelectedListener, FragmentManager.OnBackStackChangedListener {
    private var prevId = R.id.bookmarks
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        leftActionBar.apply { toolbar.post { actionBarTitle.maxWidth = (toolbar.width * 0.7).toInt() } }
        rightActionBar?.apply { toolbar.post { actionBarTitle.maxWidth = (toolbar.width * 0.7).toInt() } }

        supportFragmentManager!!.addOnBackStackChangedListener(this)

        Utils.replaceFragment(FirstViewFragment(), supportFragmentManager, R.id.fragmentLeftContainer)
        bottomNavigation.setOnNavigationItemSelectedListener(this)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        if (prevId == item.itemId) {
            return true
        }
        prevId = item.itemId
        val fragment: Fragment

        when (item.itemId) {
            R.id.bookmarks -> { fragment = FirstViewFragment() }
            R.id.favorite -> { fragment = FavoriteViewFragment() }
            R.id.recent -> { fragment = RecentsViewFragment() }
            R.id.search -> { fragment = SearchViewFragment() }
            else -> { fragment = InfoViewFragment() }
        }

        return Utils.replaceFragment(fragment, supportFragmentManager, R.id.fragmentLeftContainer)
    }

    override fun onBackStackChanged() {
        val fragment = supportFragmentManager!!.findFragmentById(R.id.fragmentLeftContainer)
        fragment?.let {
            (fragment as BaseFragment).onForeground()
        }
    }
}
