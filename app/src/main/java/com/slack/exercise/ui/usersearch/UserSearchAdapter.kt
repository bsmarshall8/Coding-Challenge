package com.slack.exercise.ui.usersearch

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.slack.exercise.databinding.ItemUserSearchBinding
import com.slack.exercise.model.UserSearchResult

/**
 * Adapter for the list of [UserSearchResult].
 */
class UserSearchAdapter : RecyclerView.Adapter<UserSearchAdapter.UserSearchViewHolder>() {
    private var userSearchResults: List<UserSearchResult> = emptyList()

    fun setResults(results: Set<UserSearchResult>) {
        userSearchResults = results.toList()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserSearchViewHolder {
        val binding: ItemUserSearchBinding =
            ItemUserSearchBinding.inflate(LayoutInflater.from(parent.context))
        return UserSearchViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return userSearchResults.size
    }

    override fun onBindViewHolder(holder: UserSearchViewHolder, position: Int) {
        val user = userSearchResults[position]
        holder.username.text = user.username
        holder.displayName.text = user.displayName
        Glide.with(holder.avatar.context).load(user.avatarUrl).into(holder.avatar)
    }

    class UserSearchViewHolder(binding: ItemUserSearchBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val username: TextView = binding.username
        val avatar: ImageView = binding.imageViewAvatar
        val displayName: TextView = binding.displayName
    }
}