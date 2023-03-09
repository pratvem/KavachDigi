package com.paras.kavach.ui.home

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import androidx.viewpager2.widget.ViewPager2
import com.paras.kavach.R
import com.paras.kavach.databinding.ActivityHomeBinding
import com.paras.kavach.decor.HorizontalMarginItemDecoration
import com.paras.kavach.ui.setting.SettingActivity
import com.paras.kavach.ui.welCome.WelcomeDetailIndicatorAdapter
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class HomeActivity : AppCompatActivity() {
    private val adapter by lazy {
        HomeNewsAdapter()
    }
    private val indicatorAdapter by lazy {
        WelcomeDetailIndicatorAdapter()
    }
    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home)

        /** View Pager Setup */
        viewPagerSetup()

        /** Click Listeners */
        clickListeners()
    }

    /**
     * Click Listeners
     */
    private fun clickListeners() {
        binding.apply {
            ivInfo.setOnClickListener {
                startActivity(Intent(this@HomeActivity, SettingActivity::class.java))
            }
        }
    }

    /**
     * View Pager Setup
     */
    private fun viewPagerSetup() {
        binding.apply {
            lifecycleScope.launch {
                viewPager.adapter = adapter
                adapter.submitList(arrayOfNulls<String>(8).toMutableList())
                viewPager.apply {
                    offscreenPageLimit = 1
                    val itemDecoration = HorizontalMarginItemDecoration(
                        this@HomeActivity,
                        R.dimen.viewpager_current_item_horizontal_margin
                    )
                    addItemDecoration(itemDecoration)
                }
                delay(200)
                rvIndicator.adapter = indicatorAdapter
                indicatorAdapter.submitList(arrayOfNulls<Int>(adapter.itemCount).toMutableList())
            }

            viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    runOnUiThread {
                        indicatorAdapter.selectedPosition = position
                        indicatorAdapter.notifyItemChanged(position)
                        indicatorAdapter.notifyItemChanged(indicatorAdapter.previousSelected)
                        indicatorAdapter.previousSelected = position
                    }
                }
            })
        }
    }
}