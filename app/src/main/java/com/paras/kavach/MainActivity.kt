package com.paras.kavach

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.paras.kavach.ui.subscription.SubscriptionActivity
import com.paras.kavach.ui.welCome.WelcomeDetailActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        startActivity(Intent(this, SubscriptionActivity::class.java))
    }
}