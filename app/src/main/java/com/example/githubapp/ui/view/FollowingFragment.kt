package com.example.githubapp.ui.view

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubapp.adapter.UserAdapter
import com.example.githubapp.databinding.FragmentFollowingBinding
import com.example.githubapp.model.HomeUsers
import com.example.githubapp.ui.view_model.FollowingViewModel


class FollowingFragment : Fragment() {

    private lateinit var followingViewModel: FollowingViewModel

    private var _fragmentFollowingBinding : FragmentFollowingBinding? = null
    private val binding get() = _fragmentFollowingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        followingViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())[FollowingViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _fragmentFollowingBinding = FragmentFollowingBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val username = arguments?.getString(DetailUserActivity.EXTRA_FRAGMENT).toString()

        followingViewModel.listFollowing.observe(viewLifecycleOwner){user ->
            setFollowingData(user)
        }

        followingViewModel.isLoading.observe(viewLifecycleOwner){
            showLoading(it)
        }

        followingViewModel.isError.observe(viewLifecycleOwner){
            showError(it)
        }

        followingViewModel.getFollowing(username)
    }

    override fun onDestroy() {
        super.onDestroy()
        _fragmentFollowingBinding = null
    }

    private fun setFollowingData(user: ArrayList<HomeUsers>) {

        if (user.size > 0) {
            val linearLayoutManager = LinearLayoutManager(activity)
            val userAdapter = UserAdapter(user)
            binding?.rvFollowing?.layoutManager = linearLayoutManager
            binding?.rvFollowing?.adapter = userAdapter

            userAdapter.setOnItemClickCallback(object : UserAdapter.OnItemClickCallback {
                override fun onItemClicked(user: HomeUsers) {
                    Toast.makeText(context,user.login, Toast.LENGTH_SHORT).show()
                    val followingIntent = Intent(activity, DetailUserActivity::class.java)
                    followingIntent.putExtra(DetailUserActivity.EXTRA_DATA,user.login)
                    activity?.startActivity(followingIntent)
                }
            })
        } else binding?.tvStatus2?.visibility = View.VISIBLE

    }

    private fun showLoading(isLoading: Boolean){
        if (isLoading){
            binding?.progressBar?.visibility = View.VISIBLE
        }else{
            binding?.progressBar?.visibility = View.GONE
        }
    }

    private fun showError(isError: Boolean){
        if (isError){
            Toast.makeText(context, "Error For Getting Data's From API", Toast.LENGTH_SHORT).show()
        }
    }
}