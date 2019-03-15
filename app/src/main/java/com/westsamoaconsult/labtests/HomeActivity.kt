package com.westsamoaconsult.labtests

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.view.MenuItem
import com.westsamoaconsult.labtests.bookmark.first.FirstViewFragment
import com.westsamoaconsult.labtests.common.BaseActivity
import com.westsamoaconsult.labtests.common.BaseFragment
import com.westsamoaconsult.labtests.info.InfoViewFragment
import com.westsamoaconsult.labtests.recent.RecentsViewFragment
import com.westsamoaconsult.labtests.utils.Utils
import kotlinx.android.synthetic.main.main_activity.*


class HomeActivity : BaseActivity(), BottomNavigationView.OnNavigationItemSelectedListener, FragmentManager.OnBackStackChangedListener{
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
        val fragment: Fragment

        when (item.itemId) {
            R.id.bookmarks -> { fragment = FirstViewFragment() }
            R.id.recent -> { fragment = RecentsViewFragment() }
            else -> { fragment = InfoViewFragment() }
        }

        return Utils.replaceFragment(fragment, supportFragmentManager, R.id.fragmentContainer)
    }

    override fun onBackStackChanged() {
        val fragment = supportFragmentManager!!.findFragmentById(com.westsamoaconsult.labtests.R.id.fragmentContainer)
        (fragment as BaseFragment).onForeground()
    }

    override fun onRightButtonPressed() {
        val fragment = supportFragmentManager!!.findFragmentById(com.westsamoaconsult.labtests.R.id.fragmentContainer)
        (fragment as BaseFragment).onRightButtonPressed()
    }
}
