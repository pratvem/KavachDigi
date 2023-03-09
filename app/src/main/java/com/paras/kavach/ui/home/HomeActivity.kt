package com.paras.kavach.ui.home

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import androidx.viewpager2.widget.ViewPager2
import com.paras.kavach.R
import com.paras.kavach.databinding.ActivityHomeBinding
import com.paras.kavach.decor.HorizontalMarginItemDecoration
import com.paras.kavach.ui.welCome.WelcomeDetailIndicatorAdapter
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class HomeActivity : AppCompatActivity() {
    val adapter by lazy {
        HomeNewsAdapter()
    }
    val indicatorAdapter by lazy {
        WelcomeDetailIndicatorAdapter()
    }
    lateinit var binding: ActivityHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home)
        lifecycleScope.launch {
            binding.viewPager.adapter = adapter
            adapter.submitList(arrayOfNulls<String>(8).toMutableList())
            binding.viewPager.apply {

                offscreenPageLimit = 1
                val nextItemVisiblePx = resources.getDimension(R.dimen.viewpager_next_item_visible)
                val currentItemHorizontalMarginPx =
                    resources.getDimension(R.dimen.viewpager_current_item_horizontal_margin)
                val pageTranslationX = nextItemVisiblePx + currentItemHorizontalMarginPx
            val item=    setPageTransformer { page, position ->
                    page.translationX = -pageTranslationX * position

                }
                val itemDecoration = HorizontalMarginItemDecoration(this@HomeActivity,
                    R.dimen.viewpager_current_item_horizontal_margin
                )
                addItemDecoration(itemDecoration)


            }
            delay(200)
            binding.rvIndicator.adapter = indicatorAdapter
            indicatorAdapter.submitList(arrayOfNulls<Int>(adapter.itemCount).toMutableList())
        }
        binding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {

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