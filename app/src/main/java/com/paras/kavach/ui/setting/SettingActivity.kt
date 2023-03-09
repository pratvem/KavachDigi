package com.paras.kavach.ui.setting

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.paras.kavach.R
import com.paras.kavach.databinding.ActivitySettingBinding

class SettingActivity : AppCompatActivity() {
    lateinit var binding:ActivitySettingBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)
    }
}