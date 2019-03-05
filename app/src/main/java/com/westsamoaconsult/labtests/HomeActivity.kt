package com.westsamoaconsult.labtests

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.view.MenuItem
import com.westsamoaconsult.labtests.bookmark.FirstViewFragment
import com.westsamoaconsult.labtests.common.BaseActivity
import com.westsamoaconsult.labtests.info.InfoViewFragment
import com.westsamoaconsult.labtests.utils.Utils
import kotlinx.android.synthetic.main.main_activity.*


class HomeActivity : BaseActivity(), BottomNavigationView.OnNavigationItemSelectedListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        setTitle("Categories")

        //loading the default fragment
        Utils.replaceFragment(FirstViewFragment(), supportFragmentManager, R.id.fragmentContainer)

        //getting bottom navigation view and attaching the listener
        bottomNavigation.setOnNavigationItemSelectedListener(this)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        val fragment: Fragment

        when (item.itemId) {
            R.id.bookmarks -> {
                setTitle("Categories")
                fragment = FirstViewFragment()
            }
            else -> {
                setTitle("More")
                fragment = InfoViewFragment()
            }
        }

        return Utils.replaceFragment(fragment, supportFragmentManager, R.id.fragmentContainer)
    }

    override fun onBackPressed() {
        val count = supportFragmentManager.backStackEntryCount

        if (count == 0) {
            super.onBackPressed()
            //additional code
        } else {
            supportFragmentManager.popBackStack()
        }

    }
}
