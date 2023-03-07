package com.paras.kavach.ui.welCome

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import androidx.viewpager2.widget.ViewPager2
import com.paras.kavach.R
import com.paras.kavach.databinding.ActivityWelcomeDetailBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class WelcomeDetailActivity : AppCompatActivity() {
    val adapter by lazy {
        WelcomeDetailAdapter()
    }
    val indicatorAdapter by lazy {
        WelcomeDetailIndicatorAdapter()
    }
    lateinit var binding: ActivityWelcomeDetailBinding
    val imageData = listOf<Int>(
        R.drawable.p1,
        R.drawable.p2,
        R.drawable.p3,
        R.drawable.p4,
        R.drawable.p5,
        R.drawable.p6
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_welcome_detail)
        lifecycleScope.launch {
            binding.viewPager.adapter = adapter
            adapter.submitList(imageData)
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