package com.westsamoaconsult.labtests

import android.app.PendingIntent.getActivity
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.view.MenuItem
import com.westsamoaconsult.labtests.common.BaseActivity
import com.westsamoaconsult.labtests.common.BaseFragment
import com.westsamoaconsult.labtests.tabs.bookmark.detail.DetailViewFragment
import com.westsamoaconsult.labtests.tabs.bookmark.first.FirstViewFragment
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

        leftActionBar.apply {
            toolbar.viewTreeObserver.addOnGlobalLayoutListener {
                actionBarTitle.maxWidth = (toolbar.width - Utils.dpToPixel(100.toFloat(), this@HomeActivity) ).toInt()
            }
        }
        rightActionBar?.apply {
            toolbar.viewTreeObserver.addOnGlobalLayoutListener {
                actionBarTitle.maxWidth = (toolbar.width - Utils.dpToPixel(100.toFloat(), this@HomeActivity) ).toInt()
            }
        }

        savedInstanceState?.let{
            prevId = savedInstanceState.getInt("prevId")
            rightActionBar?.let {
                val fragment = supportFragmentManager!!.findFragmentById(R.id.fragmentLeftContainer)
                if (fragment != null && fragment is DetailViewFragment) {
                    supportFragmentManager.popBackStackImmediate()
                    Utils.replaceFragment(fragment, supportFragmentManager, R.id.fragmentRightContainer)
                }
            } ?: run {
                val leftFragment = supportFragmentManager!!.findFragmentById(R.id.fragmentLeftContainer)
                val fragment = supportFragmentManager!!.findFragmentById(R.id.fragmentRightContainer)
                if (fragment != null && fragment is DetailViewFragment && !(leftFragment is InfoViewFragment)) {
                    Utils.removeFragment(fragment, supportFragmentManager)
                    Utils.addFragment(fragment, supportFragmentManager, R.id.fragmentLeftContainer, true)
                }
            }
        } ?: run {
            Utils.replaceFragment(FirstViewFragment(), supportFragmentManager, R.id.fragmentLeftContainer)
        }

        supportFragmentManager!!.addOnBackStackChangedListener(this)
        bottomNavigation.setOnNavigationItemSelectedListener(this)
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)

        outState!!.putInt("prevId", prevId)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        return buildNavigation(item.itemId)
    }

    private fun buildNavigation(id: Int): Boolean {
        if (prevId == id) {
            return true
        }
        prevId = id
        val fragment: Fragment

        when (id) {
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
