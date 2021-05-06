package com.nandaprasetio.moviecatalog.presentation.adapter.fragmentstateadapter

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.nandaprasetio.moviecatalog.R
import com.nandaprasetio.moviecatalog.presentation.fragment.recyclerviewfragment.BaseVideoRecyclerViewFragment
import com.nandaprasetio.moviecatalog.presentation.fragment.recyclerviewfragment.MovieVideoRecyclerViewFragment
import com.nandaprasetio.moviecatalog.presentation.fragment.recyclerviewfragment.TvShowVideoRecyclerViewFragment

class VideoFragmentStateAdapter(fragmentActivity: FragmentActivity, private val favourite: Boolean)
    : FragmentStateAdapter(fragmentActivity), TabLayoutMediator.TabConfigurationStrategy {
    private val tabNameList: List<String> = listOf(
        fragmentActivity.getString(R.string.movies),
        fragmentActivity.getString(R.string.tv_shows)
    )

    override fun getItemCount(): Int {
        return tabNameList.size
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> MovieVideoRecyclerViewFragment()
            1 -> TvShowVideoRecyclerViewFragment()
            else -> throw IllegalStateException("Invalid Position")
        }.apply {
            this.arguments = Bundle().apply {
                this.putBoolean(BaseVideoRecyclerViewFragment.ARGUMENT_FAVOURITE, favourite)
            }
        }
    }

    override fun onConfigureTab(tab: TabLayout.Tab, position: Int) {
        tab.text = tabNameList[position]
    }
}