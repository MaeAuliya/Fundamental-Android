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
import com.example.githubapp.model.HomeUsers
import com.example.githubapp.adapter.UserAdapter
import com.example.githubapp.databinding.FragmentFollowerBinding
import com.example.githubapp.ui.view_model.FollowViewModel


class FollowerFragment : Fragment() {

    private lateinit var followViewModel : FollowViewModel

    private var _fragmentFollowerBinding : FragmentFollowerBinding? = null
    private val binding get() = _fragmentFollowerBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        followViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())[FollowViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _fragmentFollowerBinding = FragmentFollowerBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val username = arguments?.getString(DetailUserActivity.EXTRA_FRAGMENT).toString()

        followViewModel.listFollowers.observe(viewLifecycleOwner){user ->
            setFollowerData(user)
        }

        followViewModel.isLoading.observe(viewLifecycleOwner){
            showLoading(it)
        }

        followViewModel.isError.observe(viewLifecycleOwner){
            showError(it)
        }

        followViewModel.getFollowers(username)

    }

    override fun onDestroy() {
        super.onDestroy()
        _fragmentFollowerBinding = null
    }

    private fun setFollowerData(follows: ArrayList<HomeUsers>){

        if (follows.size > 0){
            val linearLayoutManager = LinearLayoutManager(context)
            val userAdapter = UserAdapter(follows)
            binding?.rvFollower?.layoutManager = linearLayoutManager
            binding?.rvFollower?.adapter = userAdapter

            userAdapter.setOnItemClickCallback(object : UserAdapter.OnItemClickCallback {
                override fun onItemClicked(user: HomeUsers) {
                    Toast.makeText(context,user.login,Toast.LENGTH_SHORT).show()
                    val followerIntent = Intent(activity, DetailUserActivity::class.java)
                    followerIntent.putExtra(DetailUserActivity.EXTRA_DATA, user.login)
                    activity?.startActivity(followerIntent)
                }
            })

        }else binding?.tvStatus?.visibility = View.VISIBLE

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