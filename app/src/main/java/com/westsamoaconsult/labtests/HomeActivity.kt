package com.westsamoaconsult.labtests

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v7.app.ActionBar
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import com.westsamoaconsult.labtests.bookmark.FirstViewFragment
import com.westsamoaconsult.labtests.info.InfoViewFragment
import com.westsamoaconsult.labtests.utils.FragmentUtils
import kotlinx.android.synthetic.main.action_bar.*
import kotlinx.android.synthetic.main.main_activity.*


class HomeActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportActionBar!!.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM)
        supportActionBar!!.setCustomView(R.layout.action_bar)
        actionBarTitle.text = "Categories"

        setContentView(R.layout.main_activity)

        //loading the default fragment
        FragmentUtils.addFragment(FirstViewFragment(), supportFragmentManager, R.id.fragmentContainer)

        //getting bottom navigation view and attaching the listener
        bottomNavigation.setOnNavigationItemSelectedListener(this)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        val fragment: Fragment

        when (item.itemId) {
            R.id.bookmarks -> {
                actionBarTitle.text = "Categories"
                fragment = FirstViewFragment()
            }
            else -> {
                actionBarTitle.text = "More"
                fragment = InfoViewFragment()
            }
        }

        return FragmentUtils.replaceFragment(fragment, supportFragmentManager, R.id.fragmentContainer)
    }
}
