package com.slack.exercise.ui.usersearch

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.slack.exercise.databinding.ItemUserSearchBinding
import com.slack.exercise.model.UserSearchResult

/**
 * ListAdapter for the list of [UserSearchResult].
 */
class UserSearchAdapter : ListAdapter<UserSearchResult, UserSearchAdapter.UserSearchViewHolder>(
    UserSearchResultsDiffCallback()
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserSearchViewHolder {
        val binding: ItemUserSearchBinding =
            ItemUserSearchBinding.inflate(LayoutInflater.from(parent.context))
        return UserSearchViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UserSearchViewHolder, position: Int) {
        val user = getItem(position)
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

    private class UserSearchResultsDiffCallback : DiffUtil.ItemCallback<UserSearchResult>() {
        override fun areItemsTheSame(oldItem: UserSearchResult, newItem: UserSearchResult): Boolean {
            return oldItem.username == newItem.username
        }

        override fun areContentsTheSame(oldItem: UserSearchResult, newItem: UserSearchResult): Boolean {
            return oldItem == newItem
        }
    }
}
