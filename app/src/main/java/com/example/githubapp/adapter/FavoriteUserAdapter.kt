package com.example.githubapp.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.githubapp.database.FavoriteUser
import com.example.githubapp.databinding.UserCardBinding
import com.example.githubapp.helper.ListDiffCallback
import com.example.githubapp.ui.view.DetailUserActivity

class FavoriteUserAdapter : RecyclerView.Adapter<FavoriteUserAdapter.FavoriteUserViewHolder>() {

    private val listFavorite = ArrayList<FavoriteUser>()


    fun setListFavorite(list: List<FavoriteUser>){
        val diffCallback = ListDiffCallback(this.listFavorite, list)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        this.listFavorite.clear()
        this.listFavorite.addAll(list)
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteUserViewHolder {
        val binding = UserCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavoriteUserViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return listFavorite.size
    }

    override fun onBindViewHolder(holder: FavoriteUserViewHolder, position: Int) {
        holder.bind(listFavorite[position])
    }

    inner class FavoriteUserViewHolder(private val binding : UserCardBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(list: FavoriteUser){
            with(binding){
                cardTvUsername.text = list.username
                Glide.with(cardImageProfile.context)
                    .load(list.avatarUrl)
                    .into(cardImageProfile)
                itemView.setOnClickListener{
                    val intent = Intent(itemView.context, DetailUserActivity::class.java)
                    intent.putExtra(DetailUserActivity.EXTRA_DATA, list.username)
                    itemView.context.startActivity(intent)
                }
            }
        }
    }

}