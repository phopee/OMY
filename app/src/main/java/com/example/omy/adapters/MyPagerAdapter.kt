package com.example.omy.adapters

import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.omy.views.HistoryFragment
import com.example.omy.views.HomeFragment
import com.example.omy.views.SettingsFragment

class MyPagerAdapter(frag:FragmentActivity): FragmentStateAdapter(frag) {

    private enum class TABS {
        HOME_PAGE, HISTORY_PAGE,SETTINGS_PAGE
    }

    override fun getItemCount()= TABS.values().size

    override fun createFragment(position: Int): Fragment {

        println("TESTT createFragment in ReceivingPagerAdapter")
        val pos: TABS = if (position == 0)
            TABS.HOME_PAGE
        else if (position == 1)
            TABS.HISTORY_PAGE
        else TABS.SETTINGS_PAGE

        return when (pos) {
            TABS.HOME_PAGE -> {
                val homeFrag = HomeFragment()

                homeFrag
            }
            TABS.HISTORY_PAGE -> {
                val historyFrag = HistoryFragment()

                historyFrag
            }
            TABS.SETTINGS_PAGE -> {
                val settingsFrag = SettingsFragment()

                settingsFrag
            }
        }
    }

}