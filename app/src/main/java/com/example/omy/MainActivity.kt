package com.example.omy

import android.os.Bundle
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayout
import androidx.viewpager.widget.ViewPager
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.widget.ViewPager2
import com.example.omy.adapters.MyPagerAdapter
import com.example.omy.databinding.ActivityMainBinding
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity :AppCompatActivity(){

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewPager: ViewPager2
    private lateinit var tabLayout: TabLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewPager = findViewById(R.id.pager)
        tabLayout = findViewById(R.id.tabs)

        val pageAdapter = MyPagerAdapter(this)
        viewPager.adapter = pageAdapter

        val tabTitle = listOf(
            getString(R.string.Hhome),
            getString(R.string.history),
            getString(R.string.settings)
        )
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = tabTitle[position]
        }.attach()

    }


}
