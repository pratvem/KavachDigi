package com.paras.kavach.ui.subscription

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.paras.kavach.R
import com.paras.kavach.databinding.ActivitySubcriptionBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SubscriptionActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySubcriptionBinding
    private val viewModel: SubscriptionVM by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_subcription)


        /** Click Listeners */
        clickListeners()

        /** Observer Listeners */
        observerListeners()

        /** Connect with Billing Library */
        viewModel.initBillingClient(this)
    }

    /**
     * Observer Listeners
     */
    private fun observerListeners() {
        viewModel.purchaseSuccessResMLD.observe(this) {

        }
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
                viewModel.connectToBilling(this@SubscriptionActivity, "kavach_1_year")

                // startActivity(Intent(this@SubscriptionActivity, HomeActivity::class.java))
            }
        }
    }

}