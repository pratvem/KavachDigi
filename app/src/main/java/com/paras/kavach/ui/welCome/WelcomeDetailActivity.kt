package com.paras.kavach.ui.welCome

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import androidx.viewpager2.widget.ViewPager2
import com.paras.kavach.R
import com.paras.kavach.databinding.ActivityWelcomeDetailBinding
import com.paras.kavach.ui.subscription.SubscriptionActivity
import com.paras.kavach.utils.getWelcomeDetailsImages
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class WelcomeDetailActivity : AppCompatActivity() {

    private val adapter by lazy {
        WelcomeDetailAdapter()
    }

    private val indicatorAdapter by lazy {
        WelcomeDetailIndicatorAdapter()
    }

    private lateinit var binding: ActivityWelcomeDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_welcome_detail)

        /** View Pager Setup */
        viewPagerSetup()

        /** Click Listeners */
        clickListeners()

    }

    private fun clickListeners() {
        binding.apply {
            include.llBack.setOnClickListener {
                finish()
            }

            btProceed.setOnClickListener {
                startActivity(Intent(this@WelcomeDetailActivity, SubscriptionActivity::class.java))
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
                adapter.submitList(getWelcomeDetailsImages())
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