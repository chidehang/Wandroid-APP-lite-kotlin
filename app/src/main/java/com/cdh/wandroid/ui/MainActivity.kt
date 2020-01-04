package com.cdh.wandroid.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2
import com.cdh.wandroid.R
import com.cdh.wandroid.databinding.ActivityMainBinding
import com.cdh.wandroid.ui.adapter.MainNavPagerAdapter
import com.cdh.wandroid.util.T
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var onNavItemSelectedListener: BottomNavigationView.OnNavigationItemSelectedListener
    private lateinit var onPageChangeCallback: ViewPager2.OnPageChangeCallback

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        initView()
        T.init(applicationContext)
    }

    private fun initView() {
        onNavItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener {
            when(it.itemId) {
                R.id.navigation_home -> {
                    binding.viewPagerMain.currentItem = 0
                    return@OnNavigationItemSelectedListener true
                }
                R.id.navigation_category -> {
                    binding.viewPagerMain.currentItem = 1
                    return@OnNavigationItemSelectedListener true
                }
                R.id.navigation_mine -> {
                    binding.viewPagerMain.currentItem = 2
                    return@OnNavigationItemSelectedListener true
                }
                else -> {
                    return@OnNavigationItemSelectedListener false
                }
            }
        }

        onPageChangeCallback = object: ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                when(position) {
                    0 -> {
                        binding.bottomNavMain.selectedItemId = R.id.navigation_home
                    }
                    1 -> {
                        binding.bottomNavMain.selectedItemId = R.id.navigation_category
                    }
                    2 -> {
                        binding.bottomNavMain.selectedItemId = R.id.navigation_mine
                    }
                }
            }
        }

        binding.bottomNavMain.setOnNavigationItemSelectedListener(onNavItemSelectedListener)

        val fragments = listOf(HomeFragment(), CategoryFragment(), MineFragment())
        binding.viewPagerMain.let {
            it.isUserInputEnabled = false
            it.adapter = MainNavPagerAdapter(this, fragments)
            it.registerOnPageChangeCallback(onPageChangeCallback)
        }
    }
}
