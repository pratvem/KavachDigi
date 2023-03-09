package com.paras.kavach.ui.welCome

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.paras.kavach.R
import com.paras.kavach.databinding.ActivityWelcomeBinding

class WelcomeActivity : AppCompatActivity() {

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
                startActivity(Intent(this@WelcomeActivity, WelcomeDetailActivity::class.java))
            }
        }
    }
}