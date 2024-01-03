package com.slack.exercise.dataprovider

import android.content.Context
import com.slack.exercise.R
import java.io.BufferedReader
import java.io.InputStreamReader

object DenylistProvider {

    fun readRawFileToSet(context: Context): Set<String> {
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
}