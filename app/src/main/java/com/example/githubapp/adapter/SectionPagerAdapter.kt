package com.example.githubapp.adapter

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.githubapp.ui.view.FollowerFragment
import com.example.githubapp.ui.view.FollowingFragment

class SectionPagerAdapter(activity : AppCompatActivity, private val login : Bundle) : FragmentStateAdapter(activity) {
    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        var fragment : Fragment? = null
        when(position){
            0 -> fragment = FollowingFragment()
            1 -> fragment = FollowerFragment()
        }
        fragment?.arguments = login
        return fragment as Fragment
    }

}