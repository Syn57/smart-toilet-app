package com.project.smarttoilet.adapter

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.ta.smile.ui.analysis.FecesResultFragment
import com.ta.smile.ui.analysis.FecesTipsFragment


class FecesSectionPagerAdapter (activity: AppCompatActivity) : FragmentStateAdapter(activity) {

    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        var fragment: Fragment? = null
        when(position){
            0 -> {
                fragment = FecesResultFragment()
            }
            1 -> {
                fragment = FecesTipsFragment()
            }
        }
        return fragment as Fragment
    }
}
