package com.paras.kavach.ui.welCome

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.paras.kavach.R
import com.paras.kavach.data.local.prefs.SharedPrefs
import com.paras.kavach.databinding.ActivityWelcomeBinding
import com.paras.kavach.ui.home.HomeActivity
import com.paras.kavach.utils.AppConstant
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class WelcomeActivity : AppCompatActivity() {

    @Inject
    lateinit var sharedPrefs: SharedPrefs
    private lateinit var binding: ActivityWelcomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_welcome)


        /** Click Listeners */
        clickListeners()
    }

    /**
     * Click Listeners
     */
    private fun clickListeners() {
        binding.apply {
            btnStart.setOnClickListener {
                if (sharedPrefs.getBoolean(AppConstant.PURCHASED_KAVACH)) {
                    startActivity(Intent(this@WelcomeActivity, HomeActivity::class.java))
                } else {
                    startActivity(Intent(this@WelcomeActivity, WelcomeDetailActivity::class.java))
                }
            }
        }
    }
}