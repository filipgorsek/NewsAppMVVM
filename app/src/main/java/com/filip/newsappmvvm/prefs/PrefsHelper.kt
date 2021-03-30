package com.filip.newsappmvvm.prefs

import android.content.SharedPreferences
import com.filip.newsappmvvm.common.PREFS_TIMESTAMP

class PrefsHelper(private val sharedPreferences: SharedPreferences) : PrefsHelperInterface {

    override fun getTimeStamp(): Long = sharedPreferences.getLong(PREFS_TIMESTAMP,0L)

    override fun saveTimeStamp(timeStamp: Long) = sharedPreferences.edit().putLong(PREFS_TIMESTAMP, timeStamp).apply()

}