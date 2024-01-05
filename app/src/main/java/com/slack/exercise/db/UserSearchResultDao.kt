package com.slack.exercise.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface UserSearchResultDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUserSearchResult(userSearchResult: DbUserSearchResult)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUserSearchResults(userSearchResults: List<DbUserSearchResult>)

    @Query("SELECT * FROM user_search_results")
    fun getAllUserSearchResults(): List<DbUserSearchResult>

    @Query("SELECT * FROM user_search_results WHERE username LIKE :searchTerm OR displayName LIKE :searchTerm")
    fun fetchUsers(searchTerm: String): List<DbUserSearchResult>
}