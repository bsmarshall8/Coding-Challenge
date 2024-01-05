package com.slack.exercise.db
import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [DbUserSearchResult::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userSearchResultDao(): UserSearchResultDao
}