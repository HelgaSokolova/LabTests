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
import com.westsamoaconsult.labtests.utils.Utils
import kotlinx.android.synthetic.main.main_activity.*


class HomeActivity : BaseActivity(), BottomNavigationView.OnNavigationItemSelectedListener, FragmentManager.OnBackStackChangedListener{
    private var prevId = -1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        supportFragmentManager!!.addOnBackStackChangedListener(this)

        //loading the default fragment
        Utils.replaceFragment(FirstViewFragment(), supportFragmentManager, R.id.fragmentContainer)

        //getting bottom navigation view and attaching the listener
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
            else -> { fragment = InfoViewFragment() }
        }

        return Utils.replaceFragment(fragment, supportFragmentManager, R.id.fragmentContainer)
    }

    override fun onBackStackChanged() {
        val fragment = supportFragmentManager!!.findFragmentById(R.id.fragmentContainer)
        (fragment as BaseFragment).onForeground()
    }

    override fun onRightButtonPressed() {
        val fragment = supportFragmentManager!!.findFragmentById(R.id.fragmentContainer)
        (fragment as BaseFragment).onRightButtonPressed()
    }
}
