package com.paras.kavach.ui.subscription

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.paras.kavach.R
import com.paras.kavach.databinding.ActivitySubcriptionBinding

class SubscriptionActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySubcriptionBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_subcription)


        /** Click Listeners */
        clickListeners()
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
                //  startActivity(Intent(this@WelcomeDetailActivity, SubscriptionActivity::class.java))
            }
        }
    }
}