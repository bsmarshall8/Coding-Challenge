package com.slack.exercise.db
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_search_results")
data class DbUserSearchResult(
    @PrimaryKey val username: String,
    val displayName: String,
    val avatarUrl: String
)