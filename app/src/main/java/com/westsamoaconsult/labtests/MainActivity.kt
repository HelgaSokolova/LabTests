package com.westsamoaconsult.labtests

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.view.MenuItem
import com.westsamoaconsult.labtests.information.InfoViewFragment
import com.westsamoaconsult.labtests.utils.FragmentUtils
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //loading the default fragment
        FragmentUtils.addFragment(InfoViewFragment(), supportFragmentManager, R.id.fragmentContainer)

        //getting bottom navigation view and attaching the listener
        bottomNavigation.setOnNavigationItemSelectedListener(this)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        val fragment: Fragment

        when (item.itemId) {
            R.id.more -> fragment = InfoViewFragment()
            else -> fragment = InfoViewFragment()
        }

        return FragmentUtils.replaceFragment(fragment, supportFragmentManager, R.id.fragmentContainer)
    }
}
