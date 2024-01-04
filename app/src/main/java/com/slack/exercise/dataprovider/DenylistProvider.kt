package com.slack.exercise.dataprovider

import android.content.Context
import android.content.SharedPreferences
import com.slack.exercise.R
import java.io.BufferedReader
import java.io.InputStreamReader
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Storage solution for fetching/storing denylist
 * */
@Singleton
class DenylistProvider @Inject constructor(private val context: Context) {
    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences(SHARED_PREFS_NAME, Context.MODE_PRIVATE)
    var denylist: MutableSet<String>

    init {
        denylist = if (getDenylistFromPreferences() == null) {
            readRawFileToSet().toMutableSet()
        } else {
            getDenylistFromPreferences()!!.toMutableSet()
        }
        println(denylist)
    }

    private fun readRawFileToSet(): Set<String> {
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

    fun persistDenylist() {
        val editor = sharedPreferences.edit()
        editor.putStringSet(DENYLIST_KEY, denylist)
        editor.apply()
    }

    private fun getDenylistFromPreferences(): Set<String>? {
        return sharedPreferences.getStringSet(DENYLIST_KEY, null)
    }

    companion object {
        const val SHARED_PREFS_NAME = "SlackChallenge"
        const val DENYLIST_KEY = "denylistKey"
    }
}