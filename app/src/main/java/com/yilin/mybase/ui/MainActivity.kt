package com.yilin.mybase.ui

import com.yilin.mybase.databinding.ActivityMainBinding

class MainActivity : BaseActivity<ActivityMainBinding>() {

    override fun getViewBinding(): ActivityMainBinding {
        return ActivityMainBinding.inflate(layoutInflater)
    }

    override fun initLayoutView() {
    }

    override fun initViewData() {
    }
}