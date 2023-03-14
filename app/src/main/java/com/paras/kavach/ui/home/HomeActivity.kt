package com.paras.kavach.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import androidx.viewpager2.widget.ViewPager2
import com.paras.kavach.R
import com.paras.kavach.data.local.prefs.SharedPrefs
import com.paras.kavach.databinding.ActivityHomeBinding
import com.paras.kavach.decor.HorizontalMarginItemDecoration
import com.paras.kavach.ui.setting.SettingActivity
import com.paras.kavach.ui.setting.SettingVM
import com.paras.kavach.ui.welCome.WelcomeDetailIndicatorAdapter
import com.paras.kavach.utils.AppConstant
import com.paras.kavach.utils.gone
import com.paras.kavach.utils.visible
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject


@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {

    @Inject
    lateinit var sharedPrefs: SharedPrefs

    private val adapter by lazy {
        HomeNewsAdapter()
    }
    private val indicatorAdapter by lazy {
        WelcomeDetailIndicatorAdapter()
    }
    private lateinit var binding: ActivityHomeBinding
    private lateinit var animation: Animation
    private val viewModel: SettingVM by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home)

        if (viewModel.isAccessibilitySettingsOn(this)) {
            startActivity(Intent(this@HomeActivity, SettingActivity::class.java))
        }

        /** View Pager Setup */
        viewPagerSetup()

        /** Click Listeners */
        clickListeners()
    }

    override fun onResume() {
        super.onResume()
        if (viewModel.isAccessibilitySettingsOn(this)) {
            binding.ivCheck.visible()
            binding.ivInfo.gone()
        } else {
            binding.ivCheck.gone()
            binding.ivInfo.visible()
        }

        if (!sharedPrefs.getBoolean(AppConstant.BLINK_IMAGE)) {
            animation = AlphaAnimation(0.0f, 1.0f)
            animation.duration = 500
            animation.repeatMode = Animation.REVERSE
            animation.repeatCount = Animation.INFINITE

            binding.ivInfo.startAnimation(animation)
        }
    }

    /**
     * Click Listeners
     */
    private fun clickListeners() {
        binding.apply {
            ivInfo.setOnClickListener {
                sharedPrefs.save(AppConstant.BLINK_IMAGE, true)
                ivInfo.clearAnimation()
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