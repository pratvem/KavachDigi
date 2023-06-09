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
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.*

@AndroidEntryPoint
class WelcomeDetailActivity : AppCompatActivity() {

    private val adapter by lazy {
        WelcomeDetailAdapter()
    }

    private val indicatorAdapter by lazy {
        WelcomeDetailIndicatorAdapter()
    }
    private lateinit var timer: Timer

    private lateinit var binding: ActivityWelcomeDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_welcome_detail)

        /** View Pager Setup */
        viewPagerSetup()

        /** Click Listeners */
        clickListeners()

    }

    override fun onResume() {
        super.onResume()

        /** Image Auto Scroll Setup */
        imageScrollSetup()
    }

    /**
     * Image Auto Scroll Setup
     */
    private fun imageScrollSetup() {
        val timerTask: TimerTask = object : TimerTask() {
            override fun run() {
                binding.apply {
                    viewPager.post {
                        viewPager.currentItem =
                            (viewPager.currentItem + 1) % getWelcomeDetailsImages().size
                    }
                }

            }
        }

        timer = Timer()
        timer.schedule(timerTask, 2500, 2500)
    }

    override fun onStop() {
        super.onStop()
        timer.cancel()
    }

    /**
     * Click Listeners
     */
    private fun clickListeners() {
        binding.apply {
            include.llBack.setOnClickListener {
                finish()
            }

            btProceed.setOnClickListener {
                startActivity(
                    Intent(
                        this@WelcomeDetailActivity,
                        SubscriptionActivity::class.java
                    )
                )
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