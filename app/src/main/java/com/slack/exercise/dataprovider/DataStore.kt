package com.slack.exercise.dataprovider

import android.content.Context
import android.content.SharedPreferences
import com.slack.exercise.R
import java.io.BufferedReader
import java.io.InputStreamReader

/**
 * Storage solution for fetching/storing denylist
 */
//TODO figure out DI
class DataStore(private val context: Context) {

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences(SHARED_PREFS_NAME, Context.MODE_PRIVATE)

    fun readRawFileToSet(): Set<String> {
        val resultSet = mutableSetOf<String>()
        try {
            context.resources.openRawResource(R.raw.denylist).use { inputStream ->
                InputStreamReader(inputStream).use { inputStreamReader ->
                    BufferedReader(inputStreamReader).use { bufferedReader ->
                        var line: String?
                        while (bufferedReader.readLine().also { line = it } != null) {
                            resultSet.add(line.orEmpty().trim())
                        }
                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return resultSet
    }

    fun saveSetToPreferences(set: Set<String>) {
        val editor = sharedPreferences.edit()
        editor.putStringSet(DENYLIST_KEY, set)
        editor.apply()
    }

    fun getDenylistFromPreferences(): Set<String>? {
        return sharedPreferences.getStringSet(DENYLIST_KEY, null)
    }

    companion object {
        const val SHARED_PREFS_NAME = "SlackChallenge"
        const val DENYLIST_KEY = "denylistKey"
    }
}