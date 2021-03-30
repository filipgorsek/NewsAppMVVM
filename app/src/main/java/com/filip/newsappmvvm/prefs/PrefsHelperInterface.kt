package com.filip.newsappmvvm.prefs

interface PrefsHelperInterface {

    fun saveTimeStamp(timeStamp: Long)

    fun getTimeStamp(): Long
}