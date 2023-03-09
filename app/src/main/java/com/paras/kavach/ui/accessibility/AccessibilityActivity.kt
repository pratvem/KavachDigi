package com.paras.kavach.ui.accessibility

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.paras.kavach.R
import com.paras.kavach.databinding.ActivityAccessibilityBinding

class AccessibilityActivity : AppCompatActivity() {
    lateinit var binding:ActivityAccessibilityBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       binding=DataBindingUtil.setContentView(this,R.layout.activity_accessibility)
    }
}