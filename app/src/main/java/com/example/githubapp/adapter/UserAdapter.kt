package com.example.githubapp.adapter


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.githubapp.model.HomeUsers
import com.example.githubapp.databinding.UserCardBinding


class UserAdapter(private val listUser : ArrayList<HomeUsers>) : RecyclerView.Adapter<UserAdapter.ViewHolder>() {

    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback){
        this.onItemClickCallback = onItemClickCallback
    }

    class ViewHolder(var binding: UserCardBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = UserCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = listUser.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val user = listUser[position]

        holder.binding.apply {
            cardTvUsername.text = user.login
            Glide.with(cardImageProfile.context)
                .load(user.avatarUrl)
                .into(cardImageProfile)
        }

        holder.itemView.setOnClickListener{
            onItemClickCallback.onItemClicked(user)
        }
    }

    interface OnItemClickCallback {
        fun onItemClicked(user : HomeUsers)
    }
}