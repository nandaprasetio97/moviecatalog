package com.nandaprasetio.moviecatalog.presentation.activity

import android.os.Bundle
import com.google.android.material.tabs.TabLayoutMediator
import com.nandaprasetio.moviecatalog.presentation.adapter.fragmentstateadapter.VideoFragmentStateAdapter
import com.nandaprasetio.moviecatalog.databinding.ActivityMainBinding

abstract class VideoListActivity(private val favourite: Boolean, enableBack: Boolean): BaseActivity<ActivityMainBinding>(enableBack = enableBack) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getViewBinding()?.also {
            val videoFragmentStateAdapter = VideoFragmentStateAdapter(this, favourite)
            it.viewPager2Video.adapter = videoFragmentStateAdapter
            val tabLayoutMediator = TabLayoutMediator(
                it.tabLayoutVideo, it.viewPager2Video, videoFragmentStateAdapter
            )
            tabLayoutMediator.attach()
        }
    }

    override fun onInitializeViewBinding(): ActivityMainBinding {
        return ActivityMainBinding.inflate(this.layoutInflater)
    }
}